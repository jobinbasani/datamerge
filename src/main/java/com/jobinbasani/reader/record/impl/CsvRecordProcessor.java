package com.jobinbasani.reader.record.impl;

import com.jobinbasani.reader.RecordProcessor;
import com.jobinbasani.reader.enums.RecordType;
import com.jobinbasani.reader.record.Record;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.opencsv.ICSVWriter.*;

public class CsvRecordProcessor implements RecordProcessor {

    private static final Logger logger = LoggerFactory.getLogger(CsvRecordProcessor.class);
    private String headers;

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
            logger.info("Processed file -> {}",reportFile.toPath());
            return records;
        } catch (IOException e) {
            logger.error("Error processing records - ", e);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public void writeRecords(List<Record> records, File outputFile) {
        try {
            if(outputFile.isDirectory()){
                Files.createDirectories(outputFile.toPath());
                outputFile = Paths.get(outputFile.toPath().toString(), String.format("output_%d.csv", LocalDateTime.now().getNano())).toFile();
            }else{
                Files.createDirectories(outputFile.toPath().getParent());
            }
        } catch (IOException e) {
            logger.error("Error creating output directory structure",e);
            return;
        }
        try(CSVWriter csvWriter = new CSVWriter(Files.newBufferedWriter(outputFile.toPath()), DEFAULT_SEPARATOR, NO_QUOTE_CHARACTER, DEFAULT_ESCAPE_CHARACTER, RFC4180_LINE_END)) {
            Optional.ofNullable(headers)
                    .map(s -> s.split(String.valueOf(DEFAULT_SEPARATOR)))
                    .ifPresent(strings -> csvWriter.writeNext(strings,false));
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(csvWriter)
                    .build();
            beanToCsv.write(records);
            logger.info("Output file saved at {}", outputFile.getCanonicalPath());
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            logger.error("Error writing output - ", e);
        }
    }

    public CsvRecordProcessor withHeaders(String headers) {
        this.headers = headers;
        return this;
    }
}
