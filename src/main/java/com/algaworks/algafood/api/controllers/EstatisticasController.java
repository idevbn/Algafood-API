package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/estatisticas")
public class EstatisticasController {

    private static final String DEFAULT_VALUE_UTC = "+00:00";

    private final VendaQueryService vendaQueryService;

    @Autowired
    public EstatisticasController(final VendaQueryService vendaQueryService) {
        this.vendaQueryService = vendaQueryService;
    }

    @GetMapping(value = "/vendas-diarias")
    public ResponseEntity<List<VendaDiaria>> consultarVendasDiarias(
            final VendaDiariaFilter filter,
            @RequestParam(required = false, defaultValue = DEFAULT_VALUE_UTC)
            final String timeOffset) {

        final List<VendaDiaria> vendasDiarias = this.vendaQueryService
                .consultarVendasDiarias(filter, timeOffset);

        final ResponseEntity<List<VendaDiaria>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(vendasDiarias);

        return response;
    }

}
