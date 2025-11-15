package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "candidaturas")
public class Candidatura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidato_id")
    private Candidato candidato;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaga_id")
    private Vaga vaga;
    
    private LocalDate dataCandidatura;
    
    @Column(nullable = false)
    private String status = "PENDENTE";

    // CONSTRUTORES
    public Candidatura() {
        // Construtor padrão obrigatório para JPA
    }

    // Construtor completo
    public Candidatura(Candidato candidato, Vaga vaga, LocalDate dataCandidatura) {
        this.candidato = candidato;
        this.vaga = vaga;
        this.dataCandidatura = dataCandidatura;
        this.status = "PENDENTE";
    }

    // GETTERS E SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }

    public LocalDate getDataCandidatura() {
        return dataCandidatura;
    }

    public void setDataCandidatura(LocalDate dataCandidatura) {
        this.dataCandidatura = dataCandidatura;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // MÉTODOS AUXILIARES PARA STATUS
    public boolean isPendente() {
        return "PENDENTE".equals(status);
    }
    
    public boolean isSelecionado() {
        return "SELECIONADO".equals(status);
    }
    
    public boolean isRejeitado() {
        return "REJEITADO".equals(status);
    }

    // MÉTODO DE NEGÓCIO
    public void exibirStatus() {
        System.out.println("Candidato: " + candidato.getNome());
        System.out.println("Vaga: " + vaga.getTitulo());
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.println("Data da candidatura: " + dataCandidatura.format(formato));
        System.out.println("Status: " + status);
    }
}