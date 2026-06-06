package com.example.lojaJogos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Editora {
    private Integer id;
    private String nome;
    private Integer anoFundacao;
    private String paisOrigem;
}
