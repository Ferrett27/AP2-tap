package com.example.lojaJogos.gerenciador;

import com.example.lojaJogos.conexao.ConexaoBanco;
import com.example.lojaJogos.model.Selecao;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class GerenciadorSelecao implements OperacoesCrud<Selecao, Void> {

    @Override
    public Void inserir(Selecao selecao) {
        String querySql = "INSERT INTO Selecao (nome_pais, tecnico, ranking_fifa) VALUES (?, ?, ?)";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setString(1, selecao.getNomePais());
            comandoPreparado.setString(2, selecao.getTecnico());
            comandoPreparado.setInt(3, selecao.getRankingFifa());

            comandoPreparado.executeUpdate();

            return null;

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao inserir a seleção no banco de dados", erroSql);
        }
    }

    @Override
    public List<Selecao> listarTodos() {
        List<Selecao> listaDeSelecoes = new ArrayList<>();
        String querySql = "SELECT id_selecao, nome_pais, tecnico, ranking_fifa FROM Selecao";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             Statement comandoSimples = conexaoBanco.createStatement();
             ResultSet resultadoDaBusca = comandoSimples.executeQuery(querySql)) {

            while (resultadoDaBusca.next()) {
                listaDeSelecoes.add(Selecao.builder()
                        .idSelecao(resultadoDaBusca.getInt("id_selecao"))
                        .nomePais(resultadoDaBusca.getString("nome_pais"))
                        .tecnico(resultadoDaBusca.getString("tecnico"))
                        .rankingFifa(resultadoDaBusca.getInt("ranking_fifa"))
                        .build());
            }

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao listar as seleções do banco de dados", erroSql);
        }
        return listaDeSelecoes;
    }

    @Override
    public Selecao buscarPorId(Integer id) {
        String querySql = "SELECT id_selecao, nome_pais, tecnico, ranking_fifa FROM Selecao WHERE id_selecao = ?";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setInt(1, id);

            try (ResultSet resultadoDaBusca = comandoPreparado.executeQuery()) {
                if (resultadoDaBusca.next()) {
                    return Selecao.builder()
                            .idSelecao(resultadoDaBusca.getInt("id_selecao"))
                            .nomePais(resultadoDaBusca.getString("nome_pais"))
                            .tecnico(resultadoDaBusca.getString("tecnico"))
                            .rankingFifa(resultadoDaBusca.getInt("ranking_fifa"))
                            .build();
                }
            }

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao buscar a seleção pelo ID", erroSql);
        }
        return null;
    }

    @Override
    public void editar(Selecao selecao) {
        String querySql = "UPDATE Selecao SET nome_pais = ?, tecnico = ?, ranking_fifa = ? WHERE id_selecao = ?";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setString(1, selecao.getNomePais());
            comandoPreparado.setString(2, selecao.getTecnico());
            comandoPreparado.setInt(3, selecao.getRankingFifa());
            comandoPreparado.setInt(4, selecao.getIdSelecao());

            comandoPreparado.executeUpdate();

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao atualizar a seleção no banco de dados", erroSql);
        }
    }

    @Override
    public void deletar(Integer id) {
        String querySql = "DELETE FROM Selecao WHERE id_selecao = ?";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setInt(1, id);
            comandoPreparado.executeUpdate();

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao deletar a seleção do banco de dados", erroSql);
        }
    }
}