package com.misterf.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestMisterF {

    String path;
    MisterF fileReader;

    @BeforeEach
    public void setVars() {
        path = "res/test.txt";
        write(path, "hello1/hello2/hello3\nh1/h2/h3");
        fileReader = new MisterF(path);
    }

    @Test
    public void testConstructor() {

        write(path, "hello1/hello2/hello3\nh1/h2/h3");
        List<String> lines = fileReader.read();

        assertEquals(lines.size(),2);
        Map<String, String> str = fileReader.readSplit("/");

        Set<Map.Entry<String, String>> entrySet = str.entrySet();
        List<Map.Entry<String, String>> entryList = new ArrayList<>(entrySet);

        for(int i = 0; i < entryList.size(); i++) {
            Map.Entry<String, String> entry = entryList.get(i);
            String key = entry.getKey();
            String value = entry.getValue();

            if(i == 0) assertEquals(value, "hello2/hello3");
            if(i == 1) assertEquals(value, "h2/h3");
        }

    }

    @Test
    public void testTwoArgsPerLine() {

        write(path, "hello1/hello2\nh1/h2");
        List<String> lines = fileReader.read();

        assertEquals(lines.size(),2);
        Map<String, String> str = fileReader.readSplit("/");

        Set<Map.Entry<String, String>> entrySet = str.entrySet();
        List<Map.Entry<String, String>> entryList = new ArrayList<>(entrySet);

        for(int i = 0; i < entryList.size(); i++) {
            Map.Entry<String, String> entry = entryList.get(i);
            String key = entry.getKey();
            String value = entry.getValue();

            if(i == 0) assertEquals(value, "hello2");
            if(i == 1) assertEquals(value, "h2");
        }

    }

    @Test
    public void readSplitEmpty() {
        write(path, "hello1/hello2\nh1/");
        Map<String, String> str = fileReader.readSplit("/");

        Set<Map.Entry<String, String>> entrySet = str.entrySet();
        List<Map.Entry<String, String>> entryList = new ArrayList<>(entrySet);

        for(int i = 0; i < entryList.size(); i++) {
            Map.Entry<String, String> entry = entryList.get(i);
            String value = entry.getValue();

            if(i == 0) assertEquals(value, "hello2");
            if(i == 1) assertNull(value);
        }
    }

    @Test
    public void testReplace() {

        Map<String, String> str = fileReader.readSplit("/");

        Set<Map.Entry<String, String>> entrySet = str.entrySet();
        List<Map.Entry<String, String>> entryList = new ArrayList<>(entrySet);
        fileReader.replaceEntry("/", "hello1", "hello4/hello5");

        str = fileReader.readSplit("/");
        entrySet = str.entrySet();
        entryList = new ArrayList<>(entrySet);

        for(int i = 0; i < entryList.size(); i++) {
            Map.Entry<String, String> entry = entryList.get(i);
            String value = entry.getValue();

            if(i == 0) assertEquals(value, "hello4/hello5");
            if(i == 1) assertEquals(value, "h2/h3");
        }

    }

    @Test
    public void testGetValue() {

        assertEquals(null, fileReader.getValue("/", "helo4"));
        assertEquals("hello2/hello3", fileReader.getValue("/", "hello1"));
        assertEquals("h2/h3", fileReader.getValue("/", "h1"));

    }

    private void write(String path, String string) {
        try {
            FileWriter writer = new FileWriter(new File(path));
            writer.write(string);
            writer.close();
        } catch(IOException e) {
            System.out.println("[TestMisterF]: Error while writing in file");
        }
    }

}
