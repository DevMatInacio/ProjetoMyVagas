package com.example.demo.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empresas")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Empresa extends Usuario {
    
    private String nomeFantasia;
    
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vaga> vagasPublicadas = new ArrayList<>();

    // CONSTRUTORES
    public Empresa() {
        // Construtor padrão obrigatório para JPA
    }

    // Construtor completo
    public Empresa(String nome, String email, String senha, String nomeFantasia) {
        super(nome, email, senha, "EMPRESA");
        this.nomeFantasia = nomeFantasia;
    }

    // GETTERS E SETTERS
    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public List<Vaga> getVagasPublicadas() {
        return vagasPublicadas;
    }

    public void setVagasPublicadas(List<Vaga> vagasPublicadas) {
        this.vagasPublicadas = vagasPublicadas;
    }

    // MÉTODOS DE NEGÓCIO
    public void publicarVaga(Vaga vaga) {
        vagasPublicadas.add(vaga);
        vaga.setEmpresa(this); // Importante: estabelece o relacionamento bidirecional
        System.out.println("Vaga publicada: " + vaga.getTitulo());
    }

    public void listarVagas() {
        System.out.println("Vagas publicadas por " + getNomeFantasia() + ":");
        for (Vaga v : vagasPublicadas) {
            System.out.println("- " + v.getTitulo());
        }
    }
}