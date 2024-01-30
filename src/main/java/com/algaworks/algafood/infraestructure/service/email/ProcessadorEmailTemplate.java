//package com.algaworks.algafood.infraestructure.service.email;
//
//import com.algaworks.algafood.domain.service.EnvioEmailService;
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
//
//@Component
//public class ProcessadorEmailTemplate {
//
//    @Autowired
//    private Configuration freemarkerConfig;
//
//    protected String processarTemplate(final EnvioEmailService.Mensagem mensagem) {
//
//        try {
//            final Template template = this.freemarkerConfig.getTemplate(mensagem.getCorpo());
//
//            final String corpo = FreeMarkerTemplateUtils
//                    .processTemplateIntoString(template, mensagem.getVariaveis());
//
//            return corpo;
//        } catch (final Exception e) {
//            throw new EmailException("Não foi possível montar o template do email", e);
//        }
//    }
//
//}
