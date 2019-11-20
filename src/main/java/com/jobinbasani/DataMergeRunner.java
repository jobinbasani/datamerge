package com.jobinbasani;

import com.jobinbasani.reader.RecordProcessor;
import com.jobinbasani.reader.record.Record;
import com.jobinbasani.reader.record.impl.CsvRecordProcessor;
import com.jobinbasani.reader.record.impl.JsonRecordProcessor;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.jobinbasani.reader.enums.RecordType.CSV;
import static java.util.Optional.ofNullable;

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
        List<RecordProcessor> recordProcessors = Arrays.asList(new CsvRecordProcessor(), new JsonRecordProcessor());
        DataMergeProcessor dataMergeProcessor = new DataMergeProcessor(ofNullable(source).orElse(defaultSource), recordProcessors);
        List<Record> records = dataMergeProcessor.getRecords();
        logger.info("Record size = {}",records.size());
        logger.info("Records = {}", records);
        File outputFile = new File("C:\\Users\\jobin.basani\\Downloads\\csvoutput" + LocalDateTime.now().getNano() + ".csv");
        dataMergeProcessor.writeRecords(records, CSV, outputFile);
    }

}
