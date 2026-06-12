package com.example.lojaJogos.controller;

import com.example.lojaJogos.gerenciador.OperacoesCrudRelacao;
import com.example.lojaJogos.model.SelecaoPartida;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/selecao-partida")
@Tag(name = "Associação Seleção-Partida", description = "Operações para gerenciar a relação que define quais seleções jogam em qual partida")
public class SelecaoPartidaController {

    private final OperacoesCrudRelacao<SelecaoPartida> gerenciadorSelecaoPartida;

    public SelecaoPartidaController(OperacoesCrudRelacao<SelecaoPartida> gerenciadorSelecaoPartida) {
        this.gerenciadorSelecaoPartida = gerenciadorSelecaoPartida;
    }

    @PostMapping
    @Operation(summary = "Vincular Seleção à Partida", description = "Insere um registro na tabela intermediária escalando uma seleção para um jogo específico.")
    public String vincular(@RequestParam Integer idSelecao, @RequestParam Integer idPartida) {
        gerenciadorSelecaoPartida.vincular(idSelecao, idPartida);
        return "Seleção vinculada à partida com sucesso!";
    }

    @GetMapping
    @Operation(summary = "Listar Vínculos Existentes", description = "Exibe todos os pares relacionais cadastrados (Quem joga contra quem e onde).")
    public List<SelecaoPartida> listarTodos() {
        return gerenciadorSelecaoPartida.listarTodos();
    }

    @DeleteMapping
    @Operation(summary = "Desvincular Seleção da Partida", description = "Remove a linha de relação, tirando a seleção da partida agendada.")
    public String desvincular(@RequestParam Integer idSelecao, @RequestParam Integer idPartida) {
        gerenciadorSelecaoPartida.desvincular(idSelecao, idPartida);
        return "Vínculo removido com sucesso!";
    }
}