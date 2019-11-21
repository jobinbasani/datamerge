package com.jobinbasani.tests;

import com.jobinbasani.DataMergeProcessor;
import com.jobinbasani.reader.RecordProcessor;
import com.jobinbasani.reader.record.Record;
import com.jobinbasani.reader.record.impl.CsvRecordProcessor;
import com.jobinbasani.reader.record.impl.JsonRecordProcessor;
import com.jobinbasani.reader.record.impl.XmlRecordProcessor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataMergeTest {

    private String path = "src/test/resources";

    @DisplayName("Tests the number of lines and the returned number of records in the csv file")
    @Test
    public void testCsvRecords() throws IOException {
        List<RecordProcessor> recordProcessors = Collections.singletonList(new CsvRecordProcessor());
        DataMergeProcessor dataMergeProcessor = new DataMergeProcessor(path, recordProcessors);
        List<Record> records = dataMergeProcessor.getRecords();
        long lineCount = Files.lines(Paths.get(path+"/reports.csv")).count();
        assertEquals(lineCount,4);
        assertEquals(records.size(),2);
    }

    @DisplayName("Tests the returned number of records in the xml file")
    @Test
    public void testXmlRecords() {
        List<RecordProcessor> recordProcessors = Collections.singletonList(new XmlRecordProcessor());
        DataMergeProcessor dataMergeProcessor = new DataMergeProcessor(path, recordProcessors);
        List<Record> records = dataMergeProcessor.getRecords();
        assertEquals(records.size(),4);
    }

    @DisplayName("Tests the returned number of records in the json file")
    @Test
    public void testJsonRecords() {
        List<RecordProcessor> recordProcessors = Collections.singletonList(new JsonRecordProcessor());
        DataMergeProcessor dataMergeProcessor = new DataMergeProcessor(path, recordProcessors);
        List<Record> records = dataMergeProcessor.getRecords();
        assertEquals(records.size(),3);
    }

    @DisplayName("Test all files")
    @Test
    public void testAllFiles() {
        List<RecordProcessor> recordProcessors = Arrays.asList(new JsonRecordProcessor(),new XmlRecordProcessor(),new CsvRecordProcessor());
        DataMergeProcessor dataMergeProcessor = new DataMergeProcessor(path, recordProcessors);
        List<Record> records = dataMergeProcessor.getRecords();
        assertEquals(records.size(),9);
    }
}
