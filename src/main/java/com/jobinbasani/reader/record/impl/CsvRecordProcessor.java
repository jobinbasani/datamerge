package com.jobinbasani.reader.record.impl;

import com.jobinbasani.DataMergeProcessor;
import com.jobinbasani.reader.RecordProcessor;
import com.jobinbasani.reader.enums.ReaderType;
import com.jobinbasani.reader.record.Record;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

public class CsvRecordProcessor implements RecordProcessor {

    private static final Logger logger = LoggerFactory.getLogger(CsvRecordProcessor.class);

    @Override
    public ReaderType getReaderType() {
        return ReaderType.CSV;
    }

    @Override
    public boolean canProcessFile(File reportFile) {
        return reportFile.isFile() && reportFile.getName().toUpperCase().endsWith("." + getReaderType().name());
    }

    @Override
    public List<Record> getRecords(File reportFile){
        try {
            CSVReader csvReader = new CSVReaderBuilder(Files.newBufferedReader(reportFile.toPath()))
                    .withSkipLines(1)
                    .build();
            List<Record> records = new CsvToBeanBuilder<Record>(csvReader)
                    .withType(ReportRecord.class)
                    .build()
                    .parse();
            csvReader.close();
            return records;
        } catch (IOException e) {
            logger.error("Error processing records - {}", e);
        }
        return Collections.EMPTY_LIST;
    }
}
