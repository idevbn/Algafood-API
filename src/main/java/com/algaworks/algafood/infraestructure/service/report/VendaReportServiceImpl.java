package com.algaworks.algafood.infraestructure.service.report;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.VendaReportService;
import org.springframework.stereotype.Repository;

@Repository
public class VendaReportServiceImpl implements VendaReportService {

    @Override
    public byte[] emitirVendasDiarias(final VendaDiariaFilter filter, final String timeOffset) {
        return null;
    }

}
