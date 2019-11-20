package com.jobinbasani.reader.record.impl;

import com.jobinbasani.reader.RecordProcessor;
import com.jobinbasani.reader.enums.RecordType;
import com.jobinbasani.reader.record.Record;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

import static com.opencsv.ICSVWriter.*;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class CsvRecordProcessor implements RecordProcessor {

    private static final Logger logger = LoggerFactory.getLogger(CsvRecordProcessor.class);

    @Override
    public RecordType getRecordType() {
        return RecordType.CSV;
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
            logger.error("Error processing records - ", e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public void writeRecords(List<Record> records, File inputReferenceFile, File outputFile) {
        try(CSVWriter csvWriter = new CSVWriter(Files.newBufferedWriter(outputFile.toPath(), CREATE, APPEND), DEFAULT_SEPARATOR, NO_QUOTE_CHARACTER, DEFAULT_ESCAPE_CHARACTER, RFC4180_LINE_END);
            CSVReader csvReader = new CSVReader(Files.newBufferedReader(inputReferenceFile.toPath()))) {
            MappingStrategy<ReportRecord> strategy = new FuzzyMappingStrategy<>();
            strategy.setType(ReportRecord.class);
            strategy.captureHeader(csvReader);
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(csvWriter)
                    .withMappingStrategy(strategy)
                    .build();
            beanToCsv.write(records);
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            logger.error("Error writing output - ", e);
        }
    }
}
