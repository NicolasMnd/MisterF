package com.misterf.patterns;

import java.util.List;

public class PatternString extends PatternObject {

    private List<PatternObject> patternObjects;

    PatternString(String content, String delimeters) {
        super(content, delimeters);
    }

    /**
     * A string has no children itself. Will always return null;
     * @return null
     */
    @Override
    List<PatternObject> getChildren() {
        return null;
    }

    @Override
    public String construct() {
        return getRepresentation();
    }

    @Override
    public void modify(String s) {
        this.representation = s;
    }

}
