package com.jobinbasani.reader.record.impl;

import com.jobinbasani.reader.record.Record;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import java.time.LocalDateTime;

public class ReportRecord implements Record {

    @CsvBindByPosition(position = 0)
    private String clientAddress;
    @CsvBindByPosition(position = 1)
    private String clientGuid;
    @CsvBindByPosition(position = 2)
    @CsvDate("yyyy-MM-dd HH:mm:ss z")
    private LocalDateTime requestTime;
    @CsvBindByPosition(position = 3)
    private String serviceGuid;
    @CsvBindByPosition(position = 4)
    private int retriesRequest;
    @CsvBindByPosition(position = 5)
    private int packetsRequested;
    @CsvBindByPosition(position = 6)
    private int packetsServiced;
    @CsvBindByPosition(position = 7)
    private int maxHoleSize;

    @Override
    public String getClientAddress() {
        return clientAddress;
    }

    @Override
    public String getClientGuid() {
        return clientGuid;
    }

    @Override
    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    @Override
    public String getServiceGuid() {
        return serviceGuid;
    }

    @Override
    public int getRetriesRequest() {
        return retriesRequest;
    }

    @Override
    public int getPacketsRequested() {
        return packetsRequested;
    }

    @Override
    public int getPacketsServiced() {
        return packetsServiced;
    }

    @Override
    public int getMaxHoleSize() {
        return maxHoleSize;
    }

    @Override
    public String toString() {
        return "ReportRecord{" +
                "clientAddress='" + clientAddress + '\'' +
                ", clientGuid='" + clientGuid + '\'' +
                ", requestTime=" + requestTime +
                ", serviceGuid='" + serviceGuid + '\'' +
                ", retriesRequest=" + retriesRequest +
                ", packetsRequested=" + packetsRequested +
                ", packetsServiced=" + packetsServiced +
                ", maxHoleSize=" + maxHoleSize +
                '}';
    }
}
