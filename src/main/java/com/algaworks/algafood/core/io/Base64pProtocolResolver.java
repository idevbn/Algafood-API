package com.algaworks.algafood.core.io;

import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.Base64;

public class Base64pProtocolResolver implements ProtocolResolver,
        ApplicationListener<ApplicationContextInitializedEvent> {

    @Override
    public Resource resolve(final String location, final ResourceLoader resourceLoader) {
        if (location.startsWith("base64:")) {
            final byte[] decodedResource = Base64
                    .getDecoder()
                    .decode(location.substring(7));

            return new ByteArrayResource(decodedResource);
        }

        return null;
    }

    @Override
    public void onApplicationEvent(final ApplicationContextInitializedEvent event) {
        event.getApplicationContext().addProtocolResolver(this);
    }

}
