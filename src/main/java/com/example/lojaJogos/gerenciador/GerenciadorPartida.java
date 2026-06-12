package com.example.lojaJogos.gerenciador;

import com.example.lojaJogos.conexao.ConexaoBanco;
import com.example.lojaJogos.model.Partida;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class GerenciadorPartida implements OperacoesPartida {

    @Override
    public Void inserir(Partida partida) {
        String querySql = "INSERT INTO Partida (data_partida, estadio, fase_competicao, placar) VALUES (?, ?, ?, ?)";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setDate(1, Date.valueOf(partida.getDataPartida()));
            comandoPreparado.setString(2, partida.getEstadio());
            comandoPreparado.setString(3, partida.getFaseCompeticao());
            comandoPreparado.setString(4, partida.getPlacar());

            comandoPreparado.executeUpdate();

            return null;

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao inserir a partida no banco de dados", erroSql);
        }
    }

    @Override
    public List<Partida> listarTodos() {
        List<Partida> listaDePartidas = new ArrayList<>();
        String querySql = "SELECT id_partida, data_partida, estadio, fase_competicao, placar FROM Partida";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             Statement comandoSimples = conexaoBanco.createStatement();
             ResultSet resultadoDaBusca = comandoSimples.executeQuery(querySql)) {

            while (resultadoDaBusca.next()) {
                listaDePartidas.add(Partida.builder()
                        .idPartida(resultadoDaBusca.getInt("id_partida"))
                        .dataPartida(resultadoDaBusca.getDate("data_partida").toLocalDate())
                        .estadio(resultadoDaBusca.getString("estadio"))
                        .faseCompeticao(resultadoDaBusca.getString("fase_competicao"))
                        .placar(resultadoDaBusca.getString("placar"))
                        .build());
            }

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao listar as partidas do banco de dados", erroSql);
        }
        return listaDePartidas;
    }

    @Override
    public Partida buscarPorId(Integer id) {
        String querySql = "SELECT id_partida, data_partida, estadio, fase_competicao, placar FROM Partida WHERE id_partida = ?";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setInt(1, id);

            try (ResultSet resultadoDaBusca = comandoPreparado.executeQuery()) {
                if (resultadoDaBusca.next()) {
                    return Partida.builder()
                            .idPartida(resultadoDaBusca.getInt("id_partida"))
                            .dataPartida(resultadoDaBusca.getDate("data_partida").toLocalDate())
                            .estadio(resultadoDaBusca.getString("estadio"))
                            .faseCompeticao(resultadoDaBusca.getString("fase_competicao"))
                            .placar(resultadoDaBusca.getString("placar"))
                            .build();
                }
            }

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao buscar a partida pelo ID", erroSql);
        }
        return null;
    }

    @Override
    public void editar(Partida partida) {
        String querySql = "UPDATE Partida SET data_partida = ?, estadio = ?, fase_competicao = ?, placar = ? WHERE id_partida = ?";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setDate(1, Date.valueOf(partida.getDataPartida()));
            comandoPreparado.setString(2, partida.getEstadio());
            comandoPreparado.setString(3, partida.getFaseCompeticao());
            comandoPreparado.setString(4, partida.getPlacar());
            comandoPreparado.setInt(5, partida.getIdPartida());

            comandoPreparado.executeUpdate();

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao atualizar a partida no banco de dados", erroSql);
        }
    }

    @Override
    public void deletar(Integer id) {
        String querySql = "DELETE FROM Partida WHERE id_partida = ?";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setInt(1, id);
            comandoPreparado.executeUpdate();

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao deletar a partida do banco de dados", erroSql);
        }
    }

    @Override
    public List<Partida> buscarPartidasDaSelecao(Integer idSelecao) {
        List<Partida> partidas = new ArrayList<>();

        String querySql = "SELECT partida.id_partida, partida.data_partida, partida.estadio, partida.fase_competicao, partida.placar " +
                "FROM Partida partida " +
                "INNER JOIN Selecao_Partida selecaoPartida ON partida.id_partida = selecaoPartida.id_partida " +
                "WHERE selecaoPartida.id_selecao = ?";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setInt(1, idSelecao);

            try (ResultSet resultadoDaBusca = comandoPreparado.executeQuery()) {
                while (resultadoDaBusca.next()) {
                    partidas.add(Partida.builder()
                            .idPartida(resultadoDaBusca.getInt("id_partida"))
                            .dataPartida(resultadoDaBusca.getDate("data_partida").toLocalDate())
                            .estadio(resultadoDaBusca.getString("estadio"))
                            .faseCompeticao(resultadoDaBusca.getString("fase_competicao"))
                            .placar(resultadoDaBusca.getString("placar"))
                            .build());
                }
            }
        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao buscar a agenda de partidas desta seleção", erroSql);
        }
        return partidas;
    }
}