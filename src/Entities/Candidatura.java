package Entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Candidatura {

	private int id;
	private Candidato candidato;
	private Vaga vaga;
	private LocalDate dataCandidatura;

	public Candidatura(int id, Candidato candidato, Vaga vaga, LocalDate dataCandidatura) {
		this.id = id;
		this.candidato = candidato;
		this.vaga = vaga;
		this.dataCandidatura = dataCandidatura;
	}

	public int getId() {
		return id;
	}

	public Candidato getCandidato() {
		return candidato;
	}

	public Vaga getVaga() {
		return vaga;
	}

	public LocalDate getDataCandidatura() {
		return dataCandidatura;
	}

	public void exibirStatus() {
		System.out.println("Candidato: " + candidato.getNome());
		System.out.println("Vaga: " + vaga.getTitulo());
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		System.out.println("Data da candidatura: " + dataCandidatura.format(formato));

	}
}
