## MisterF: files

MisterF stands for mister file. This library offers execution of low level structuring and retrieval of data in text files. Simple data storage for terminal applications can 
be executed swiftly by prodiving following functionality:

``MisterF`` is instantiated with a path of a file on your computer. MisterF will then search for data by looking line per line. Each line is inspected and split by a **delimeter**.

Functionality:
1) ``String getValue(String,String)``
   Retrieve a value of a line for a given key. When file contains data "foo/bar", and getValue("/",foo) is executed, it will return _bar_

2) ``List<String> read()``
   Retrieves all lines.

3) ``clear()``
   Removes all data from the file.

4) ``Map<String, String> readSplit(String)``
   For a given delimeter, split each line in two parts after the first delimeter. If a line does not contain a delimeter, the value in the map will be null.

5) ``overwrite(List<String>)``
   Remove all values in the file and write the parameter list.

6) ``List<String> mend(String,Map<String,String>)``
   Given a delimeter and a map of String, create a list of strings by doing: getKey() + "/" + getValue() for each entry in the map.

7) ``replaceEntry(String,String,String)``
   Given a delimeter, key and value, replace value the value of the given key with the parameter replacement. The modified replacement is the full string after the first occurence of the delimeter.

## MisterF: file browser

MisterF provides a class ``TreeInteraction``, which will create a ``List<String>`` representing the file structure of the root folder. It allows basic interaction with it: next, previous, open, close, launch and selecting files.

## MisterF: pattern checker

MisterF provides a class ``PatternObject``, which creates a composite structural design pattern given a certain String object. This functionality is used by using the static getter ``PatternObject#getPatternHelper(String,String)``.
First parameter contains the full String object that you want to split into a pattern. The second parameter contains all the delimeters (for example "/:*").

Example in tests is as follows: "abc/def:ghi", "/:"
After given this to the static getter, it will return a ``PatternDivider``, since the algorithm found a delimeter that **splits** the string as follows "abc" and "def:ghi"
"abc" will be contained in a ``PatternString``, since no delimeters were found.
"def:ghi" will be contained in a ``PatternDivider``, since delimter **:** was found. This divider will have two ``PatternString`` **children** being "def" and "ghi" 
So, "abc" and "def:ghi" are **children** of the ``PatternDivider`` retrieved from ``PatternObject#getPatternHelper(String,String)``.

This ``PatternObject`` being edited using ``PatternObject#modify(int,String)``. For ``PatternDivider``, a certain index of the children will be replaced by the parameter string.
Finally, after edits you can call ``PatternObject#construct`` to get the full string.
