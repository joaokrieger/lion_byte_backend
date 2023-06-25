package br.com.dsw.LionByte.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.dsw.LionByte.model.CarrinhoCompras;
import br.com.dsw.LionByte.model.Pedido;
import br.com.dsw.LionByte.model.Usuario;

@Repository
public interface CarrinhoRepository extends JpaRepository<CarrinhoCompras, Long> {

    @Query("SELECT c FROM CarrinhoCompras c WHERE c.pedido IS NULL AND c.usuario = :usuario")
    List<CarrinhoCompras> findCarrinhosSemPedidoPorUsuario(@Param("usuario") Usuario usuario);


    @Query("SELECT c, p.status FROM CarrinhoCompras c JOIN c.pedido p WHERE c.pedido = :pedido")
    List<CarrinhoCompras> findCarrinhosPorPedido(@Param("pedido") Optional<Pedido> pedido);
}


