package com.example.lojaJogos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Selecao {
    private Integer idSelecao;
    private String nomePais;
    private String tecnico;
    private Integer rankingFifa;
}