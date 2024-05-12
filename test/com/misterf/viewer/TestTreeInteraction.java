package com.misterf.viewer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTreeInteraction {

    TreeInteraction interactionRoot;
    TreeInteraction interaction;

    @BeforeEach
    public void setVars() {
        this.interaction = TreeInteraction.interact(new File("res/testdir"), 30);
        this.interactionRoot = TreeInteraction.interact(null, 30);
    }

    @Test
    public void testConstructor() {

        assertEquals(interactionRoot.getRoot().getName(), ".");
        assertEquals(interaction.getRoot().getName(), "testdir");

    }

    @Test
    public void testList() {

        this.interaction.compose();
        assertEquals(interaction.getFiles().size(), 1);
        assertEquals(interaction.getOpenfiles().size(), 0);

        this.interaction.openFolder(1);
        assertEquals(interaction.getFiles().size(), 4+1);

        assertEquals(interaction.getOpenfiles().size(), 1);

        this.interaction.closeFolder(0);
        assertEquals(interaction.getFiles().size(), 4+1);
        assertEquals(interaction.getOpenfiles().size(), 1);

        this.interaction.closeFolder(1);
        assertEquals(interaction.getFiles().size(), 2+1);
        assertEquals(interaction.getOpenfiles().size(), 0);

    }

    @Test
    public void testCommands() {

        // Opening files
        this.interaction.compose();
        assertEquals(interaction.getOpenfiles().size(), 0);
        assertEquals(interaction.getFiles().size(), 2+1);
        this.interaction.command("o", 1);
        assertEquals(interaction.getOpenfiles().size(), 1);
        assertEquals(interaction.getFiles().size(), 4+1);

        // Close file
        this.interaction.command("c", 1);
        assertEquals(interaction.getOpenfiles().size(), 0);
        assertEquals(interaction.getFiles().size(), 2+1);

        // Focus file after opening
        this.interaction.command("o", 1);
        this.interaction.command("f", 1);
        assertEquals(interaction.getOpenfiles().size(), 0);
        assertEquals(interaction.getFiles().size(), 2+1);
        this.interaction.command("b", 0);

        // Open file in open file
        this.interaction.command("o", 1);
        assertEquals(interaction.getOpenfiles().size(), 1);
        assertEquals(interaction.getFiles().size(), 3);
        this.interaction.command("o", 1); // open file in openfiles
        assertEquals(interaction.getOpenfiles().size(), 1); // do nothing to openfiles array
        this.interaction.command("o", 2); // open non directory
        assertEquals(interaction.getOpenfiles().size(), 2); // no change
        this.interaction.command("o", 3); // open directory in already open directory
        assertEquals(interaction.getOpenfiles().size(), 2); // focus on new file: no openfiles anymore
        assertEquals(interaction.getFiles().size(), 4); // amount of files in sub2 = 1

        // Go back to start:
        this.interaction.command("b", 1);
        this.interaction.command("b", 1);
        assertEquals(interaction.getOpenfiles().size(), 2);



    }

}
