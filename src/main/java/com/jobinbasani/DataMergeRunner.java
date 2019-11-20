package com.jobinbasani;

import com.jobinbasani.reader.RecordReader;
import com.jobinbasani.reader.record.Record;
import com.jobinbasani.reader.record.impl.CsvRecordReader;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataMergeRunner {
    private static final Logger logger = LoggerFactory.getLogger(DataMergeRunner.class);

    @Option(name = "-source", usage = "Source directory of files")
    private String source;
    @Option(name = "-defaultSource", hidden = true, usage = "Default source directory")
    private String defaultSource = ".";

    public static void main(String[] args) {
        new DataMergeRunner().runMain(args);
    }

    private void runMain(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            runDataMerge();
        } catch (CmdLineException e) {
            logger.error("Error parsing arguments!");
            logger.error("Valid arguments: -source ");
        }
    }

    private void runDataMerge() {
        List<RecordReader> recordReaders = Collections.singletonList(new CsvRecordReader());
        List<File> fileList = getFiles(new File(Optional.ofNullable(source).orElse(defaultSource)), recordReaders);
        logger.info("Files = {}", fileList);
        List<Record> records = fileList.stream()
                .map(file -> getRecordReader(recordReaders, file).getRecords(file))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        logger.info("Records size = {}", records.size());
    }

    private List<File> getFiles(File sourceFile, List<RecordReader> recordReaders) {
        List<File> files = new ArrayList<>();
        logger.info("Source = {}", sourceFile.getAbsolutePath());
        if (sourceFile.exists() && sourceFile.isFile()) {
            files.add(sourceFile);
        } else if (sourceFile.exists() && sourceFile.isDirectory()) {
            List<File> supportedFiles = Stream.of(sourceFile.listFiles())
                    .filter(file -> recordReaders.stream().anyMatch(recordReader -> recordReader.canProcessFile(file)))
                    .collect(Collectors.toList());
            files.addAll(supportedFiles);
        }
        return files;
    }

    private RecordReader getRecordReader(List<RecordReader> recordReaders, File file) {
        return recordReaders.stream().filter(recordReader -> recordReader.canProcessFile(file)).findFirst().get();
    }

}
