package com.example.lojaJogos.gerenciador;

import com.example.lojaJogos.model.Partida;
import java.util.List;

public interface OperacoesPartida extends OperacoesCrud<Partida, Void> {
    List<Partida> buscarPartidasDaSelecao(Integer idSelecao);
}
