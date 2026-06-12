package com.example.lojaJogos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Jogador {
    private Integer idJogador;
    private String nome;
    private Integer numeroCamisa;
    private String posicao;
    private Integer idade;
    private Integer idSelecao;
}
