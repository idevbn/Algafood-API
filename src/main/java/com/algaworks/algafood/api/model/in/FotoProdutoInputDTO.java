package com.algaworks.algafood.api.model.in;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FotoProdutoInputDTO {

    private MultipartFile arquivo;
    private String descricao;

}
