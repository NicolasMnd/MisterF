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
    String representation, delimeters;

    /**
     * Upon creation of this object, the full string is passed. Then the children will be created
     * depending on a delimeter found.
     * @param pattern the string that is supposed to be analysed.
     */
    public PatternObject(String pattern, String delimeters) {
        this.representation = pattern;
        this.delimeters = delimeters;
        this.patterns = getChildren();
    }

    /**
     * The children of this object
     * @return
     */
    abstract List<PatternObject> getChildren();

    public abstract String construct();

    public abstract void modify(String s);

    /**
     * Returns the string of this object
     * @return {@link PatternObject#representation}
     */
    final String getRepresentation() {
        return new String(this.representation);
    }

    /**
     * Returns the delimeters
     * @return {@link PatternObject#delimeters}
     */
    final String getDelimeters() {
        return new String(this.delimeters);
    }

    /**
     * Gets a {@link PatternObject} object.
     * @param pattern The pattern we'd like to decompose
     * @return a {@link PatternString} or {@link PatternDivider} object
     */
    public static PatternObject getPatternHelper(String pattern, String delimeters) {
        PatternDivider divider = new PatternDivider(pattern, "", delimeters);
        if(divider.getChildren().size() == 1) return new PatternString(pattern, delimeters);
        else return divider;
    }

}
