package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQuery {

    @Query("from Produto where restaurante.id = :restaurante and id = :produto")
    Optional<Produto> findById(@Param("restaurante") final Long restauranteId,
                               @Param("produto") final Long produtoId);

    List<Produto> findByRestaurante(final Restaurante restaurante);

    @Query("from Produto p where p.ativo = true and restaurante = :restaurante")
    List<Produto> findAtivosByRestaurante(@Param("restaurante") final Restaurante restaurante);

    @Query("select f from FotoProduto f join f.produto p " +
            "where p.restaurante.id = :restauranteId  and f.produto.id = :produtoId")
    Optional<FotoProduto> findFotoById(@Param("restauranteId") final Long restauranteId,
                                       @Param("produtoId") final Long produtoId);

}
