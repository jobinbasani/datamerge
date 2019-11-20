package com.jobinbasani.reader.record;

import java.time.ZonedDateTime;

public interface Record {
    String getClientAddress();
    String getClientGuid();
    ZonedDateTime getRequestTime();
    String getServiceGuid();
    int getRetriesRequest();
    int getPacketsRequested();
    int getPacketsServiced();
    int getMaxHoleSize();
}
