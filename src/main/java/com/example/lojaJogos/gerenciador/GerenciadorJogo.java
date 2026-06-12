package com.example.lojaJogos.gerenciador;

import com.example.lojaJogos.conexao.ConexaoBanco;
import com.example.lojaJogos.model.Jogo;
import com.example.lojaJogos.observer.Observador;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class GerenciadorJogo implements OperacoesCrud<Jogo, List<String>> {

    private final List<Observador> listaDeObservadores;

    public GerenciadorJogo(List<Observador> listaDeObservadores) {
        this.listaDeObservadores = listaDeObservadores;
    }

    private List<String> notificarObservadores(Jogo jogoSalvo) {
        List<String> logsGerados = new ArrayList<>();
        for (Observador observador : listaDeObservadores) {
            logsGerados.add(observador.reagirNovoJogoAdicionado(jogoSalvo));
        }
        return logsGerados;
    }

    public List<String> inserir(Jogo jogo) {
        String querySql = "INSERT INTO Jogo (titulo, preco, data_lancamento, editora_id) VALUES (?, ?, ?, ?)";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql, Statement.RETURN_GENERATED_KEYS)) {

            comandoPreparado.setString(1, jogo.getTitulo());
            comandoPreparado.setBigDecimal(2, jogo.getPreco());
            comandoPreparado.setDate(3, Date.valueOf(jogo.getDataLancamento()));
            comandoPreparado.setObject(4, jogo.getEditoraId());

            comandoPreparado.executeUpdate();

            try (ResultSet chavesGeradas = comandoPreparado.getGeneratedKeys()) {
                if (chavesGeradas.next()) {
                    jogo.setId(chavesGeradas.getInt(1));
                }
            }

            return notificarObservadores(jogo);

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao inserir o jogo no banco de dados", erroSql);
        }
    }

    public List<Jogo> listarTodos() {
        List<Jogo> listaDeJogos = new ArrayList<>();
        String querySql = "SELECT id, titulo, preco, data_lancamento, editora_id FROM Jogo";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             Statement comandoSimples = conexaoBanco.createStatement();
             ResultSet resultadoDaBusca = comandoSimples.executeQuery(querySql)) {

            while (resultadoDaBusca.next()) {
                listaDeJogos.add(Jogo.builder()
                        .id(resultadoDaBusca.getInt("id"))
                        .titulo(resultadoDaBusca.getString("titulo"))
                        .preco(resultadoDaBusca.getBigDecimal("preco"))
                        .dataLancamento(resultadoDaBusca.getDate("data_lancamento").toLocalDate())
                        .editoraId(resultadoDaBusca.getInt("editora_id"))
                        .build());
            }

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao listar os jogos do banco de dados", erroSql);
        }
        return listaDeJogos;
    }

    public Jogo buscarPorId(Integer id) {
        String querySql = "SELECT id, titulo, preco, data_lancamento, editora_id FROM Jogo WHERE id = ?";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setInt(1, id);

            try (ResultSet resultadoDaBusca = comandoPreparado.executeQuery()) {
                if (resultadoDaBusca.next()) {
                    return Jogo.builder()
                            .id(resultadoDaBusca.getInt("id"))
                            .titulo(resultadoDaBusca.getString("titulo"))
                            .preco(resultadoDaBusca.getBigDecimal("preco"))
                            .dataLancamento(resultadoDaBusca.getDate("data_lancamento").toLocalDate())
                            .editoraId(resultadoDaBusca.getInt("editora_id"))
                            .build();
                }
            }

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao buscar o jogo pelo ID", erroSql);
        }
        return null;
    }

    public void editar(Jogo jogo) {
        String querySql = "UPDATE Jogo SET titulo = ?, preco = ?, data_lancamento = ?, editora_id = ? WHERE id = ?";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setString(1, jogo.getTitulo());
            comandoPreparado.setBigDecimal(2, jogo.getPreco());
            comandoPreparado.setDate(3, Date.valueOf(jogo.getDataLancamento()));
            comandoPreparado.setObject(4, jogo.getEditoraId());
            comandoPreparado.setInt(5, jogo.getId());

            comandoPreparado.executeUpdate();

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao atualizar o jogo no banco de dados", erroSql);
        }
    }

    public void deletar(Integer id) {
        String querySql = "DELETE FROM Jogo WHERE id = ?";

        try (Connection conexaoBanco = ConexaoBanco.getInstancia().getConexao();
             PreparedStatement comandoPreparado = conexaoBanco.prepareStatement(querySql)) {

            comandoPreparado.setInt(1, id);
            comandoPreparado.executeUpdate();

        } catch (SQLException erroSql) {
            throw new RuntimeException("Erro ao deletar o jogo do banco de dados", erroSql);
        }
    }
}