package com.algaworks.algafood.core.security.authorizationserver;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorizedClientsController {

    private final OAuth2AuthorizationQueryService oAuth2AuthorizationQueryService;
    private final RegisteredClientRepository clientRepository;
    private final OAuth2AuthorizationConsentService oAuth2AuthorizationConsentService;
    private final OAuth2AuthorizationService oAuth2AuthorizationService;

    @GetMapping("/oauth2/authorized-clients")
    public String clientsList(final Principal principal, final Model model) {
        final List<RegisteredClient> clients = this.oAuth2AuthorizationQueryService
                .listClientsWithConsent(principal.getName());

        model.addAttribute("clients", clients);

        return "pages/authorized_clients";
    }

    @PostMapping("/oauth2/authorized-clients/revoke")
    public String revoke(
            final Principal principal,
            @RequestParam(OAuth2ParameterNames.CLIENT_ID) final String clientId
    ) throws AccessDeniedException {
        final RegisteredClient registeredClient = this.clientRepository.findByClientId(clientId);

        if (registeredClient == null) {
            throw new AccessDeniedException(String.format("Cliente %s não encontrado", clientId));
        }

        final OAuth2AuthorizationConsent consent = this.oAuth2AuthorizationConsentService
                .findById(registeredClient.getId(), principal.getName());

        final List<OAuth2Authorization> authorizations = this.oAuth2AuthorizationQueryService
                .listAuthorizations(principal.getName(), registeredClient.getClientId());

        if (consent != null) {
            this.oAuth2AuthorizationConsentService.remove(consent);
        }

        for (final OAuth2Authorization authorization : authorizations) {
            this.oAuth2AuthorizationService.remove(authorization);
        }

        return "redirect:/oauth2/authorized-clients";
    }

}
