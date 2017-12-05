package com.wisdom.framework.core.util.json.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Hyberbin
 * Date: 13-10-30
 * Time: 下午6:55
 * To change this template use File | Settings | File Templates.
 */
public class JsonDateTimeDeserializer extends JsonDeserializer<Date> {
    protected transient final static Logger log = LoggerFactory.getLogger(JsonDateTimeDeserializer.class);
    /**
     * Method that can be called to ask implementation to deserialize
     * JSON content into the value type this serializer handles.
     * Returned instance is to be constructed by method itself.
     * <p/>
     * Pre-condition for this method is that the parser points to the
     * first event that is part of value to deserializer (and which
     * is never JSON 'null' literal, more on this below): for simple
     * types it may be the only value; and for structured types the
     * Object start marker.
     * Post-condition is that the parser will point to the last
     * event that is part of deserialized value (or in case deserialization
     * fails, event that was not recognized or usable, which may be
     * the same event as the one it pointed to upon call).
     * <p/>
     * Note that this method is never called for JSON null literal,
     * and thus deserializers need (and should) not check for it.
     *
     * @param parser   Parsed used for reading JSON content
     * @param ctxt Context that can be used to access information about
     *             this deserialization activity.
     * @return Deserializer value
     */
    private final static String defaultDate="2013-01-01 00:00:00";
    private final static int defaultLength=19;
    @Override
    public Date deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String dateFormat= "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf= new SimpleDateFormat(dateFormat);
        String fieldData= parser.getText();
        if(fieldData.length()==0){
            return null;
        }else if(fieldData.length()!=defaultLength){
            fieldData+=defaultDate.substring(fieldData.length());
        }
        try {
            return sdf.parse(fieldData);
        } catch (ParseException e) {
            log.warn("时间类型反序列化出错！",e);
            return null;
        }

    }
}
