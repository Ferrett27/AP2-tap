package com.example.lojaJogos.controller;

import com.example.lojaJogos.model.Selecao;
import com.example.lojaJogos.gerenciador.OperacoesCrud;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/selecao")
@Tag(name = "Gerenciamento de Seleções", description = "Operações CRUD para as seleções da copa")
public class SelecaoController {

    private final OperacoesCrud<Selecao, Void> gerenciadorSelecao;

    public SelecaoController(OperacoesCrud<Selecao, Void> gerenciadorSelecao) {
        this.gerenciadorSelecao = gerenciadorSelecao;
    }

    @PostMapping
    @Operation(summary = "Inserir Seleção", description = "Adiciona uma nova seleção à base de dados.")
    public String inserir(
            @RequestParam String nomePais,
            @RequestParam String tecnico,
            @RequestParam Integer rankingFifa) {

        Selecao novaSelecao = Selecao.builder()
                .nomePais(nomePais)
                .tecnico(tecnico)
                .rankingFifa(rankingFifa)
                .build();

        gerenciadorSelecao.inserir(novaSelecao);
        return "Seleção cadastrada com sucesso!";
    }

    @GetMapping
    @Operation(summary = "Listar Seleções", description = "Exibe todas as seleções cadastradas.")
    public List<Selecao> listar() {
        return gerenciadorSelecao.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Seleção por ID", description = "Retorna uma única seleção através do seu identificador.")
    public Selecao buscar(@PathVariable Integer id) {
        return gerenciadorSelecao.buscarPorId(id);
    }

    @PutMapping
    @Operation(summary = "Editar Seleção", description = "Modifica os dados de uma seleção existente.")
    public String editar(
            @RequestParam Integer id,
            @RequestParam String nomePais,
            @RequestParam String tecnico,
            @RequestParam Integer rankingFifa) {

        Selecao selecaoModificada = Selecao.builder()
                .idSelecao(id)
                .nomePais(nomePais)
                .tecnico(tecnico)
                .rankingFifa(rankingFifa)
                .build();

        gerenciadorSelecao.editar(selecaoModificada);
        return "Seleção atualizada com sucesso!";
    }

    @DeleteMapping
    @Operation(summary = "Deletar Seleção", description = "Remove uma seleção da base pelo seu ID.")
    public void deletar(@RequestParam Integer id) {
        gerenciadorSelecao.deletar(id);
    }
}