package br.com.dsw.LionByte.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fornecedores")
public class Fornecedor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_fornecedor;
    private String nome;
    private String endereco;
    private String contato;

    public Long getId_fornecedor() {
        return id_fornecedor;
    }
    public void setId_fornecedor(Long id_fornecedor) {
        this.id_fornecedor = id_fornecedor;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public String getContato() {
        return contato;
    }
    public void setContato(String contato) {
        this.contato = contato;
    }
}