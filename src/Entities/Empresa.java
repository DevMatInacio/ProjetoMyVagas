package Entities;

import java.util.ArrayList;
import java.util.List;

public class Empresa extends Usuario {

	private String nomeFantasia;
	private List<Vaga> vagasPublicadas;

	public Empresa(int id, String nome, String email, String senha, String nomeFantasia, List<Vaga> vagasPublicadas) {
		super(id, nome, email, senha);
		this.nomeFantasia = nomeFantasia;
		this.vagasPublicadas = new ArrayList<>();

	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public void publicarVaga(Vaga vaga) {
		vagasPublicadas.add(vaga);
		System.out.println("Vaga publicada: " + vaga.getTitulo());

	}

	public void listarVagas() {
		System.out.println("Vagas publicadas por " + getNomeFantasia() + ":");
		for (Vaga v : vagasPublicadas) {
			System.out.println("- " + v.getTitulo());
		}
	}

}
