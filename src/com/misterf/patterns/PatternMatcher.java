package com.misterf.patterns;

/**
 * File analyser.
 * It will analyse a file path & check it contents.
 * Given a set of delimeters, it will be able to split up the file into logical parts
 * where the user can specify which parts are keys & values.
 * Then it will offer functionality like removing & adding
 */
public class PatternMatcher {

    PatternMatcher(String path, String delimeters) {

    }


    public static PatternObject getPatternObject(String pattern) {
        return null;
    }

    public static PatternMatcher getPatternHelper(String path, String delimeters) {

        if(delimeters == null)
            return new PatternMatcher(path, "/,:;");
        else return new PatternMatcher(path, delimeters);

    }

}
