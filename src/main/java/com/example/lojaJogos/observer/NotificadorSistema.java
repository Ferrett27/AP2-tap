package com.example.lojaJogos.observer;

import com.example.lojaJogos.model.Jogo;
import java.time.LocalDateTime;

public class NotificadorSistema implements Observador{
    @Override
    public String reagirNovoJogoAdicionado(Jogo jogo) {
        return "Sucesso: Jogo '" + jogo.getTitulo() + "' (ID: " + jogo.getId() + ") inserido às " + LocalDateTime.now();
    }
}
