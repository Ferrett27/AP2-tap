package com.example.lojaJogos.observer;

import com.example.lojaJogos.model.Jogo;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotificadorSistema implements Observador{
    @Override
    public String reagirNovoJogoAdicionado(Jogo jogo) {
        return "Sucesso: Jogo '" + jogo.getTitulo() + "' (ID: " + jogo.getId() + ") inserido às " + LocalDateTime.now();
    }
}
