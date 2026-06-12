package com.example.lojaJogos.observer;

import com.example.lojaJogos.model.Jogador;

public interface Observador {
    String reagirNovoJogadorAdicionado(Jogador jogador);
}
