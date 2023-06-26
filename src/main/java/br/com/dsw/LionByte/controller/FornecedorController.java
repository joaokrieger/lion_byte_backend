package br.com.dsw.LionByte.controller;

import java.util.List;
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

import br.com.dsw.LionByte.model.Fornecedor;
import br.com.dsw.LionByte.repository.FornecedorRepository;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public FornecedorController(FornecedorRepository fornecedorRepository){
        this.fornecedorRepository = fornecedorRepository;
    }

    @GetMapping
    public List<Fornecedor> listarFornecedores() {
        return fornecedorRepository.findAll();
    }

    @PostMapping
    public Fornecedor salvarFornecedor(@RequestBody Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    @GetMapping("/{id}")
    public Fornecedor getFornecedor(@PathVariable Long id) throws Exception{
        Optional<Fornecedor> fornecedor = fornecedorRepository.findById(id);
        if(fornecedor.isEmpty()){
            throw new Exception("Erro: Id de fornecedor não encontrado " + id);
        }
        return fornecedor.get();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirFornecedor(@PathVariable Long id) {
        fornecedorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
