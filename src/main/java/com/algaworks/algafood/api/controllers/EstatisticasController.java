package com.algaworks.algafood.api.controllers;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.model.out.EstadoOutputDTO;
import com.algaworks.algafood.api.openapi.controllers.EstatisticasControllerOpenApi;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/estatisticas")
public class EstatisticasController implements EstatisticasControllerOpenApi {

    private static final String DEFAULT_VALUE_UTC = "+00:00";

    private final VendaQueryService vendaQueryService;

    private final VendaReportService vendaReportService;

    private final AlgaLinks algaLinks;

    @Autowired
    public EstatisticasController(final VendaQueryService vendaQueryService,
                                  final VendaReportService vendaReportService,
                                  final AlgaLinks algaLinks) {
        this.vendaQueryService = vendaQueryService;
        this.vendaReportService = vendaReportService;
        this.algaLinks = algaLinks;
    }


    @Override
    @GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VendaDiaria>> consultarVendasDiarias(
            final VendaDiariaFilter filter,
            @RequestParam(required = false, defaultValue = DEFAULT_VALUE_UTC) final String timeOffset) {

        final List<VendaDiaria> vendasDiarias = this.vendaQueryService
                .consultarVendasDiarias(filter, timeOffset);

        final ResponseEntity<List<VendaDiaria>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(vendasDiarias);

        return response;
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstatisticasModel> estatisticas() {
        final EstatisticasModel estatisticasModel = new EstatisticasModel();

        estatisticasModel
                .add(this.algaLinks.linkToEstatisticasVendasDiarias("vendas-diarias"));

        final ResponseEntity<EstatisticasModel> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(estatisticasModel);

        return response;
    }

    @Override
    @GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultarVendasDiariasPdf(
            final VendaDiariaFilter filter,
            @RequestParam(required = false, defaultValue = DEFAULT_VALUE_UTC) final String timeOffset) {

        final byte[] bytesPdf = this.vendaReportService
                .emitirVendasDiarias(filter, timeOffset);

        final HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=vendas-diarias.pdf");

        final ResponseEntity<byte[]> response = ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPdf);

        return response;
    }

    public static class EstatisticasModel extends RepresentationModel<EstadoOutputDTO> {
    }

}
