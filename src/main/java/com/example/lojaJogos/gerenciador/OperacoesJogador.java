package com.example.lojaJogos.gerenciador;

import com.example.lojaJogos.model.Jogador;

import java.util.List;

public interface OperacoesJogador extends OperacoesCrud<Jogador, List<String>> {

    List<Jogador> buscarPorSelecao(Integer idSelecao);
}
