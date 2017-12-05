package com.wisdom.framework.core.util.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonDateStringSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator jgen,
                          SerializerProvider provider) throws IOException,
            JsonProcessingException {
        SimpleDateFormat formatter10 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatter8 = new SimpleDateFormat("yyyyMMdd");
        if (null == value) {
            jgen.writeString("");
        } else {
            int length = value.length();
            Date date = null;
            try {
                switch (length) {
                    case 8:
                        date = formatter8.parse(value);
                        break;
                    case 6:
                        date = formatter8.parse(value + "01");
                        break;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (null == date) {
                jgen.writeString(value);
            } else {
                String formattedDate = formatter10.format(date);
                if (6 == length) formattedDate = formatter6.format(date);
                jgen.writeString(formattedDate);
            }

        }
    }
}