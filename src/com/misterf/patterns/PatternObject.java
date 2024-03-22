package com.misterf.patterns;

import java.util.List;

/**
 * The pattern object is a general class of how the string will be divided in sub parts.
 */
public abstract class PatternObject {

    /**
     * Possible children that this object has.
     */
    private List<PatternObject> patterns;

    /**
     * The full reference string that this object holds.
     */
    private String representation;

    /**
     * Upon creation of this object, the full string is passed. Then the children will be created
     * depending on a delimeter found.
     * @param pattern the string that is supposed to be analysed.
     */
    public PatternObject(String pattern) {
        this.representation = pattern;
        this.patterns = getChildren();
    }

    abstract List<PatternObject> getChildren();


    /**
     * Gets a {@link PatternObject} object.
     * @param pattern The pattern we'd like to decompose
     * @return a {@link PatternString} or {@link PatternDivider} object
     */
    public static PatternObject getPatternHelper(String pattern) {
        for(int i = 0; i < pattern.length(); i++) {
            if("/.;,".contains(pattern.substring(i, i+1)))
                return new PatternString(pattern, pattern.substring(i,i+1));
        }
        return new PatternDivider(pattern);
    }

}
