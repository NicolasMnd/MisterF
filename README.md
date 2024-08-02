## MisterF

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

   
