package br.com.dsw.LionByte.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
public class CarrinhoComprasController {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public CarrinhoComprasController(CarrinhoRepository carrinhoRepository, ProdutoRepository produtoRepository,UsuarioRepository usuarioRepository) {
        this.carrinhoRepository = carrinhoRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
    }

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
    public CarrinhoCompras salvarCarrinhoCompras(@RequestBody Map<String, Object> requestBody) {
        
        CarrinhoCompras carrinho = new CarrinhoCompras();
        int quantidade = 0;

        // Verifica se a quantidade foi fornecida
        if (requestBody.containsKey("quantidade")) {
            quantidade = Integer.parseInt(requestBody.get("quantidade").toString());
            carrinho.setQuantidade(quantidade);
        }

        // Verifica se o id_produto foi fornecido
        if (requestBody.containsKey("id_produto")) {
            Long id_produto = Long.parseLong(requestBody.get("id_produto").toString());
            Produto produto = produtoRepository.findById(id_produto)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id_produto));
            carrinho.setProduto(produto);

            
            //Removendo quantidade comprada
            produto.setQuantidade(produto.getQuantidade() - quantidade);
            produtoRepository.save(produto);
        }

        // Verifica se o id_usuario foi fornecido
        if (requestBody.containsKey("id_usuario")) {
            Long id_usuario = Long.parseLong(requestBody.get("id_usuario").toString());
            Usuario usuario = usuarioRepository.findById(id_usuario)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id_usuario));
            carrinho.setUsuario(usuario);
        }

        return carrinhoRepository.save(carrinho);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCarrinho(@PathVariable Long id) {
        
        // Carregando Carrinho
        CarrinhoCompras carrinhoCompras = carrinhoRepository.findById(id).
            orElseThrow(() -> new RuntimeException("Carrinho não encontrado com o ID: " + id));

        // Reatribuíndo quantidade de itens comprada
        Produto produto =carrinhoCompras.getProduto();
        produto.setQuantidade(produto.getQuantidade() + carrinhoCompras.getQuantidade());

        produtoRepository.save(produto);
        carrinhoRepository.deleteById(id);
        
        return ResponseEntity.noContent().build();
    }
}

