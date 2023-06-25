package br.com.dsw.LionByte.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.dsw.LionByte.model.CarrinhoCompras;
import br.com.dsw.LionByte.model.Produto;
import br.com.dsw.LionByte.model.Usuario;
import br.com.dsw.LionByte.repository.CarrinhoRepository;
import br.com.dsw.LionByte.repository.ProdutoRepository;
import br.com.dsw.LionByte.repository.UsuarioRepository;


@RestController
@RequestMapping("/carrinho")
@CrossOrigin(origins = "http://localhost:3000")
public class CarrinhoComprasController {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<Map<String, Object>> listarCarrinho(@RequestParam("id_usuario") Long id_usuario) {

        List<Map<String, Object>> carrinhos = new ArrayList<>();

        // Verifica se a id_usuario foi fornecido
        if (id_usuario > 0) {
            Usuario usuario = usuarioRepository.findById(id_usuario)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id_usuario));
        

            List<CarrinhoCompras> carrinhoComprasList = carrinhoRepository.findCarrinhosSemPedidoPorUsuario(usuario);

            for (CarrinhoCompras carrinho : carrinhoComprasList) {
                
                Map<String, Object> carrinhoMap = new HashMap<>();
                carrinhoMap.put("id_carrinho", carrinho.getId_carrinho());
                carrinhoMap.put("quantidade", carrinho.getQuantidade());

                Produto produto = carrinho.getProduto();
                carrinhoMap.put("nome", produto.getNome());
                carrinhoMap.put("preco", produto.getPreco());

                carrinhos.add(carrinhoMap);
            }
        }

        return carrinhos;
    }


    @PostMapping
    public CarrinhoCompras criarCarrinhoCompras(@RequestBody Map<String, Object> requestBody) {
        CarrinhoCompras carrinho = new CarrinhoCompras();
        
        // Verifica se o id_produto foi fornecido
        if (requestBody.containsKey("id_produto")) {
            Long id_produto = Long.parseLong(requestBody.get("id_produto").toString());
            Produto produto = produtoRepository.findById(id_produto)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id_produto));
            carrinho.setProduto(produto);
        }

        // Verifica se o id_usuario foi fornecido
        if (requestBody.containsKey("id_usuario")) {
            Long id_usuario = Long.parseLong(requestBody.get("id_usuario").toString());
            Usuario usuario = usuarioRepository.findById(id_usuario)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id_usuario));
            carrinho.setUsuario(usuario);
        }

        // Verifica se a quantidade foi fornecida
        if (requestBody.containsKey("quantidade")) {
            int quantidade = Integer.parseInt(requestBody.get("quantidade").toString());
            carrinho.setQuantidade(quantidade);
        }

        return carrinhoRepository.save(carrinho);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCarrinho(@PathVariable Long id) {
        carrinhoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

