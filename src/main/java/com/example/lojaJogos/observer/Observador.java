package com.example.lojaJogos.observer;

import com.example.lojaJogos.model.Jogo;

// Utilizaão do Observer aqui
public interface Observador {
    String reagirNovoJogoAdicionado(Jogo jogo);
}
