package com.example.lojaJogos.controller;

import com.example.lojaJogos.gerenciador.GerenciadorEditora;
import com.example.lojaJogos.model.Editora;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/editora")
@Tag(name = "Gerenciamento de Editoras", description = "Operações CRUD para as distribuidoras de jogos")
public class EditoraController {

    private final GerenciadorEditora gerenciadorEditora;

    public EditoraController(GerenciadorEditora gerenciadorEditora) {
        this.gerenciadorEditora = gerenciadorEditora;
    }

    @PostMapping
    @Operation(summary = "Inserir Editora", description = "Adiciona uma nova editora à base de dados.")
    public String inserir(@RequestParam String nome, @RequestParam Integer anoFundacao, @RequestParam String paisOrigem) {
        Editora novaEditora = Editora.builder()
                .nome(nome)
                .anoFundacao(anoFundacao)
                .paisOrigem(paisOrigem)
                .build();
        gerenciadorEditora.inserir(novaEditora);
        return "Editora cadastrada com sucesso!";
    }

    @GetMapping
    @Operation(summary = "Listar Editoras", description = "Exibe todas as editoras salvas.")
    public List<Editora> listar() {
        return gerenciadorEditora.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Editora por ID", description = "Retorna uma única editora através do seu identificador.")
    public Editora buscar(@PathVariable Integer id) {
        return gerenciadorEditora.buscarPorId(id);
    }

    @PutMapping
    @Operation(summary = "Editar Editora", description = "Modifica os dados de uma editora existente.")
    public String editar(@RequestParam Integer id, @RequestParam String nome, @RequestParam Integer anoFundacao, @RequestParam String paisOrigem) {
        Editora editoraModificada = Editora.builder()
                .id(id)
                .nome(nome)
                .anoFundacao(anoFundacao)
                .paisOrigem(paisOrigem)
                .build();
        gerenciadorEditora.editar(editoraModificada);
        return "Editora atualizada com sucesso!";
    }

    @DeleteMapping
    @Operation(summary = "Deletar Editora", description = "Remove uma editora da base pelo seu ID.")
    public void deletar(@RequestParam Integer id) {
        gerenciadorEditora.deletar(id);
    }
}