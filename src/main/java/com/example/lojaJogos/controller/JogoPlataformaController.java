package com.example.lojaJogos.controller;

import com.example.lojaJogos.gerenciador.GerenciadorJogoPlataforma;
import com.example.lojaJogos.model.JogoPlataforma;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vinculos")
@Tag(name = "Associação Jogo-Plataforma", description = "Operações para gerenciar a tabela associativa de Jogo e Plataforma")
public class JogoPlataformaController {

    private final GerenciadorJogoPlataforma gerenciadorJogoPlataforma;

    public JogoPlataformaController(GerenciadorJogoPlataforma gerenciadorJogoPlataforma) {
        this.gerenciadorJogoPlataforma = gerenciadorJogoPlataforma;
    }

    @PostMapping
    @Operation(summary = "Vincular Jogo a Plataforma", description = "Insere um registro na tabela intermediária associando um jogo a uma plataforma.")
    public String vincular(@RequestParam Integer jogoId, @RequestParam Integer plataformaId) {
        gerenciadorJogoPlataforma.vincular(jogoId, plataformaId);
        return "Vínculo mapeado com sucesso!";
    }

    @GetMapping
    @Operation(summary = "Listar Vínculos Existentes", description = "Exibe todos os pares relacionais cadastrados.")
    public List<JogoPlataforma> listarTodos() {
        return gerenciadorJogoPlataforma.listarTodos();
    }

    @DeleteMapping
    @Operation(summary = "Desvincular Jogo e Plataforma", description = "Deleta a linha de relação entre o jogo e a plataforma selecionada.")
    public String desvincular(@RequestParam Integer jogoId, @RequestParam Integer plataformaId) {
        gerenciadorJogoPlataforma.desvincular(jogoId, plataformaId);
        return "Vínculo removido com sucesso!";
    }
}