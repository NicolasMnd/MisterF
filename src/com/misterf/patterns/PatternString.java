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
    public List<String> getStrings() {
        return List.of(this.representation);
    }

    @Override
    public void modify(int part, String s) {
        this.representation = s;
    }

}
