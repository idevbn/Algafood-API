package com.algaworks.algafood.core.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

/**
 * Classe que configura a exibição de paginações customizadas
 * na aplicação.
 *
 * A API pode fornecer informações importantes a respeito dos
 * elementos que constituem a página e com isso o cliente tem
 * mais possibilidade de construir elementos significativos
 * para o usuário final.
 */
@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>> {

    @Override
    public void serialize(final Page<?> page,
                          final JsonGenerator gen,
                          final SerializerProvider serializerProvider) throws IOException {
        gen.writeStartObject();

        gen.writeObjectField("content", page.getContent());
        gen.writeNumberField("size", page.getSize());
        gen.writeNumberField("totalElements", page.getTotalElements());
        gen.writeNumberField("totalPages", page.getTotalPages());
        gen.writeNumberField("number", page.getNumber());

        gen.writeEndObject();
    }

}
