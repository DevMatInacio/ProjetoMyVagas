package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "candidatos")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Candidato extends Usuario {
    
    @Column(columnDefinition = "TEXT")
    private String curriculo;
    
    private String telefone;
    
    @Column(columnDefinition = "TEXT")
    private String experiencia;

    // CONSTRUTORES
    public Candidato() {
        // Construtor padrão obrigatório para JPA
    }

    // Construtor completo
    public Candidato(String nome, String email, String senha, String curriculo, String telefone, String experiencia) {
        super(nome, email, senha, "CANDIDATO");
        this.curriculo = curriculo;
        this.telefone = telefone;
        this.experiencia = experiencia;
    }

    // GETTERS E SETTERS
    public String getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(String curriculo) {
        this.curriculo = curriculo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    // MÉTODO DE NEGÓCIO
    public void candidatar(Vaga vaga) {
        System.out.println(getNome() + " se candidatou à vaga: " + vaga.getTitulo());
    }
}