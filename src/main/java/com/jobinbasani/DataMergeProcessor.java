package com.jobinbasani;

import com.jobinbasani.reader.RecordReader;
import com.jobinbasani.reader.record.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataMergeProcessor {
    private final Collection<File> files;
    private final Collection<RecordReader> recordReaders;
    private static final Logger logger = LoggerFactory.getLogger(DataMergeProcessor.class);

    public DataMergeProcessor(String file, Collection<RecordReader> recordReaders) {
        this.recordReaders = recordReaders;
        this.files = getFiles(new File(file));
    }

    public List<Record> getRecords(){
        logger.info("Files = {}", files);
        return files.stream()
                .map(file -> getRecordReader(file).getRecords(file))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<File> getFiles(File sourceFile) {
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

    private RecordReader getRecordReader(File file) {
        return recordReaders.stream().filter(recordReader -> recordReader.canProcessFile(file)).findFirst().get();
    }
}
