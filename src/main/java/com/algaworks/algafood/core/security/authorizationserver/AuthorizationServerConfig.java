package com.algaworks.algafood.core.security.authorizationserver;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtKeyStoreProperties jwtKeyStoreProperties;
    private final DataSource dataSource;

    @Autowired
    public AuthorizationServerConfig(final AuthenticationManager authenticationManager,
                                     final UserDetailsService userDetailsService,
                                     final JwtKeyStoreProperties jwtKeyStoreProperties,
                                     final DataSource dataSource) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtKeyStoreProperties = jwtKeyStoreProperties;
        this.dataSource = dataSource;
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(this.dataSource);
    }

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer security) throws Exception {

        /**
         * security.checkTokenAccess("isAuthenticated()");
         * Com o permitAll() não é necessário autenticar o usuário
         */
        security.checkTokenAccess("permitAll()")
                .tokenKeyAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(
                List.of(new JwtCustomClaimTokenEnhancer(),
                        this.jwtAccessTokenConverter()
                )
        );

        endpoints
                .authenticationManager(this.authenticationManager)
                .userDetailsService(this.userDetailsService)
                .accessTokenConverter(this.jwtAccessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain)
                .approvalStore(this.approvalStore(endpoints.getTokenStore()))
                .tokenGranter(this.tokenGranter(endpoints));
        /**
         * Com essa configuração, o mesmo refresh_token não gera outros access_token
         *  o default é true
         */
//                .reuseRefreshTokens(false);
    }

    @Bean
    public JWKSet jwkSet() {
        final RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) this.keyPair().getPublic())
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID("algafood-key-id");

        return new JWKSet(builder.build());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter jwtAccessTokenConverter
                = new JwtAccessTokenConverter();

        jwtAccessTokenConverter.setKeyPair(this.keyPair());

        return jwtAccessTokenConverter;
    }

    private KeyPair keyPair() {
        final Resource jksLocation = this.jwtKeyStoreProperties.getJksLocation();
        final String password = this.jwtKeyStoreProperties.getPassword();
        final String pairAlias = this.jwtKeyStoreProperties.getKeyPairAlias();

        final KeyStoreKeyFactory keyStoreKeyFactory
                = new KeyStoreKeyFactory(jksLocation, password.toCharArray());

        final KeyPair keyPair = keyStoreKeyFactory.getKeyPair(pairAlias);

        return keyPair;
    }

    private ApprovalStore approvalStore(final TokenStore tokenStore) {
        final TokenApprovalStore approvalStore = new TokenApprovalStore();

        approvalStore.setTokenStore(tokenStore);

        return approvalStore;
    }

    private TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {
        final PkceAuthorizationCodeTokenGranter pkceAuthorizationCodeTokenGranter
                = new PkceAuthorizationCodeTokenGranter(
                endpoints.getTokenServices(),
                endpoints.getAuthorizationCodeServices(),
                endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory()
        );

        final List<TokenGranter> granters = Arrays.asList(
                pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter()
        );

        return new CompositeTokenGranter(granters);
    }

}
