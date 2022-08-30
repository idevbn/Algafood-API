package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaFormaPagamentoMain {
    public static void main(String args[]) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        FormaPagamentoRepository formaPagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);

        List<FormaPagamento> formasPagamento = formaPagamentoRepository.listar();

        for (FormaPagamento formaPagamento : formasPagamento) {
            System.out.printf("Forma de pagamento: %s - Restaurante: %s - Cozinha: %s \n",
                    formaPagamento.getDescricao(),
                    formaPagamento.getRestaurante().getNome(),
                    formaPagamento.getRestaurante().getCozinha().getNome()
            );
        }
    }
}
