package com.algaworks.algafood.util;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class ResourceUtils {

    public static String getContentFromResource(final String resourceName) {
        try {
            final InputStream stream = ResourceUtils.class.getResourceAsStream(resourceName);
            return StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

}
