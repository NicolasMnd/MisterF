package com.misterf.core;

import java.io.*;
import java.util.*;

public class MisterF {

    private final File file;
    final String path;

    public MisterF(String path) {
        this.file = new File(path);
        this.path = path;
    }

    /**
     * Replaces an entry in the file
     * @param delimeter the delimeter used to split the entry from key an value
     * @param key the key on which the value should be replaced
     * @param replace the new value for the given key
     */
    public void replaceEntry(String delimeter, String key, String replace) {

        // Clear file & get contents
        Map<String, String> map = readSplit(delimeter);
        clear();

        // Loop over the entries & replace the specified entry
        for(Map.Entry<String, String> entry : map.entrySet())

            if(entry.getKey().toLowerCase().strip().equals(key))

                map.replace(key, replace);

        // Overwrite the whole file with the modified map
        overwrite(mend(delimeter, map));

    }

    /**
     * Returns the value of the key. Returns null if key not found
     * @param delimeter the value of which each line is split
     * @param key the key that is looked for in the file.
     * @return null if entry not found, else the value of the key
     */
    public String getValue(String delimeter, String key) {

        Map<String, String> map = readSplit(delimeter);

        for(Map.Entry<String, String> entry : map.entrySet())
            if(entry.getKey().toLowerCase().strip().equals(key))
                return entry.getValue();

        return null;

    }

    /**
     * Reads the contents of the file & puts them in a map based on the delimeter
     * @param delimeter the string that is used to split each line
     * @return returns the map
     */
    public Map<String, String> readSplit(String delimeter) {

        Map<String, String> map = new HashMap<>();
        List<String> content = read();

        for(String line : content) {

            List<String> arr = new LinkedList<String>(Arrays.asList(line.split(delimeter)));
            String beg = arr.remove(0);

            // We removed 1 from the array, so if it is still 1, then there were 2 or more arguments.
            if(arr.size() >= 1)
                map.put(beg, mend(delimeter, arr));
            else map.put(arr.get(0), null);

        }

        return map;

    }

    /**
     * Reads the file and puts the strings in a list.
     * @return a list with strings per line
     */
    public List<String> read() {
        try {

            List<String> returnList = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(this.file));
            String line = null;

            while((line = reader.readLine()) != null) {
                returnList.add(line);
            }

            return returnList;

        } catch (IOException e) {

            System.out.println("[MisterF]: Error occured while reading in file");

        }

        return null;
    }

    /**
     * Clears the text file
     */
    public void clear() {
        overwrite(null);
    }

    /**
     * Overwrites the whole file with a given list of lines
     * @param lines the list of lines that will be in the file
     */
    public void overwrite(List<String> lines) {

        try {

            FileWriter writer = new FileWriter(this.file);
            if(lines == null) {
                writer.write("");
                writer.close();
                return;
            }

            for(String line : lines)
                writer.write(line + "\n");
            writer.close();

        } catch(IOException io) {

            System.out.println("[MisterF]: Error occured while writing in file");

        }

    }

    /**
     * Appends given line at the end of the file
     * @param line the line that is to be appended
     */
    public void append(String line) {

        try {

            FileWriter writer = new FileWriter(this.file, true);
            writer.write(line);
            writer.close();

        } catch(IOException io) {

            System.out.println("[MisterF]: Error occured while appending in file");

        }

    }

    /**
     * Mends the list back together.
     * @param delimeter
     * @return
     */
    private String mend(String delimeter, List<String> list) {

        String s = "";

        for(int i = 0; i < list.size(); i++) {
            s += list.get(i);
            if(i < list.size() - 1) s += delimeter;
        }

        return s;

    }

    /**
     * Mends a {@link Map} with a delimeter in a string list.
     * @param delimeter Mends a string back together when split by {@link MisterF#readSplit(String)}
     * @param map the map retrieved from {@link MisterF#readSplit(String)}
     * @return a {@link List} with the mended string using the delimeter
     */
    public List<String> mend(String delimeter, Map<String, String> map) {

        List<String> list = new ArrayList<>();

        for(Map.Entry<String, String> entry : map.entrySet())
            list.add(entry.getKey() + delimeter + entry.getValue());

        return list;

    }

}
