package Entities;

public class Candidato extends Usuario {

	private String curriculo;

	public Candidato(int id, String nome, String email, String senha, String curriculo) {
		super(id, nome, email, senha);
		this.curriculo = curriculo;
	}

	public String getCurriculo() {
		return curriculo;
	}

	public void setCurriculo(String curriculo) {
		this.curriculo = curriculo;
	}

	public void candidatar(Vaga vaga) {
		System.out.println(getNome() + " se candidatou à vaga: " + vaga.getTitulo());

	}

}
