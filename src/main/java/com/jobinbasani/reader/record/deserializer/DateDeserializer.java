package com.jobinbasani.reader.record.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateDeserializer extends StdDeserializer<ZonedDateTime> {
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss z";
    public DateDeserializer() {
        this(null);
    }

    protected DateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String date = jsonParser.getText();
        try{
            return ZonedDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(date)), ZoneId.systemDefault());
        }catch (NumberFormatException nfe){
            return ZonedDateTime.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
        }
    }
}
