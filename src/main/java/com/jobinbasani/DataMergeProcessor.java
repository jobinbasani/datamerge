package com.jobinbasani;

import com.jobinbasani.reader.RecordReader;

import java.io.File;
import java.util.Collection;

public class DataMergeProcessor {
    private final Collection<File> files;
    private final Collection<RecordReader> recordReaders;

    public DataMergeProcessor(Collection<File> files, Collection<RecordReader> recordReaders) {
        this.files = files;
        this.recordReaders = recordReaders;
    }
}
