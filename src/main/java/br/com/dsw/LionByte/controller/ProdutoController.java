package br.com.dsw.LionByte.controller;

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

import br.com.dsw.LionByte.model.Categoria;
import br.com.dsw.LionByte.model.Fornecedor;
import br.com.dsw.LionByte.model.Produto;
import br.com.dsw.LionByte.repository.CategoriaRepository;
import br.com.dsw.LionByte.repository.FornecedorRepository;
import br.com.dsw.LionByte.repository.ProdutoRepository;


@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public ProdutoController(ProdutoRepository produtoRepository, FornecedorRepository fornecedorRepository,
            CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Produto getProduto(@PathVariable Long id) throws Exception{
        Optional<Produto> produto = produtoRepository.findById(id);
        if(produto.isEmpty()){
            throw new Exception("Erro: Id de Produto não encontrado " + id);
        }
        return produto.get();
    }

    @PostMapping
    public Produto salvarProduto(@RequestBody Map<String, Object> requestBody) {
        Produto produto = new Produto();

        // Verifica se o id_produto foi fornecido
        if (requestBody.containsKey("id_produto")) {
            Integer id_produto = (Integer) requestBody.get("id_produto");
            Long id_produto_long = id_produto.longValue();
            produto.setId_produto(id_produto_long);
        }

        // Verifica se o nome foi fornecido
        if (requestBody.containsKey("nome")) {
            String nome = (String) requestBody.get("nome");
            produto.setNome(nome);
        }

        // Verifica se a descrição foi fornecida
        if (requestBody.containsKey("descricao")) {
            String descricao = (String) requestBody.get("descricao");
            produto.setDescricao(descricao);
        }

        // Verifica se o preço foi fornecido
        if (requestBody.containsKey("preco")) {
            double preco = Double.parseDouble(requestBody.get("preco").toString());
            produto.setPreco(preco);
        }

        // Verifica se a quantidade foi fornecida
        if (requestBody.containsKey("quantidade")) {
            int quantidade = Integer.parseInt(requestBody.get("quantidade").toString());
            produto.setQuantidade(quantidade);
        }

        // Verifica se o id_fornecedor foi fornecido
        if (requestBody.containsKey("id_fornecedor")) {
            Long id_fornecedor = Long.parseLong(requestBody.get("id_fornecedor").toString());
            Fornecedor fornecedor = fornecedorRepository.findById(id_fornecedor)
                    .orElseThrow(() -> new RuntimeException("Fornecedor não encontrada com o ID: " + id_fornecedor));
            produto.setFornecedor(fornecedor);
        }

        // Verifica se o id_categoria foi fornecido
        if (requestBody.containsKey("id_categoria")) {
            Long id_categoria = Long.parseLong(requestBody.get("id_categoria").toString());
            Categoria categoria = categoriaRepository.findById(id_categoria)
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada com o ID: " + id_categoria));
            produto.setCategoria(categoria);
        }

        return produtoRepository.save(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable Long id) {
        produtoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
