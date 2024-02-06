package com.algaworks.algafood.api.v1.openapi.controllers;

import com.algaworks.algafood.api.v1.controllers.EstatisticasController.EstatisticasModel;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SecurityRequirement(name = "security_auth")
public interface EstatisticasControllerOpenApi {

    ResponseEntity<List<VendaDiaria>> consultarVendasDiarias(
            final VendaDiariaFilter filtro,
            final String timeOffset
    );

    ResponseEntity<EstatisticasModel> estatisticas();

    ResponseEntity<byte[]> consultarVendasDiariasPdf(
            final VendaDiariaFilter filtro,
            final String timeOffset
    );

}
