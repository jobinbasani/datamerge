package com.jobinbasani;

import com.jobinbasani.reader.RecordProcessor;
import com.jobinbasani.reader.enums.RecordType;
import com.jobinbasani.reader.record.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataMergeProcessor {
    private final Collection<File> files;
    private final Collection<RecordProcessor> recordProcessors;
    private static final Logger logger = LoggerFactory.getLogger(DataMergeProcessor.class);

    public DataMergeProcessor(String file, Collection<RecordProcessor> recordProcessors) {
        this.recordProcessors = recordProcessors;
        this.files = getFiles(new File(file));
    }

    public List<Record> getRecords(){
        logger.info("Files = {}", files);
        return files.stream()
                .map(file -> getRecordReader(file).getRecords(file))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet())
                .stream()
                .filter(record -> record.getPacketsServiced()>0)
                .sorted(Comparator.comparing(Record::getRequestTime))
                .collect(Collectors.toList());
    }

    public void writeRecords(List<Record> records, RecordType recordType, File outputFile){
        recordProcessors.stream().filter(recordProcessor -> recordProcessor.getRecordType() == recordType)
                .findFirst()
                .ifPresent(recordProcessor -> recordProcessor.writeRecords(records, outputFile));
    }

    private List<File> getFiles(File sourceFile) {
        List<File> files = new ArrayList<>();
        logger.info("Source = {}", sourceFile.getAbsolutePath());
        if (sourceFile.exists() && sourceFile.isFile()) {
            files.add(sourceFile);
        } else if (sourceFile.exists() && sourceFile.isDirectory() && sourceFile.listFiles() != null) {
            List<File> supportedFiles = Stream.of(sourceFile.listFiles())
                    .filter(file -> recordProcessors.stream().anyMatch(recordProcessor -> recordProcessor.canProcessFile(file)))
                    .collect(Collectors.toList());
            files.addAll(supportedFiles);
        }
        return files;
    }

    private RecordProcessor getRecordReader(File file) {
        return recordProcessors.stream().filter(recordProcessor -> recordProcessor.canProcessFile(file)).findFirst().get();
    }
}
