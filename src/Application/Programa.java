package Application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Entities.Candidato;
import Entities.Candidatura;
import Entities.Empresa;
import Entities.Vaga;

public class Programa {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

        List<Empresa> empresas = new ArrayList<>();
        List<Candidato> candidatos = new ArrayList<>();
        List<Vaga> vagas = new ArrayList<>();
        List<Candidatura> candidaturas = new ArrayList<>();

        int opcao = -1;
        while(opcao != 0) {
            System.out.println("\n=== Menu Indeed Simulado ===");
            System.out.println("1 - Criar Empresa");
            System.out.println("2 - Publicar Vaga");
            System.out.println("3 - Criar Candidato");
            System.out.println("4 - Listar Vagas");
            System.out.println("5 - Candidatar-se a Vaga");
            System.out.println("6 - Ver Candidaturas");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            
			try {
				opcao = sc.nextInt();
				sc.nextLine();
			} catch (java.util.InputMismatchException e) {
				System.out.println("Opção inválida! Digite apenas números.");
				sc.nextLine();
				opcao = -1;
				continue;
			}

			switch (opcao) {
			case 1:

				break;

			case 0:
				System.out.println("Saindo...");
				break;
			default:
				System.out.println("Opção inválida!");
			}

            switch(opcao) {
                case 1:
                    System.out.print("Nome do usuário: ");
                    String nome = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Senha: ");
                    String senha = sc.nextLine();
                    System.out.print("Nome fantasia da empresa: ");
                    String fantasia = sc.nextLine();
                    Empresa emp = new Empresa(empresas.size()+1, nome, email, senha, fantasia, null);
                    empresas.add(emp);
                    System.out.println("Empresa criada com sucesso!");
                    break;

                case 2:
                    if(empresas.isEmpty()) {
                        System.out.println("Nenhuma empresa cadastrada!");
                        break;
                    }
                    System.out.println("Escolha a empresa que vai publicar a vaga:");
                    for(int i=0; i<empresas.size(); i++) {
                        System.out.println(i + " - " + empresas.get(i).getNomeFantasia());
                    }
                    int idxEmp = sc.nextInt(); sc.nextLine();
                    Empresa empresaSelecionada = empresas.get(idxEmp);

                    System.out.print("Título da vaga: ");
                    String titulo = sc.nextLine();
                    System.out.print("Descrição da vaga: ");
                    String desc = sc.nextLine();
                    System.out.print("Local: ");
                    String local = sc.nextLine();

                    Vaga vaga = new Vaga(vagas.size()+1, titulo, desc, local, empresaSelecionada);
                    empresaSelecionada.publicarVaga(vaga);
                    vagas.add(vaga);
                    break;

                case 3:
                    System.out.print("Nome do candidato: ");
                    String nomeC = sc.nextLine();
                    System.out.print("Email: ");
                    String emailC = sc.nextLine();
                    System.out.print("Senha: ");
                    String senhaC = sc.nextLine();
                    System.out.print("Currículo: ");
                    String curriculo = sc.nextLine();

                    Candidato c = new Candidato(candidatos.size()+1, nomeC, emailC, senhaC, curriculo);
                    candidatos.add(c);
                    System.out.println("Candidato criado com sucesso!");
                    break;

                case 4:
                    System.out.println("=== Vagas Disponíveis ===");
                    for(int i=0; i<vagas.size(); i++) {
                        System.out.println(i + " - " + vagas.get(i).getTitulo() + " | " + vagas.get(i).getLocal() +
                                           " | Empresa: " + vagas.get(i).getEmpresa().getNomeFantasia());
                    }
                    break;

                case 5:
                    if(candidatos.isEmpty() || vagas.isEmpty()) {
                        System.out.println("Candidatos ou vagas indisponíveis!");
                        break;
                    }
                    System.out.println("Escolha o candidato:");
                    for(int i=0; i<candidatos.size(); i++) {
                        System.out.println(i + " - " + candidatos.get(i).getNome());
                    }
                    int idxCand = sc.nextInt(); sc.nextLine();
                    Candidato candidatoSelecionado = candidatos.get(idxCand);

                    System.out.println("Escolha a vaga:");
                    for(int i=0; i<vagas.size(); i++) {
                        System.out.println(i + " - " + vagas.get(i).getTitulo());
                    }
                    int idxVaga = sc.nextInt(); sc.nextLine();
                    Vaga vagaSelecionada = vagas.get(idxVaga);

                    candidatoSelecionado.candidatar(vagaSelecionada);
                    Candidatura cand = new Candidatura(candidaturas.size()+1, candidatoSelecionado, vagaSelecionada, LocalDate.now());
                    candidaturas.add(cand);
                    break;

                case 6:
                    System.out.println("=== Candidaturas ===");
                    for(Candidatura cdt : candidaturas) {
                        cdt.exibirStatus();
                        System.out.println("------------------------");
                    }
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }

        sc.close();

	}

}
