package com.algaworks.algafood.core.security.authorizationserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.List;

public class JdbcOAuth2AuthorizationQueryService implements OAuth2AuthorizationQueryService {

    private final JdbcOperations jdbcOperations;
    private final RowMapper<RegisteredClient> registeredClientRowMapper;

    private final String LIST_AUTHORIZED_CLIENTS = "SELECT rc.* FROM oauth2_authorization_consent c " +
            "INNER JOIN oauth2_registered_client rc " +
            "ON rc.id = c.registered_client_id " +
            "WHERE c.principal_name = ?";

    @Autowired
    public JdbcOAuth2AuthorizationQueryService(
            final JdbcOperations jdbcOperations
    ) {
        this.jdbcOperations = jdbcOperations;
        this.registeredClientRowMapper = new JdbcRegisteredClientRepository.RegisteredClientRowMapper();
    }

    @Override
    public List<RegisteredClient> listClientsWithConsent(final String principalName) {
        final List<RegisteredClient> query = this.jdbcOperations
                .query(LIST_AUTHORIZED_CLIENTS, registeredClientRowMapper, principalName);

        return query;
    }

}
