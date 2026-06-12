package com.example.lojaJogos.gerenciador;

import java.util.List;

public interface OperacoesCrudRelacao<Entidade> {
    void vincular(Integer IdPrimario, Integer IdSecundario);
    void desvincular(Integer IdPrimario, Integer IdSecundario);
    List<Entidade> listarTodos();
}
