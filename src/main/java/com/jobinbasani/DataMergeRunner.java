package com.jobinbasani;

import com.jobinbasani.reader.RecordProcessor;
import com.jobinbasani.reader.record.Record;
import com.jobinbasani.reader.record.impl.CsvRecordProcessor;
import com.jobinbasani.reader.record.impl.JsonRecordProcessor;
import com.jobinbasani.reader.record.impl.XmlRecordProcessor;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

import static com.jobinbasani.reader.enums.RecordType.CSV;
import static java.util.Collections.reverseOrder;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.*;

public class DataMergeRunner {
    private static final Logger logger = LoggerFactory.getLogger(DataMergeRunner.class);

    @Option(name = "-source", usage = "Source directory of files")
    private String source;
    @Option(name = "-output", usage = "Output file name")
    private String outputFileName;
    @Option(name = "-defaultSource", hidden = true, usage = "Default source directory")
    private String defaultSource = ".";
    @Option(name="-csvheaders", usage = "Headers to be used in CSV output")
    private String csvHeaders;
    @Option(name="-defaultCsvheaders", hidden = true, usage = "Default CSV Headers")
    private String defaultCsvHeaders = "client-address,client-guid,request-time,service-guid,retries-request,packets-requested,packets-serviced,max-hole-size";

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
            logger.error("Valid arguments: -source <directory containing report files or the file itself> -output <Location where output csv is to be created>");
        }
    }

    private void runDataMerge() {
        List<RecordProcessor> recordProcessors = Arrays.asList(new JsonRecordProcessor(), new XmlRecordProcessor(),new CsvRecordProcessor().withHeaders(ofNullable(csvHeaders).orElse(defaultCsvHeaders)));
        DataMergeProcessor dataMergeProcessor = new DataMergeProcessor(ofNullable(source).orElse(defaultSource), recordProcessors);
        List<Record> records = dataMergeProcessor.getRecords();
        logger.info("Records size = {}",records.size());
        File outputFile = new File(Optional.ofNullable(outputFileName).orElse(String.format("output%soutput_%d.csv", File.separator, LocalDateTime.now().getNano())));
        dataMergeProcessor.writeRecords(records, CSV, outputFile);
        Map<String, Long> result = records.stream()
                .map(Record::getServiceGuid)
                .collect(groupingBy(Function.identity(), counting()))
                .entrySet().stream()
                .sorted(reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        logger.info("============Service GUID, No of Records============");
        result.keySet().stream().forEach(serviceId -> logger.info("{}\t{}",serviceId,result.get(serviceId)));
        logger.info("==================End of Summary==================");
    }

}
