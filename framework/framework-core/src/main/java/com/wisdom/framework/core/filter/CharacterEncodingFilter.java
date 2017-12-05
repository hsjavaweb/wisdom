package com.wisdom.framework.core.filter;

import org.springframework.util.ReflectionUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLDecoder;

/**
 * @author hyberbin on 2017/9/20.
 */
public class CharacterEncodingFilter extends org.springframework.web.filter.CharacterEncodingFilter {
    private String encoding;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            request.setCharacterEncoding(encoding);

            Field _requestField = ReflectionUtils.findField(request.getClass(), "request");
            _requestField.setAccessible(true);
            Object _request = _requestField.get(request);

            Field _originalURIField = ReflectionUtils.findField(_request.getClass(), "_originalURI");
            if(_originalURIField!=null){
                _originalURIField.setAccessible(true);
                Object _originalURI=_originalURIField.get(_request);
                _originalURIField.set(_request,URLDecoder.decode((String) _originalURI, encoding));

                Field _metaDataField = ReflectionUtils.findField(_request.getClass(), "_metaData");
                _metaDataField.setAccessible(true);
                Object _metaData=_metaDataField.get(_request);


                Field _uriField=ReflectionUtils.findField(_metaData.getClass(), "_uri");
                _uriField.setAccessible(true);
                Object _uri=_uriField.get(_metaData);

                Field _queryField=ReflectionUtils.findField(_uri.getClass(), "_query");
                _queryField.setAccessible(true);
                Object _query=_queryField.get(_uri);
                if(_query!=null){
                    _queryField.set(_uri, URLDecoder.decode((String) _query, encoding));

                    Field __uriField=ReflectionUtils.findField(_uri.getClass(), "_uri");
                    __uriField.setAccessible(true);
                    Object __uri=_queryField.get(_uri);
                    __uriField.set(_uri, URLDecoder.decode((String) __uri, encoding));
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        super.doFilterInternal(request, response, filterChain);
    }

    @Override
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
