package Entities;

public class Vaga {

	private int id;
	private String titulo;
	private String descricao;
	private String local;
	private Empresa empresa;

	public Vaga(int id, String titulo, String descricao, String local, Empresa empresa) {
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.local = local;
		this.empresa = empresa;
	}

	public int getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getLocal() {
		return local;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void exibirDetalhes() {
		System.out.println("ID: " + id);
		System.out.println("Título: " + titulo);
		System.out.println("Descrição: " + descricao);
		System.out.println("Local: " + local);
		System.out.println("Empresa: " + empresa.getNomeFantasia());
	}
}
