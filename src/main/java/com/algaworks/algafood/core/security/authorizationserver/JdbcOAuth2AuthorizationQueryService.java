package com.algaworks.algafood.core.security.authorizationserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.List;

public class JdbcOAuth2AuthorizationQueryService implements OAuth2AuthorizationQueryService {

    private final JdbcOperations jdbcOperations;
    private final RowMapper<RegisteredClient> registeredClientRowMapper;
    private final RowMapper<OAuth2Authorization> oAuth2AuthorizationRowMapper;

    private final String LIST_AUTHORIZED_CLIENTS = "SELECT rc.* FROM oauth2_authorization_consent c " +
            "INNER JOIN oauth2_registered_client rc " +
            "ON rc.id = c.registered_client_id " +
            "WHERE c.principal_name = ?";

    private final String LIST_AUTHORIZATION_QUERY = "SELECT a.* FROM oauth2_authorization a " +
            "INNER JOIN oauth2_registered_client c " +
            "ON c.id = a.registered_client_id " +
            "WHERE a.principal_name = ? " +
            "AND a.registered_client_id  = ? ";

    @Autowired
    public JdbcOAuth2AuthorizationQueryService(
            final JdbcOperations jdbcOperations,
            final RegisteredClientRepository clientRepository
            ) {
        this.jdbcOperations = jdbcOperations;
        this.oAuth2AuthorizationRowMapper
                = new JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper(clientRepository);
        this.registeredClientRowMapper = new JdbcRegisteredClientRepository.RegisteredClientRowMapper();
    }

    @Override
    public List<RegisteredClient> listClientsWithConsent(final String principalName) {
        final List<RegisteredClient> query = this.jdbcOperations
                .query(LIST_AUTHORIZED_CLIENTS, this.registeredClientRowMapper, principalName);

        return query;
    }

    @Override
    public List<OAuth2Authorization> listAuthorizations(final String principalName,
                                                        final String clientId) {
        final List<OAuth2Authorization> query = this.jdbcOperations
                .query(
                        LIST_AUTHORIZATION_QUERY,
                        this.oAuth2AuthorizationRowMapper,
                        principalName,
                        clientId
                );

        return query;
    }

}
