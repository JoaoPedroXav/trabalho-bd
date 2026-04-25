
package com.mycompany.casoclinico;
import java.sql.*;
import java.util.Scanner;
/**
 *
 * @author joaop
 */
public class CasoClinico {

    static Connection conn;
    static Scanner sc = new Scanner(System.in);
    static int idAluno;
    static String nomeAluno;
 
    public static void main(String[] args) {
        conn = Conexao.conectar();
 
        if (conn == null) {
            System.out.println("Nao foi possivel conectar. Encerrando.");
            return;
        }
 
        telaInicial();
    }
 
    static void telaInicial() {
        System.out.println("\nSistema de Casos Clínicos");
        System.out.println("1. Cadastrar");
        System.out.println("2. Fazer Login");
        System.out.println("0. Sair");
        System.out.print("Opcao: ");
 
        String opcao = sc.nextLine();
 
        if (opcao.equals("1")) {
            cadastrar();
        } else if (opcao.equals("2")) {
            login();
        } else if (opcao.equals("0")) {
            System.out.println("Ate logo!");
        } else {
            System.out.println("Opcao invalida. Tente Novamente");
            telaInicial();
        }
    }
 
    static void cadastrar() {
        System.out.println("\nCadastro de aluno");
        System.out.print("Nome: ");
        String nome = sc.nextLine();
 
        System.out.print("Matricula: ");
        String matricula = sc.nextLine();
 
        System.out.print("Email: ");
        String email = sc.nextLine();
 
        System.out.print("Curso: ");
        String curso = sc.nextLine();
 
        System.out.print("Senha: ");
        String senha = sc.nextLine();
 
        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO aluno (nome, matricula, email, curso, senha) VALUES (?, ?, ?, ?, ?)"
            );
            ps.setString(1, nome);
            ps.setString(2, matricula);
            ps.setString(3, email);
            ps.setString(4, curso);
            ps.setString(5, senha);
            ps.executeUpdate();
 
