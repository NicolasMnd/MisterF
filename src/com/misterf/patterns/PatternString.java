package com.misterf.patterns;

import java.util.List;

public class PatternString extends PatternObject {

    private List<PatternObject> patternObjects;
    private final String delimeter;

    PatternString(String content, String delimeter) {
        super(content);
        this.delimeter = delimeter;
    }

    @Override
    List<PatternObject> getChildren() {
        return null;
    }

}
