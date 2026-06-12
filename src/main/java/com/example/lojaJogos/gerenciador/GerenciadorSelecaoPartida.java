package com.example.lojaJogos.gerenciador;

import com.example.lojaJogos.conexao.ConexaoBanco;
import com.example.lojaJogos.model.SelecaoPartida;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class GerenciadorSelecaoPartida implements OperacoesCrudRelacao<SelecaoPartida> {

    public void vincular(Integer idSelecao, Integer idPartida) {
        String querySql = "INSERT INTO Selecao_Partida (id_selecao, id_partida) VALUES (?, ?)";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setInt(1, idSelecao);
            comandoPreparado.setInt(2, idPartida);

            comandoPreparado.executeUpdate();

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao vincular a seleção à partida no banco de dados", erroSql);
        }
    }

    public List<SelecaoPartida> listarTodos() {
        List<SelecaoPartida> listaDeVinculos = new ArrayList<>();
        String querySql = "SELECT id_selecao, id_partida FROM Selecao_Partida";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             Statement comandoSimples = conexaoBanco.createStatement();
             ResultSet resultadoDaBusca = comandoSimples.executeQuery(querySql)) {

            while (resultadoDaBusca.next()) {
                listaDeVinculos.add(SelecaoPartida.builder()
                        .idSelecao(resultadoDaBusca.getInt("id_selecao"))
                        .idPartida(resultadoDaBusca.getInt("id_partida"))
                        .build());
            }

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao listar os vínculos do banco de dados", erroSql);
        }
        return listaDeVinculos;
    }

    public void desvincular(Integer idSelecao, Integer idPartida) {
        String querySql = "DELETE FROM Selecao_Partida WHERE id_selecao = ? AND id_partida = ?";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setInt(1, idSelecao);
            comandoPreparado.setInt(2, idPartida);

            comandoPreparado.executeUpdate();

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao remover o vínculo do banco de dados", erroSql);
        }
    }
}