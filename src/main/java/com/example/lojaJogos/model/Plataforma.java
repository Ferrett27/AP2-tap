package com.example.lojaJogos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plataforma {
    private Integer id;
    private String nome;
    private String fabricante;
}
