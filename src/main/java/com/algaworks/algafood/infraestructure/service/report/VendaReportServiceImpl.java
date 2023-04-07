package com.algaworks.algafood.infraestructure.service.report;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Repository
public class VendaReportServiceImpl implements VendaReportService {

    private final VendaQueryService vendaQueryService;

    @Autowired
    public VendaReportServiceImpl(final VendaQueryService vendaQueryService) {
        this.vendaQueryService = vendaQueryService;
    }

    @Override
    public byte[] emitirVendasDiarias(final VendaDiariaFilter filter,
                                      final String timeOffset) {

        try {
            final InputStream inputStream = this.getClass()
                    .getResourceAsStream("/relatorios/vendas-diarias.jasper");

            final HashMap<String, Object> params = new HashMap<>();
            params.put("REPORT_LOCALE", new Locale("pt", "BR"));

            final List<VendaDiaria> vendasDiarias = this.vendaQueryService
                    .consultarVendasDiarias(filter, timeOffset);

            final JRBeanCollectionDataSource dataSource =
                    new JRBeanCollectionDataSource(vendasDiarias);

            final JasperPrint jasperPrint = JasperFillManager
                    .fillReport(inputStream, params, dataSource);

            final byte[] reportToPdf = JasperExportManager.exportReportToPdf(jasperPrint);

            return reportToPdf;
        } catch (final Exception e) {
            throw new ReportException("Não foi possível emitir relatório de vendas diárias", e);
        }
    }

}
