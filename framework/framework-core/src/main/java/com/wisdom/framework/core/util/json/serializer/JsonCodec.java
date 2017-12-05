/**
 * hyberbin.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.wisdom.framework.core.util.json.serializer;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import org.redisson.client.codec.Codec;
import org.redisson.client.handler.State;
import org.redisson.client.protocol.Decoder;
import org.redisson.client.protocol.Encoder;

import java.io.IOException;

/**
 * @author hyberbin
 * @version $Id: JsonCodec.java, v 0.1 2017年10月24日 20:52 hyberbin Exp $
 */
public class JsonCodec implements Codec {

    private static final Decoder<Object> decoder = new Decoder<Object>() {
                                                     @Override
                                                     public Object decode(ByteBuf buf,
                                                                          State state) throws IOException {
                                                         try (ByteBufInputStream inputStream = new ByteBufInputStream(
                                                             buf)) {
                                                             return JSON.parseObject(inputStream,
                                                                 Object.class);
                                                         }
                                                     }
                                                 };

    private static final Encoder         encoder = new Encoder() {
                                                     @Override
                                                     public byte[] encode(Object in) throws IOException {
                                                         return JSON.toJSONString(in).getBytes();
                                                     }
                                                 };

    @Override
    public Decoder<Object> getMapValueDecoder() {
        return decoder;
    }

    @Override
    public Encoder getMapValueEncoder() {
        return encoder;
    }

    @Override
    public Decoder<Object> getMapKeyDecoder() {
        return decoder;
    }

    @Override
    public Encoder getMapKeyEncoder() {
        return encoder;
    }

    @Override
    public Decoder<Object> getValueDecoder() {
        return decoder;
    }

    @Override
    public Encoder getValueEncoder() {
        return encoder;
    }
}