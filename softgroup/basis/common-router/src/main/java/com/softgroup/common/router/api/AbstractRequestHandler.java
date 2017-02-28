package com.softgroup.common.router.api;


import com.softgroup.common.datamapper.JacksonDataMapper;
import com.softgroup.common.protocol.Request;
import com.softgroup.common.protocol.RequestData;
import com.softgroup.common.protocol.Response;
import com.softgroup.common.protocol.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public abstract class AbstractRequestHandler<T extends RequestData, R extends ResponseData> implements RequestHandler {

    @Autowired
    JacksonDataMapper mapper;
    public abstract Response<R> doHandle(Request<T> request);

    @Override
    public Response<R> handle(Request<?> msg) {

        Map<String, Object> map = mapper.convertToMap(msg);
        T data = mapper.convert(map, (Class<T>) RequestData.class);
        Request<T> converted = new Request<T>();
        converted.setHeader(msg.getHeader());
        converted.setData(data);

        return doHandle(converted);
    }

}
