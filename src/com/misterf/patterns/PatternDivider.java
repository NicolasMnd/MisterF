package com.misterf.patterns;

import java.util.ArrayList;
import java.util.List;

public class PatternDivider extends PatternObject {

    /**
     * The string value of this part
     */
    private String divider;
    private final List<PatternObject> children;

    PatternDivider(String content, String divider, String delimeters) {
        super(content, delimeters);
        this.divider = divider;
        this.children = setChildren();
    }

    /**
     * Returns the divider of this pattern.
     * @return {@link PatternDivider#divider}
     */
    String getDivider() {
        return new String(this.divider);
    }

    /**
     * Sets the children of this object. Will look over the whole current {@link PatternObject#getRepresentation()} and find any
     * {@link PatternObject#getDelimeters()}. In case the current {@link PatternDivider#divider} is directly found next, then the children
     * will be directly set.
     * @return a {@link java.util.List} object containing {@link PatternObject} objects.
     */
    private List<PatternObject> setChildren() {

        List<PatternObject> patternObjectList = new ArrayList<>();
        int snipped = 0;

        // Loop over each character
        for(int i = 0; i < this.getRepresentation().length(); i++) {

            // If we encounter a delimeter, then we add a child.
            if(getDelimeters().contains(this.getRepresentation().substring(i, i+1))) {

                // If this class doesn't have a divider yet, we assign it here
                if(this.divider.isEmpty())
                    this.divider = this.getRepresentation().substring(i, i+1);

                // Case 1: the divider is the same as the divider of this class
                if(getDivider().equals(this.getRepresentation().substring(i, i+1))) {
                    patternObjectList.add(
                            new PatternString(
                                    this.getRepresentation().substring(snipped, i),
                                    getDelimeters()
                            )
                    );
                    // We update our i.
                    snipped = i+1;
                } else {
                    // Case 2: the divider is different, so we create a child that contains new
                    patternObjectList.add(
                            new PatternDivider(
                                    // Representation from our last string up to the end
                                    this.getRepresentation().substring(snipped),
                                    this.getRepresentation().substring(i, i + 1),
                                    getDelimeters()
                            )
                    );
                    // We update our i.
                    snipped = i+1;
                    break;
                }

            }

            // If we got out of the loop, then we have only encountered delimeters of type defined in class field, meaning that the last of our string:
            // abc/def/ghi -> ghi is not added
            if(i == this.getRepresentation().length() - 1)
                patternObjectList.add(
                        new PatternString(
                                this.getRepresentation().substring(snipped),
                                getDelimeters()
                        )
                );

        }

        return patternObjectList;

    }

    @Override
    List<PatternObject> getChildren() {
        return this.children;
    }

    @Override
    public String construct() {
        String construct = "";
        for (int i = 0; i < getChildren().size(); i++){
            construct += getChildren().get(i).construct();
            if(i < getChildren().size() - 1) construct += divider;
        }
        return construct;
    }

    @Override
    public void modify(int part, String s) {
        if(children.size() <= part) {
            this.children.get(part).modify(0, s);
        } else {

        }
        return;
    }

}
