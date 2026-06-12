package com.example.lojaJogos.gerenciador;

import com.example.lojaJogos.conexao.ConexaoBanco;
import com.example.lojaJogos.model.Editora;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class GerenciadorEditora implements OperacoesCrud<Editora, Void> {

    public Void inserir(Editora editora) {
        String querySql = "INSERT INTO Editora (nome, ano_fundacao, pais_origem) VALUES (?, ?, ?)";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setString(1, editora.getNome());
            comandoPreparado.setInt(2, editora.getAnoFundacao());
            comandoPreparado.setString(3, editora.getPaisOrigem());

            comandoPreparado.executeUpdate();

            return null;

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao inserir editora no banco de dados", erroSql);
        }
    }

    public List<Editora> listarTodos() {
        List<Editora> listaDeEditoras = new ArrayList<>();
        String querySql = "SELECT id, nome, ano_fundacao, pais_origem FROM Editora";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             Statement comandoSimples = conexaoBanco.createStatement();
             ResultSet resultadoDaBusca = comandoSimples.executeQuery(querySql)) {

            while (resultadoDaBusca.next()) {
                listaDeEditoras.add(Editora.builder()
                        .id(resultadoDaBusca.getInt("id"))
                        .nome(resultadoDaBusca.getString("nome"))
                        .anoFundacao(resultadoDaBusca.getInt("ano_fundacao"))
                        .paisOrigem(resultadoDaBusca.getString("pais_origem"))
                        .build());
            }

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao listar as editoras do banco de dados", erroSql);
        }
        return listaDeEditoras;
    }

    public Editora buscarPorId(Integer id) {
        String querySql = "SELECT id, nome, ano_fundacao, pais_origem FROM Editora WHERE id = ?";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setInt(1, id);

            try (ResultSet resultadoDaBusca = comandoPreparado.executeQuery()) {
                if (resultadoDaBusca.next()) {
                    return Editora.builder()
                            .id(resultadoDaBusca.getInt("id"))
                            .nome(resultadoDaBusca.getString("nome"))
                            .anoFundacao(resultadoDaBusca.getInt("ano_fundacao"))
                            .paisOrigem(resultadoDaBusca.getString("pais_origem"))
                            .build();
                }
            }

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao buscar a editora pelo ID", erroSql);
        }
        return null;
    }

    public void editar(Editora editora) {
        String querySql = "UPDATE Editora SET nome = ?, ano_fundacao = ?, pais_origem = ? WHERE id = ?";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setString(1, editora.getNome());
            comandoPreparado.setInt(2, editora.getAnoFundacao());
            comandoPreparado.setString(3, editora.getPaisOrigem());
            comandoPreparado.setInt(4, editora.getId());

            comandoPreparado.executeUpdate();

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao atualizar a editora no banco de dados", erroSql);
        }
    }

    public void deletar(Integer id) {
        String querySql = "DELETE FROM Editora WHERE id = ?";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setInt(1, id);
            comandoPreparado.executeUpdate();

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao deletar a editora do banco de dados", erroSql);
        }
    }
}