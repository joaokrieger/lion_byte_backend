package br.com.dsw.LionByte.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pedido;
    
    private LocalDate data;
    private double total;
    private String status;

    public Long getId_pedido() {
        return id_pedido;
    }
    public void setId_pedido(Long id_pedido) {
        this.id_pedido = id_pedido;
    }

    public LocalDate getData() {
        return data;
    }

    public void setDataAtual() {
        this.data = LocalDate.now();
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}