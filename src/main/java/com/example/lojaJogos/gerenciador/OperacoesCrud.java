package com.example.lojaJogos.gerenciador;

import java.util.List;

public interface OperacoesCrud<Entidade, TipoInserir> {
    TipoInserir inserir(Entidade entidade);
    List<Entidade> listarTodos();
    Entidade buscarPorId(Integer id);
    void editar(Entidade entidade);
    void deletar(Integer id);
}
