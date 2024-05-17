package com.misterf.viewer;

import java.awt.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class TreeInteraction {

    private List<File> openfiles, files;
    private File root;
    private int listLength, start;
    protected List<String> visual = new ArrayList<>();

    public TreeInteraction(File root, List<File> openfiles, int listLength, int start) {
        this.openfiles = openfiles;
        this.root = root.getAbsoluteFile();
        this.listLength = listLength;
        this.files = new ArrayList<>();
        this.start = start;
    }

    /**
     * Used to traverse in the tree using commands. The caller must separate the command in number & string.
     * Hint: use MisterP
     * @param command a command of type:
     *                "f@": focus on a folder. Changes the {@link TreeInteraction#root}
     *                "b": go back in the file structure. Changes the {@link TreeInteraction#root}
     *                "o@": open a folder. Changes the {@link TreeInteraction#openfiles}
     *                "c@": close a folder. Changes the {@link TreeInteraction#openfiles}
     *                "n": goes to the next page, shows more files. Changes the {@link TreeInteraction#start}
     *                "p": goes to the previous page, shows previous files. Changes the {@link TreeInteraction#start} with {@link TreeInteraction#listLength}
     * @param number the number that goes with the command.
     */
    public File command(String command, int number) {

        command = command.toUpperCase().strip();

        switch(command) {
            case "L":
                try {
                    if(getFiles().get(number).isFile())
                        Runtime.getRuntime().exec("explorer.exe " + getFiles().get(number).getParentFile().getAbsolutePath());
                    else
                        Runtime.getRuntime().exec("explorer.exe " + getFiles().get(number).getAbsolutePath());
                } catch(Exception e) {

                }
                break;
            // Show next page
            case "N":
                if(this.files.size()-start > listLength)
                    this.start += listLength;
                break;
            // Show previous page
            case "P":
                start -= listLength;
                if(start < 0)
                    start = 0;
                break;
            // Focus on folder
            case "F":
                if(isNumberValid(number) && files.get(number).isDirectory()) {
                    this.root = files.get(number);
                    this.openfiles.clear();
                    this.start = 0;
                }
                break;
            // Open folder
            case "O":
                if(isNumberValid(number) && !openfiles.contains(files.get(number).getParentFile()) && !openfiles.contains(files.get(number)))
                    openfiles.add(files.get(number));
                else if(isNumberValid(number) && openfiles.contains(files.get(number).getParentFile()))
                    command("f", number);
                break;
            // Close file
            case "C":
                if(isNumberValid(number) && openfiles.contains(files.get(number)))
                    openfiles.remove(files.get(number));
                break;
            // Go back to previous file
            case "R":
                String path = root.getAbsolutePath();
                this.root = this.root.getParentFile();
                if(path.equals(root.getAbsolutePath() + "\\.")) root = this.root.getParentFile();
                start = 0;
                files.clear();
                openfiles.clear();
                break;
        }

        if(command.isEmpty() && number <= listLength && number >= 1)
            return getFile(number);


        compose();
        return null;

    }

    /**
     * Returns a {@link File} from the {@link TreeInteraction#files} list.
     * @param number an index in the list. Invalid indices will always return null
     * @return a file from the {@link TreeInteraction#files} list, or null
     */
    File getFile(int number) {
        if(isNumberValid(number)) return this.files.get(number);
        return null;
    }

    /**
     * Returns the string that represents the tree
     * @return a string that displays the tree
     */
    List<String> getDisplay() {
        this.visual = new ArrayList<>();
        compose();
        return this.visual;
    }

    /**
     * Prints the tree in default format.
     */
    public void printDefault() {
        this.printDisplay("  ", " ");
    }

    /**
     * Prints the tree in custom format
     * @param sideIndent the amount of spaces at the left before printing
     * @param indent the amount of spaces between the numbering & naming
     */
    public void printDisplay(String sideIndent, String indent) {
        compose();
        String extraIndent = "";

        System.out.println(sideIndent + "# " + getRoot().getName());
        List<String> visuals = getDisplay();
        for(int i = 0; i < visuals.size(); i++) {

            if(i < 9) extraIndent = " ";
            else extraIndent = "";
            System.out.println(sideIndent + Integer.toString(i+1) + ")" + indent + extraIndent + visuals.get(i));

        }

    }

    /**
     * Opens a folder in {@link TreeInteraction#openfiles}. If the index is not a directory, nothing will happen.
     * @param index the index in the list of files that should be opened
     */
    void openFolder(int index) {
        if(this.files.get(index).isDirectory()) {
            this.openfiles.add(this.files.get(index));
            this.compose();
        }
    }

    /**
     * Closes a folder
     * @param index the index in the {@link TreeInteraction#files} that should be closed
     */
    void closeFolder(int index) {
        if(this.files.size() >= index && files.get(index) != null && files.get(index).isDirectory() && openfiles.contains(files.get(index))) {
            this.openfiles.remove(files.get(index));
            this.compose();
        }
    }

    /**
     * Creates the tree visualization in a string with current class fields
     */
     protected void compose() {
        // Create new file array & fill with files in the root directory
        this.files = new ArrayList<>();
        File[] inDirectory = this.root.listFiles();

        // We sort all the files
        if(inDirectory.length > 0) Arrays.sort(inDirectory, Comparator.comparing(File::getName));

        // We add 'null' object in the first entry, such that all indices given to interaction represent direct integers in the files array
        files.add(null);

        // If we have added the action NEXT, then files will still be displayed 31-60. However the list is empty in those indices
        // note that we fill it until start - 1. Such that the index start
        for(int i = 0; i < start; i++)
            files.add(null);

        // When a subfolder is opened, we keep track of how many files have been added.
        // Upon return, we let the loop wait. So the files are correctly NUMBERED
        int listed = 0;

        // When we have to skip iterations because a subfolder was opened, the main loop
        // will skip files in the array. We only want to skip the numbering.
        int fileIndex = start;

        // When only folders or files are shown, the ignored files still count up the counter i, which makes
        // the print 1) 3) 5) 9)
        int fileSkip = 0;

        for (int i = start+1; fileIndex < inDirectory.length && i < start + listLength+1; i++) {

            if(listed > 1) {
                listed--;
                continue;
            }

            File file = inDirectory[fileIndex];

            if(file.isDirectory() && openfiles.contains(file))
                listed = handleOpenFolder(file, i, start);
            else if(shouldAddFile(file) && !this.files.contains(file)) {
                this.files.add(file);
                formatFiles(file, " ");
            }

            fileIndex++;

        }

        if(fileIndex < inDirectory.length) {
            this.visual.add("        ...");
        }

    }

    /**
     * Determines if an entry should be added to the list of displayed items
     * @param file the file we want to inspect
     * @return boolean determining if that file should be added to the list
     */
    protected boolean shouldAddFile(File file) {
         return true;
    }

    /**
     * When an item in {@link TreeInteraction#openfiles} is encountered while looping over files in the {@link TreeInteraction#root}, this method
     * is used to print files in that subdirectory.
     * Will update {@link TreeInteraction#visual}.
     * @param folder the folder in {@link TreeInteraction#openfiles}
     * @param listing the index where the element in {@link TreeInteraction#openfiles} is found. Used to keep the list intact
     * @param start used to limit the amount of files printed.
     * @return returns the integer where the listing stopped. Used to resume finding files in the parent folder & keep the list intact
     */
    private int handleOpenFolder(File folder, int listing, int start) {
        int displayed = 0;
        File[] inDirectory = folder.listFiles();

        // We show the folder that is opened first
        formatFiles(folder, " ");
        listing += 1;

        // We also add it to our list
        this.files.add(folder);
        displayed += 1;

        for(int i = 0; i < inDirectory.length && listing - start < listLength; i++) {

            File file = inDirectory[i];

            formatFiles(file, "   ");

            this.files.add(file);
            displayed++;
            listing++;

        }

        return displayed;
    }

    /**
     * Used to print files with correct offset.
     * @param file the file that should be printed
     */
    protected void formatFiles(File file, String indent) {
        if(file.isFile())
            visual.add(indent + "- " + file.getName());
        else if(file.isDirectory())
            visual.add(indent + "+ " + file.getName());
    }

    /**
     * Determines if the number given is valid in the {@link TreeInteraction#files} array.
     * @param number the number that will be checked
     * @return a boolean determining if the number is in bounds of indices of {@link TreeInteraction#files}
     */
    protected boolean isNumberValid(int number) {
        return number > 0 && number >= start+1 && number < start+1+listLength;
    }

    /**
     * Creates an interaction object.
     * @param root the root where the tree should start. Given null it will start at root of jar.
     * @param listLength an integer which indicates how many files may be displayed in {@link TreeInteraction#getDisplay()}
     * @return a {@link TreeInteraction} object.
     */
    public static TreeInteraction interact(File root, int listLength) {

        TreeInteraction interactionManager;

        if(root == null) interactionManager = new TreeInteraction(new File(".").getAbsoluteFile(), new ArrayList<>(), listLength, 0);
        else interactionManager = new TreeInteraction(root, new ArrayList<>(), listLength, 0);

        return interactionManager;

    }

    /**
     * Returns a copy of the root path.
     */
    File getRoot() {
        return this.root;
    }

    /**
     * Returns a list of opened files
     * @return list of File objects that are opened
     */
    List<File> getOpenfiles() {
        return this.openfiles;
    }

    /**
     * Returns a list of {@link File}s that are in the {@link TreeInteraction#root}
     * @return a List of files in the directory of {@link TreeInteraction#root}
     */
    protected List<File> getFiles() {
        return this.files;
    }

    /**
     * Returns the start index for list printing
     * @return the start index
     */
    protected int getStart() {
        return this.start;
    }

}
