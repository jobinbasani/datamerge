package com.jobinbasani.reader.record.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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

public class XmlRecordProcessor implements RecordProcessor {

    private static final Logger logger = LoggerFactory.getLogger(XmlRecordProcessor.class);

    @Override
    public RecordType getRecordType() {
        return RecordType.XML;
    }

    @Override
    public List<Record> getRecords(File reportFile) {

        XmlMapper mapper = new XmlMapper();
        mapper.findAndRegisterModules();
        mapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
        logger.info("Processing file -> {}",reportFile.toPath());
        try(Reader reader = Files.newBufferedReader(reportFile.toPath())) {
            List<ReportRecord> entries = mapper.readValue(reader, new TypeReference<List<ReportRecord>>() {});
            return new ArrayList<>(entries);
        } catch (IOException e) {
            logger.error("Error processing XML Report - ", e);
        }
        return Collections.EMPTY_LIST;
    }
}
