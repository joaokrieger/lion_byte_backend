package br.com.dsw.LionByte.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dsw.LionByte.model.CarrinhoCompras;
import br.com.dsw.LionByte.model.Pedido;
import br.com.dsw.LionByte.model.Usuario;
import br.com.dsw.LionByte.repository.CarrinhoRepository;
import br.com.dsw.LionByte.repository.PedidoRepository;
import br.com.dsw.LionByte.repository.UsuarioRepository;


@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public PedidoController(PedidoRepository pedidoRepository, CarrinhoRepository carrinhoRepository,UsuarioRepository usuarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.carrinhoRepository = carrinhoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/{id}")
    public List<CarrinhoCompras> getCarrinhoByPedido(@PathVariable Long id) throws Exception{
        
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        
        if(pedido.isEmpty()){
            throw new Exception("Erro: Id de Pedido não encontrado " + id);
        }

        List<CarrinhoCompras> carrinhos = carrinhoRepository.findCarrinhosPorPedido(pedido);

        return carrinhos;
    }

    @GetMapping
    public List<Pedido> listarPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        Collections.sort(pedidos, Comparator.comparing(Pedido::getId_pedido));
        return pedidos;
    }

    @PostMapping
    public Pedido salvarPedido(@RequestBody Map<String, Object> requestBody) {
        Pedido pedido = new Pedido();
        
        pedido.setStatus("Realizado");
        pedido.setDataAtual();

        // Verifica se a total foi fornecida
        if (requestBody.containsKey("total")) {
            double total = Double.parseDouble(requestBody.get("total").toString());
            pedido.setTotal(total);
        }

        // Verifica se a id_usuario foi fornecido
        if (requestBody.containsKey("id_usuario")) {
            Long id_usuario = Long.parseLong(requestBody.get("id_usuario").toString());
            Usuario usuario = usuarioRepository.findById(id_usuario)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id_usuario));
        
            pedidoRepository.save(pedido);

            List<CarrinhoCompras> carrinhosSemPedido = carrinhoRepository.findCarrinhosSemPedidoPorUsuario(usuario);
            for(CarrinhoCompras c:carrinhosSemPedido){
                c.setPedido(pedido);
                carrinhoRepository.save(c);
            }
        }

        return pedido;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPedido(@PathVariable Long id) {
        pedidoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/atender/{id_pedido}")
    public void atenderPedido(@PathVariable("id_pedido") Long id_pedido) throws Exception {
        
        Pedido pedido = pedidoRepository.findById(id_pedido).orElse(null);

        if(pedido != null){
            
            //Verificando status do pedido
            if(pedido.getStatus().equals("Realizado"))
                pedido.setStatus("Em Preparo");
            else if(pedido.getStatus().equals("Em Preparo"))
                pedido.setStatus("Enviado");
            else if(pedido.getStatus().equals("Enviado"))
                pedido.setStatus("Entregue");

            pedidoRepository.save(pedido);
        }
        else{
            throw new Exception("Erro: Id de Pedido não encontrado " + id_pedido);
        }
    }
}

