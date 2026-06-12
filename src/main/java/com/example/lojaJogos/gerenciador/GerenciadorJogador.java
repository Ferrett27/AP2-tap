package com.example.lojaJogos.gerenciador;

import com.example.lojaJogos.conexao.ConexaoBanco;
import com.example.lojaJogos.model.Jogador;
import com.example.lojaJogos.observer.Observador;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class GerenciadorJogador implements OperacoesCrud<Jogador, List<String>>, OperacoesJogador {

    private final List<Observador> listaDeObservadores;

    public GerenciadorJogador(List<Observador> listaDeObservadores) {
        this.listaDeObservadores = listaDeObservadores;
    }

    private List<String> notificarObservadores(Jogador jogadorSalvo) {
        List<String> logsGerados = new ArrayList<>();
        for (Observador observador : listaDeObservadores) {
            logsGerados.add(observador.reagirNovoJogadorAdicionado(jogadorSalvo));
        }
        return logsGerados;
    }

    @Override
    public List<String> inserir(Jogador jogador) {
        String querySql = "INSERT INTO Jogador (nome, numero_camisa, posicao, idade, id_selecao) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql, Statement.RETURN_GENERATED_KEYS)) {

            comandoPreparado.setString(1, jogador.getNome());
            comandoPreparado.setInt(2, jogador.getNumeroCamisa());
            comandoPreparado.setString(3, jogador.getPosicao());
            comandoPreparado.setInt(4, jogador.getIdade());
            comandoPreparado.setInt(5, jogador.getIdSelecao());

            comandoPreparado.executeUpdate();

            try (ResultSet chavesGeradas = comandoPreparado.getGeneratedKeys()) {
                if (chavesGeradas.next()) {
                    jogador.setIdJogador(chavesGeradas.getInt(1));
                }
            }

            return notificarObservadores(jogador);

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao inserir o jogador no banco de dados", erroSql);
        }
    }

    @Override
    public List<Jogador> listarTodos() {
        List<Jogador> listaDeJogadores = new ArrayList<>();
        String querySql = "SELECT id_jogador, nome, numero_camisa, posicao, idade, id_selecao FROM Jogador";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             Statement comandoSimples = conexaoBanco.createStatement();
             ResultSet resultadoDaBusca = comandoSimples.executeQuery(querySql)) {

            while (resultadoDaBusca.next()) {
                listaDeJogadores.add(Jogador.builder()
                        .idJogador(resultadoDaBusca.getInt("id_jogador"))
                        .nome(resultadoDaBusca.getString("nome"))
                        .numeroCamisa(resultadoDaBusca.getInt("numero_camisa"))
                        .posicao(resultadoDaBusca.getString("posicao"))
                        .idade(resultadoDaBusca.getInt("idade"))
                        .idSelecao(resultadoDaBusca.getInt("id_selecao"))
                        .build());
            }

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao listar os jogadores do banco de dados", erroSql);
        }
        return listaDeJogadores;
    }

    @Override
    public Jogador buscarPorId(Integer id) {
        String querySql = "SELECT id_jogador, nome, numero_camisa, posicao, idade, id_selecao FROM Jogador WHERE id_jogador = ?";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setInt(1, id);

            try (ResultSet resultadoDaBusca = comandoPreparado.executeQuery()) {
                if (resultadoDaBusca.next()) {
                    return Jogador.builder()
                            .idJogador(resultadoDaBusca.getInt("id_jogador"))
                            .nome(resultadoDaBusca.getString("nome"))
                            .numeroCamisa(resultadoDaBusca.getInt("numero_camisa"))
                            .posicao(resultadoDaBusca.getString("posicao"))
                            .idade(resultadoDaBusca.getInt("idade"))
                            .idSelecao(resultadoDaBusca.getInt("id_selecao"))
                            .build();
                }
            }

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao buscar o jogador pelo ID", erroSql);
        }
        return null;
    }

    @Override
    public void editar(Jogador jogador) {
        String querySql = "UPDATE Jogador SET nome = ?, numero_camisa = ?, posicao = ?, idade = ?, id_selecao = ? WHERE id_jogador = ?";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setString(1, jogador.getNome());
            comandoPreparado.setInt(2, jogador.getNumeroCamisa());
            comandoPreparado.setString(3, jogador.getPosicao());
            comandoPreparado.setInt(4, jogador.getIdade());
            comandoPreparado.setInt(5, jogador.getIdSelecao());
            comandoPreparado.setInt(6, jogador.getIdJogador());

            comandoPreparado.executeUpdate();

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao atualizar o jogador no banco de dados", erroSql);
        }
    }

    @Override
    public void deletar(Integer id) {
        String querySql = "DELETE FROM Jogador WHERE id_jogador = ?";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setInt(1, id);
            comandoPreparado.executeUpdate();

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao deletar o jogador do banco de dados", erroSql);
        }
    }

    @Override
    public List<Jogador> buscarPorSelecao(Integer idSelecao) {
        List<Jogador> elenco = new ArrayList<>();
        String querySql = "SELECT id_jogador, nome, numero_camisa, posicao, idade, id_selecao FROM Jogador WHERE id_selecao = ?";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setInt(1, idSelecao);

            try (ResultSet resultadoDaBusca = comandoPreparado.executeQuery()) {
                while (resultadoDaBusca.next()) {
                    elenco.add(Jogador.builder()
                            .idJogador(resultadoDaBusca.getInt("id_jogador"))
                            .nome(resultadoDaBusca.getString("nome"))
                            .numeroCamisa(resultadoDaBusca.getInt("numero_camisa"))
                            .posicao(resultadoDaBusca.getString("posicao"))
                            .idade(resultadoDaBusca.getInt("idade"))
                            .idSelecao(resultadoDaBusca.getInt("id_selecao"))
                            .build());
                }
            }
        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao buscar os jogadores da seleção", erroSql);
        }
        return elenco;
    }
}