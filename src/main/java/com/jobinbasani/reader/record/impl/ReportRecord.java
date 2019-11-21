package com.jobinbasani.reader.record.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.jobinbasani.reader.record.Record;
import com.jobinbasani.reader.record.deserializer.DateDeserializer;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import java.time.ZonedDateTime;
import java.util.Objects;

import static com.jobinbasani.reader.record.deserializer.DateDeserializer.DATE_FORMAT;

@JacksonXmlRootElement(localName = "records")
public class ReportRecord implements Record {

    @CsvBindByPosition(position = 0)
    private String clientAddress;

    @CsvBindByPosition(position = 1)
    private String clientGuid;

    @CsvBindByPosition(position = 2)
    @CsvDate(DATE_FORMAT)
    private ZonedDateTime requestTime;

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
    public ZonedDateTime getRequestTime() {
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

    @JsonProperty("client-address")
    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    @JsonProperty("client-guid")
    public void setClientGuid(String clientGuid) {
        this.clientGuid = clientGuid;
    }

    @JsonProperty("request-time")
    @JsonDeserialize(using = DateDeserializer.class)
    public void setRequestTime(ZonedDateTime requestTime) {
        this.requestTime = requestTime;
    }

    @JsonProperty("service-guid")
    public void setServiceGuid(String serviceGuid) {
        this.serviceGuid = serviceGuid;
    }

    @JsonProperty("retries-request")
    public void setRetriesRequest(int retriesRequest) {
        this.retriesRequest = retriesRequest;
    }

    @JsonProperty("packets-requested")
    public void setPacketsRequested(int packetsRequested) {
        this.packetsRequested = packetsRequested;
    }

    @JsonProperty("packets-serviced")
    public void setPacketsServiced(int packetsServiced) {
        this.packetsServiced = packetsServiced;
    }

    @JsonProperty("max-hole-size")
    public void setMaxHoleSize(int maxHoleSize) {
        this.maxHoleSize = maxHoleSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportRecord that = (ReportRecord) o;
        return retriesRequest == that.retriesRequest &&
                packetsRequested == that.packetsRequested &&
                packetsServiced == that.packetsServiced &&
                maxHoleSize == that.maxHoleSize &&
                clientAddress.equals(that.clientAddress) &&
                clientGuid.equals(that.clientGuid) &&
                requestTime.equals(that.requestTime) &&
                serviceGuid.equals(that.serviceGuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientAddress, clientGuid, requestTime, serviceGuid, retriesRequest, packetsRequested, packetsServiced, maxHoleSize);
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
