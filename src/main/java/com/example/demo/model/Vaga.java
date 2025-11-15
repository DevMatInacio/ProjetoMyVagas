package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vagas")
public class Vaga {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String titulo;
    
    @Column(columnDefinition = "TEXT")
    private String descricao;
    
    private String local;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    // CONSTRUTORES
    public Vaga() {
        // Construtor padrão obrigatório para JPA
    }

    // Construtor completo
    public Vaga(String titulo, String descricao, String local, Empresa empresa) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.local = local;
        this.empresa = empresa;
    }

    // GETTERS E SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    // MÉTODO DE NEGÓCIO
    public void exibirDetalhes() {
        System.out.println("ID: " + id);
        System.out.println("Título: " + titulo);
        System.out.println("Descrição: " + descricao);
        System.out.println("Local: " + local);
        System.out.println("Empresa: " + empresa.getNomeFantasia());
    }
}