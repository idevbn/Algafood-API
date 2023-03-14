package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>,
        RestauranteRepositoryQuery, JpaSpecificationExecutor<Restaurante> {
    List<Restaurante> findByTaxaFreteBetween(final BigDecimal taxaInicial,
                                             final BigDecimal taxaFinal);

//    @Query(value = "from Restaurante where nome like %:nome% and cozinha.id = :id")
    List<Restaurante> consultarPorNome(final String nome,
                                       @Param(value = "id") final Long cozinhaId);

    Optional<Restaurante> findFirstRestauranteByNomeContaining(final String nome);

    List<Restaurante> findTop2ByNomeContaining(final String nome);

    List<Restaurante> find(
            final String nome,
            final BigDecimal taxaFreteInicial,
            final BigDecimal taxaFreteFinal);
}
