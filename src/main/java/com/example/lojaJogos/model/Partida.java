package com.example.lojaJogos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Partida {
    private Integer idPartida;
    private LocalDate dataPartida;
    private String estadio;
    private String faseCompeticao;
    private String placar;
}