package com.example.lojaJogos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Jogo {
    private Integer id;
    private String titulo;
    private BigDecimal preco;
    private LocalDate dataLancamento;
    private Integer editoraId; // N:1 com Editora
}
