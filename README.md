# Data Merge
Implementation of [datamerge](https://github.com/kyleboyle/datamerge) @kyleboyle

An interface is created to handle the reading(optionally writing too) of reports in different formats.
3 implementations are provided to handle the reading of json, xml and csv reports. These implementations are injected to ```com.jobinbasani.DataMergeProcessor``` which will invoke them to generate the final list of records.

The final output merges records from all files, removes entries where packets-serviced equal to zero, and sorts them by request-time in ascending order

Implementation is not coupled to filenames, directories etc. By default it looks in the current directory for suitable files, but you can pass arguments to specify the location of reports and the output file path.

## Libraries used
* [args4j](https://github.com/kohsuke/args4j) - makes it easy to parse command line options/arguments
* [logback](http://logback.qos.ch/) - for logging
* [opencsv](http://opencsv.sourceforge.net/) - for reading and writing csv files
* [jackson](https://github.com/FasterXML/jackson) - for parsing both json and xml files
* [JUnit 5](https://junit.org/junit5/) - for testing

## Execution
Run below maven command to compile and package the application.
```
mvn clean package
```
This will create a runnable jar file in target folder.
Alternatively, you can run the application with maven itself
```
mvn exec:java
```
This will execute the application using the default options - it will look at the current directory to parse the report files.
To pass the source directory and output file location, use the below example command.
```
mvn exec:java -Dexec.args="-source /Users/username/Desktop/reports -output /Users/username/Desktop/output.csv"
```
Replace the ```-source``` and ```-output``` with correct values.

Additionally, you can execute the ```com.jobinbasani.DataMergeRunner``` class directly too. The ```-source``` and ```-output``` arguments can be passed here too.
After execution, it prints a summary that groups the records by Service GUID and shows the number of records associated with it.


[jobinbasani.com](https://jobinbasani.com)
