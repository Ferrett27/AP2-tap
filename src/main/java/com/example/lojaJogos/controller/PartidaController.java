package com.example.lojaJogos.controller;

import com.example.lojaJogos.gerenciador.OperacoesCrud;
import com.example.lojaJogos.gerenciador.OperacoesPartida;
import com.example.lojaJogos.model.Partida;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/partida")
@Tag(name = "Gerenciamento de Partidas", description = "Operações CRUD para as partidas da copa")
public class PartidaController {

    private final OperacoesPartida gerenciadorPartida;
    private final DateTimeFormatter formatoDataBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PartidaController(OperacoesPartida gerenciadorPartida) {
        this.gerenciadorPartida = gerenciadorPartida;
    }

    @PostMapping
    @Operation(summary = "Inserir Partida", description = "Adiciona uma nova partida à tabela de jogos.")
    public String inserir(
            @Parameter(description = "Formato: DD/MM/AAAA") @RequestParam String dataPartida,
            @RequestParam String estadio,
            @RequestParam String faseCompeticao,
            @RequestParam String placar) {

        Partida novaPartida = Partida.builder()
                .dataPartida(LocalDate.parse(dataPartida, formatoDataBR))
                .estadio(estadio)
                .faseCompeticao(faseCompeticao)
                .placar(placar)
                .build();

        gerenciadorPartida.inserir(novaPartida);
        return "Partida cadastrada com sucesso!";
    }

    @GetMapping
    @Operation(summary = "Listar Partidas", description = "Retorna todas as partidas registradas.")
    public List<Partida> listar() {
        return gerenciadorPartida.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Partida por ID", description = "Retorna uma partida específica pelo seu identificador.")
    public Partida buscar(@PathVariable Integer id) {
        return gerenciadorPartida.buscarPorId(id);
    }

    @PutMapping
    @Operation(summary = "Editar Partida", description = "Atualiza os dados de uma partida (ex: alterar o placar após o jogo).")
    public String editar(
            @RequestParam Integer id,
            @Parameter(description = "Formato: DD/MM/AAAA") @RequestParam String dataPartida,
            @RequestParam String estadio,
            @RequestParam String faseCompeticao,
            @RequestParam String placar) {

        Partida partidaModificada = Partida.builder()
                .idPartida(id)
                .dataPartida(LocalDate.parse(dataPartida, formatoDataBR))
                .estadio(estadio)
                .faseCompeticao(faseCompeticao)
                .placar(placar)
                .build();

        gerenciadorPartida.editar(partidaModificada);
        return "Partida atualizada com sucesso!";
    }

    @DeleteMapping
    @Operation(summary = "Deletar Partida", description = "Remove uma partida do sistema.")
    public void deletar(@RequestParam Integer id) {
        gerenciadorPartida.deletar(id);
    }

    @GetMapping("/selecao/{idSelecao}")
    @Operation(summary = "Partidas de uma seleção", description = "Retorna o histórico das partidas de uma seleção específica.")
    public List<Partida> buscarPartidasDaSelecao(@PathVariable Integer idSelecao) {
        return gerenciadorPartida.buscarPartidasDaSelecao(idSelecao);
    }
}