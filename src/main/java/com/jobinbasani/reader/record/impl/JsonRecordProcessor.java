package com.jobinbasani.reader.record.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobinbasani.reader.RecordProcessor;
import com.jobinbasani.reader.enums.RecordType;
import com.jobinbasani.reader.record.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class JsonRecordProcessor implements RecordProcessor {
    private static final Logger logger = LoggerFactory.getLogger(JsonRecordProcessor.class);

    @Override
    public RecordType getRecordType() {
        return RecordType.JSON;
    }

    @Override
    public List<Record> getRecords(File reportFile) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        try(Reader fileReader = Files.newBufferedReader(reportFile.toPath())) {
            List<ReportRecord> records = mapper.readValue(fileReader, new TypeReference<List<ReportRecord>>(){});
            logger.info("Processed file -> {}",reportFile.toPath());
            return new ArrayList<>(records);
        } catch (IOException e) {
            logger.error("Error parsing JSON report - ", e);
        }
        return Collections.EMPTY_LIST;
    }
}
