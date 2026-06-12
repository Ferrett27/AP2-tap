package com.example.lojaJogos.observer;

import com.example.lojaJogos.model.Jogador;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotificadorSistema implements Observador{
    @Override
    public String reagirNovoJogadorAdicionado(Jogador jogador) {
        return "Sucesso: Jogador '" + jogador.getNome() + "' (ID: " + jogador.getIdJogador() + ") " + "na posição " + jogador.getPosicao() + " Camisa " + jogador.getNumeroCamisa() + " inserido às " + LocalDateTime.now();
    }
}
