package com.misterf.patterns;

import java.util.List;

public class PatternDivider extends PatternObject {

    /**
     * The string value of this part
     */
    private final String content;

    PatternDivider(String content) {
        super(null);
        this.content = content;
    }

    @Override
    List<PatternObject> getChildren() {
        return null;
    }

}
