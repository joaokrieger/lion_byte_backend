package br.com.dsw.LionByte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dsw.LionByte.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}

