package com.example.lojaJogos.observer;

import com.example.lojaJogos.gerenciador.OperacoesCrud;
import com.example.lojaJogos.model.Jogador;
import com.example.lojaJogos.model.Selecao;
import org.springframework.stereotype.Component;

@Component
public class NotificadorEmailMarketing implements Observador {

    private final OperacoesCrud<Selecao, Void> gerenciadorSelecao;

    public NotificadorEmailMarketing(OperacoesCrud<Selecao, Void> gerenciadorSelecao) {
        this.gerenciadorSelecao = gerenciadorSelecao;
    }

    @Override
    public String reagirNovoJogadorAdicionado(Jogador jogador) {

        Selecao selecao = gerenciadorSelecao.buscarPorId(jogador.getIdSelecao());

        String nomeDaSelecao = (selecao != null) ? selecao.getNomePais() : "Seleção Desconhecida";

        return "Notificação enviada: Novo Jogador convocado! " + jogador.getNome() +
                " jogará pela seleção do(a) " + nomeDaSelecao + "na posição " + jogador.getPosicao() + "!";
    }
}