            System.out.println("Cadastro realizado com sucesso! Faca o login para continuar.");
            login();
 
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
            telaInicial();
        }
    }
  
    static void login() {
        System.out.println("\nLogin");
        System.out.print("Matricula: ");
        String matricula = sc.nextLine();
 
        System.out.print("Senha: ");
        String senha = sc.nextLine();
 
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT id_aluno, nome FROM aluno WHERE matricula = ? AND senha = ?"
            );
            ps.setString(1, matricula);
            ps.setString(2, senha);
            ResultSet rs = ps.executeQuery();
 
            if (rs.next()) {
                idAluno   = rs.getInt("id_aluno");
                nomeAluno = rs.getString("nome");
                System.out.println("Bem-vindo, " + nomeAluno + "!");
                menu();
            } else {
                System.out.println("Matricula ou senha incorretos. Tente novamente.");
                telaInicial();
            }
 
        } catch (SQLException e) {
            System.out.println("Erro no login: " + e.getMessage());
        }
    }
 
    static void menu() {
        int opcao = -1;
 
        while (opcao != 0) {
            System.out.println("\nMenu principal");
            System.out.println("1. Ver casos clinicos");
            System.out.println("2. Responder um caso");
            System.out.println("3. Ver minhas respostas");
            System.out.println("4. Atualizar resposta");
            System.out.println("5. Excluir resposta");
            System.out.println("6. Filtrar casos por area");
            System.out.println("7. Relatorio com INNER JOIN");
            System.out.println("8. Relatorio com LEFT JOIN");
            System.out.println("0. Sair");
            System.out.print("Opcao: ");
 
            opcao = Integer.parseInt(sc.nextLine());
 
            if (opcao == 1) listarCasos();
            else if (opcao == 2) responderCaso();
            else if (opcao == 3) minhasRespostas();
            else if (opcao == 4) atualizarResposta();
            else if (opcao == 5) excluirResposta();
            else if (opcao == 6) filtrarPorArea();
            else if (opcao == 7) relatorioInnerJoin();
            else if (opcao == 8) relatorioLeftJoin();
            else if (opcao == 0) System.out.println("Encerrando o sistema.");
            else System.out.println("Opcao invalida.");
        }
    }
 
    static void listarCasos() {
        System.out.println("\nLista de casos clínicos");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT id_caso, titulo, area, nivel_dificuldade FROM caso_clinico ORDER BY area"
            );
 
            while (rs.next()) {
                System.out.println("[" + rs.getInt("id_caso") + "] " + rs.getString("titulo")
                    + " | " + rs.getString("area")
                    + " | " + rs.getString("nivel_dificuldade"));
            }
 
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    static void responderCaso() {
        listarCasos();
        System.out.print("\nDigite o ID do caso: ");
        int idCaso = Integer.parseInt(sc.nextLine());
 
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT titulo, descricao FROM caso_clinico WHERE id_caso = ?"
            );
            ps.setInt(1, idCaso);
            ResultSet rs = ps.executeQuery();
 
            if (rs.next()) {
                System.out.println("\n" + rs.getString("titulo"));
                System.out.println(rs.getString("descricao"));
            } else {
                System.out.println("Caso nao encontrado.");
                return;
            }
 
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }
 
        System.out.print("\nDigite sua resposta: ");
        String resposta = sc.nextLine();
 
        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO resposta (id_aluno, id_caso, texto_resposta) VALUES (?, ?, ?)"
            );
            ps.setInt(1, idAluno);
            ps.setInt(2, idCaso);
            ps.setString(3, resposta);
            ps.executeUpdate();
            System.out.println("Resposta enviada com sucesso!");
 
        } catch (SQLException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }
 
    static void minhasRespostas() {
        System.out.println("\nSuas respostas");
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT r.id_resposta, cc.titulo, r.nota, r.feedback, r.data_envio " +
                "FROM resposta r " +
                "INNER JOIN caso_clinico cc ON r.id_caso = cc.id_caso " +
                "WHERE r.id_aluno = ? " +
                "ORDER BY r.data_envio DESC"
            );
            ps.setInt(1, idAluno);
            ResultSet rs = ps.executeQuery();
 
            boolean temResultado = false;
            while (rs.next()) {
                temResultado = true;
                System.out.println("\nID: "       + rs.getInt("id_resposta"));
                System.out.println("Caso: "       + rs.getString("titulo"));
                System.out.println("Nota: "       + rs.getString("nota"));
                System.out.println("Feedback: "   + rs.getString("feedback"));
                System.out.println("Enviado em: " + rs.getTimestamp("data_envio"));
            }
 
            if (!temResultado) {
                System.out.println("Nenhuma resposta encontrada.");
            }
 
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
  
    static void atualizarResposta() {
        minhasRespostas();
        System.out.print("\nID da resposta que deseja atualizar: ");
        int idResposta = Integer.parseInt(sc.nextLine());
 
        System.out.print("Novo texto da resposta: ");
        String novoTexto = sc.nextLine();
 
        try {
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE resposta SET texto_resposta = ? WHERE id_resposta = ? AND id_aluno = ?"
            );
            ps.setString(1, novoTexto);
            ps.setInt(2, idResposta);
            ps.setInt(3, idAluno);
 
            int linhas = ps.executeUpdate();
            if (linhas > 0) {
                System.out.println("Resposta atualizada!");
            } else {
                System.out.println("Resposta nao encontrada.");
            }
 
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
  
    static void excluirResposta() {
        minhasRespostas();
        System.out.print("\nID do caso que que deseja excluir a resposta: ");
        int idResposta = Integer.parseInt(sc.nextLine());
 
        System.out.print("Tem certeza? (s/n): ");
        String confirmacao = sc.nextLine();
 
        if (!confirmacao.equals("s")) {
            System.out.println("Operacao cancelada.");
            return;
        }
 
        try {
            PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM resposta WHERE id_resposta = ? AND id_aluno = ?"
            );
            ps.setInt(1, idResposta);
            ps.setInt(2, idAluno);
 
            int linhas = ps.executeUpdate();
            if (linhas > 0) {
                System.out.println("Resposta excluida!");
            } else {
                System.out.println("Resposta nao encontrada.");
            }
 
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
  
    static void filtrarPorArea() {
        System.out.print("\nDigite a area (Fisioterapia / Medicina / Odontologia): ");
        String area = sc.nextLine();
 
        System.out.println("\nResultados encontrados");
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT id_caso, titulo, nivel_dificuldade FROM caso_clinico " +
                "WHERE area = ? ORDER BY nivel_dificuldade"
            );
            ps.setString(1, area);
            ResultSet rs = ps.executeQuery();
 
            boolean temResultado = false;
            while (rs.next()) {
                temResultado = true;
                System.out.println("[" + rs.getInt("id_caso") + "] "
                    + rs.getString("titulo")
                    + " | " + rs.getString("nivel_dificuldade"));
            }
 
            if (!temResultado) {
                System.out.println("Nenhum caso encontrado para essa area.");
            }
 
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
  
    static void relatorioInnerJoin() {
        System.out.println("\nAlunos que responderam casos");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT a.nome, a.matricula, cc.titulo, cc.area, r.nota " +
                "FROM resposta r " +
                "INNER JOIN aluno a ON r.id_aluno = a.id_aluno " +
                "INNER JOIN caso_clinico cc ON r.id_caso = cc.id_caso " +
                "ORDER BY a.nome"
            );
 
            while (rs.next()) {
                System.out.println("Aluno: "    + rs.getString("nome")
                    + " | Matricula: "          + rs.getString("matricula")
                    + " | Caso: "               + rs.getString("titulo")
                    + " | Area: "               + rs.getString("area")
                    + " | Nota: "               + rs.getString("nota"));
            }
 
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
 
    static void relatorioLeftJoin() {
        System.out.println("\nCasos e quantidade de respostas");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT cc.titulo, cc.area, COUNT(r.id_resposta) AS total " +
                "FROM caso_clinico cc " +
                "LEFT JOIN resposta r ON cc.id_caso = r.id_caso " +
                "GROUP BY cc.id_caso, cc.titulo, cc.area " +
                "ORDER BY total DESC"
            );
 
            while (rs.next()) {
                System.out.println("Caso: "  + rs.getString("titulo")
                    + " | Area: "            + rs.getString("area")
                    + " | Respostas: "       + rs.getInt("total"));
            }
 
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
