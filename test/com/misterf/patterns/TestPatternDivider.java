package com.misterf.patterns;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class TestPatternDivider {

    @Test
    public void testTypeReturned() {

        String abc = "abc/def:ghi";
        PatternObject div = PatternObject.getPatternHelper(abc, "/:");
        assertInstanceOf(PatternDivider.class, div);

        abc = "abcdef";
        div = PatternObject.getPatternHelper(abc, "/:");
        assertInstanceOf(PatternString.class, div);

    }

    @Test
    public void testChildren() {
        String abc = "abc/def:ghi";
        PatternObject div = PatternObject.getPatternHelper(abc, "/:");

        assertEquals(div.getChildren().size(), 2);
        assertEquals(div.getChildren().get(1).getChildren().size(), 2);

        assertEquals(div.getChildren().get(0).getRepresentation(), "abc");
        assertEquals(div.getChildren().get(1).getChildren().get(0).getRepresentation(), "def");
        assertEquals(div.getChildren().get(1).getChildren().get(1).getRepresentation(), "ghi");

        abc = "abc/def/ghi/";
        div = PatternObject.getPatternHelper(abc, "/:");
        assertEquals(div.getChildren().size(), 4);
        assertEquals(div.getChildren().get(0).getRepresentation(), "abc");
        assertEquals(div.getChildren().get(1).getRepresentation(), "def");
        assertEquals(div.getChildren().get(2).getRepresentation(), "ghi");
        assertEquals(div.getChildren().get(3).getRepresentation(), "");
    }

    @Test
    public void testConstruct() {
        String abc = "abc/def:ghi";
        PatternObject div = PatternObject.getPatternHelper(abc, "/:");

        assertEquals(div.construct(), "abc/def:ghi");
    }

    @Test
    public void testModify() {

        String abc = "abc/def:ghi";
        PatternObject div = PatternObject.getPatternHelper(abc, "/:");

        div.getChildren().get(0).modify("piemel");
        assertEquals(div.construct(), "piemel/def:ghi");

    }



}
