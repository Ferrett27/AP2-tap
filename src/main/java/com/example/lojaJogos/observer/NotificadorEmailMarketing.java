package com.example.lojaJogos.observer;

import com.example.lojaJogos.model.Jogo;
import org.springframework.stereotype.Component;

@Component
public class NotificadorEmailMarketing implements Observador {
    @Override
    public String reagirNovoJogoAdicionado(Jogo jogo) {
        return "Notificação enviada: Novo jogo disponível! " + jogo.getTitulo() + " por apenas R$" + jogo.getPreco();
    }
}
