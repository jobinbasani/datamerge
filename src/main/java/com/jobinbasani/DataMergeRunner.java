package com.jobinbasani;

import com.jobinbasani.reader.RecordReader;
import com.jobinbasani.reader.record.Record;
import com.jobinbasani.reader.record.impl.CsvRecordReader;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        DataMergeProcessor dataMergeProcessor = new DataMergeProcessor(Optional.ofNullable(source).orElse(defaultSource),recordReaders);
        List<Record> records = dataMergeProcessor.getRecords();
        logger.info("Record size = {}",records.size());
    }

}
