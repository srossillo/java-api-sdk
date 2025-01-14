package com.smartling.api.v2.client.unmarshal;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.util.Map;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class RestApiContextResolver implements ContextResolver<ObjectMapper>
{
    private ObjectMapper objectMapper;

    @SuppressWarnings("unchecked")
    public RestApiContextResolver(final Map<Class<?>, JsonDeserializer<?>> classJsonDeserializerMap)
    {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SimpleModule deserializerModule = new SimpleModule();
        for (final Class<?> klass : classJsonDeserializerMap.keySet())
            deserializerModule.addDeserializer(klass, (JsonDeserializer)classJsonDeserializerMap.get(klass));

        objectMapper.registerModule(deserializerModule);

        this.objectMapper = objectMapper;
    }

    @Override
    public ObjectMapper getContext(final Class<?> type)
    {
        return objectMapper;
    }
}
