package com.example.lojaJogos.observer;

import com.example.lojaJogos.model.Jogo;

public interface Observador {
    String reagirNovoJogoAdicionado(Jogo jogo);
}
