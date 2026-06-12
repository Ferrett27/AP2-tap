package com.example.lojaJogos.gerenciador;

import com.example.lojaJogos.conexao.ConexaoBanco;
import com.example.lojaJogos.model.JogoPlataforma;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class GerenciadorJogoPlataforma implements OperacoesCrudRelacao<JogoPlataforma> {

    public void vincular(Integer jogoId, Integer plataformaId) {
        String querySql = "INSERT INTO Jogo_Plataforma (jogo_id, plataforma_id) VALUES (?, ?)";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setInt(1, jogoId);
            comandoPreparado.setInt(2, plataformaId);

            comandoPreparado.executeUpdate();

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao vincular o jogo à plataforma no banco de dados", erroSql);
        }
    }

    public List<JogoPlataforma> listarTodos() {
        List<JogoPlataforma> listaDeVinculos = new ArrayList<>();
        String querySql = "SELECT jogo_id, plataforma_id FROM Jogo_Plataforma";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             Statement comandoSimples = conexaoBanco.createStatement();
             ResultSet resultadoDaBusca = comandoSimples.executeQuery(querySql)) {

            while (resultadoDaBusca.next()) {
                listaDeVinculos.add(JogoPlataforma.builder()
                        .jogoId(resultadoDaBusca.getInt("jogo_id"))
                        .plataformaId(resultadoDaBusca.getInt("plataforma_id"))
                        .build());
            }

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao listar os vínculos do banco de dados", erroSql);
        }
        return listaDeVinculos;
    }

    public void desvincular(Integer jogoId, Integer plataformaId) {
        String querySql = "DELETE FROM Jogo_Plataforma WHERE jogo_id = ? AND plataforma_id = ?";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setInt(1, jogoId);
            comandoPreparado.setInt(2, plataformaId);

            comandoPreparado.executeUpdate();

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao remover o vínculo do banco de dados", erroSql);
        }
    }
}