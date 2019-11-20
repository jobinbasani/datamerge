package com.jobinbasani.reader.record;

import java.time.LocalDateTime;

public interface Record {
    String getClientAddress();
    String getClientGuid();
    LocalDateTime getRequestTime();
    String getServiceGuid();
    int getRetriesRequest();
    int getPacketsRequested();
    int getPacketsServiced();
    int getMaxHoleSize();
}
