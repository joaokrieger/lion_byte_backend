package br.com.dsw.LionByte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dsw.LionByte.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
