package com.example.lojaJogos.gerenciador;

import com.example.lojaJogos.conexao.ConexaoBanco;
import com.example.lojaJogos.model.Plataforma;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class GerenciadorPlataforma {

    public void inserir(Plataforma plataforma) {
        String querySql = "INSERT INTO Plataforma (nome, fabricante) VALUES (?, ?)";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setString(1, plataforma.getNome());
            comandoPreparado.setString(2, plataforma.getFabricante());

            comandoPreparado.executeUpdate();

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao inserir plataforma no banco de dados", erroSql);
        }
    }

    public List<Plataforma> listarTodos() {
        List<Plataforma> listaDePlataformas = new ArrayList<>();
        String querySql = "SELECT id, nome, fabricante FROM Plataforma";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             Statement comandoSimples = conexaoBanco.createStatement();
             ResultSet resultadoDaBusca = comandoSimples.executeQuery(querySql)) {

            while (resultadoDaBusca.next()) {
                listaDePlataformas.add(Plataforma.builder()
                        .id(resultadoDaBusca.getInt("id"))
                        .nome(resultadoDaBusca.getString("nome"))
                        .fabricante(resultadoDaBusca.getString("fabricante"))
                        .build());
            }

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao listar as plataformas do banco de dados", erroSql);
        }
        return listaDePlataformas;
    }

    public Plataforma buscarPorId(Integer id) {
        String querySql = "SELECT id, nome, fabricante FROM Plataforma WHERE id = ?";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setInt(1, id);

            try (ResultSet resultadoDaBusca = comandoPreparado.executeQuery()) {
                if (resultadoDaBusca.next()) {
                    return Plataforma.builder()
                            .id(resultadoDaBusca.getInt("id"))
                            .nome(resultadoDaBusca.getString("nome"))
                            .fabricante(resultadoDaBusca.getString("fabricante"))
                            .build();
                }
            }

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao buscar a plataforma pelo ID", erroSql);
        }
        return null;
    }

    public void editar(Plataforma plataforma) {
        String querySql = "UPDATE Plataforma SET nome = ?, fabricante = ? WHERE id = ?";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setString(1, plataforma.getNome());
            comandoPreparado.setString(2, plataforma.getFabricante());
            comandoPreparado.setInt(3, plataforma.getId());

            comandoPreparado.executeUpdate();

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao atualizar a plataforma no banco de dados", erroSql);
        }
    }

    public void deletar(Integer id) {
        String querySql = "DELETE FROM Plataforma WHERE id = ?";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setInt(1, id);
            comandoPreparado.executeUpdate();

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao deletar a plataforma do banco de dados", erroSql);
        }
    }
}