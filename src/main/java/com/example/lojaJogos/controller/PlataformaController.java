package com.example.lojaJogos.controller;

import com.example.lojaJogos.gerenciador.OperacoesCrud;
import com.example.lojaJogos.model.Plataforma;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plataforma")
@Tag(name = "Gerenciamento de Plataformas", description = "Operações para as plataformas disponíveis (PC, Consoles, Mobile)")
public class PlataformaController {

    private final OperacoesCrud<Plataforma, Void> gerenciadorPlataforma;

    public PlataformaController(OperacoesCrud<Plataforma, Void> gerenciadorPlataforma) {
        this.gerenciadorPlataforma = gerenciadorPlataforma;
    }

    @PostMapping
    @Operation(summary = "Inserir Plataforma", description = "Adiciona uma plataforma.")
    public String inserir(@RequestParam String nome, @RequestParam String fabricante) {
        Plataforma novaPlataforma = Plataforma.builder()
                .nome(nome)
                .fabricante(fabricante)
                .build();
        gerenciadorPlataforma.inserir(novaPlataforma);
        return "Plataforma cadastrada com sucesso!";
    }

    @GetMapping
    @Operation(summary = "Listar Plataformas", description = "Retorna todas as plataformas salvas.")
    public List<Plataforma> listar() {
        return gerenciadorPlataforma.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Plataforma por ID", description = "Retorna uma plataforma específica por id.")
    public Plataforma buscar(@PathVariable Integer id) {
        return gerenciadorPlataforma.buscarPorId(id);
    }

    @PutMapping
    @Operation(summary = "Editar Plataforma", description = "Atualiza o nome ou fabricante de uma plataforma.")
    public String editar(@RequestParam Integer id, @RequestParam String nome, @RequestParam String fabricante) {
        Plataforma plataformaModificada = Plataforma.builder()
                .id(id)
                .nome(nome)
                .fabricante(fabricante)
                .build();
        gerenciadorPlataforma.editar(plataformaModificada);
        return "Plataforma atualizada com sucesso!";
    }

    @DeleteMapping
    @Operation(summary = "Deletar Plataforma", description = "Remove uma plataforma.")
    public void deletar(@RequestParam Integer id) {
        gerenciadorPlataforma.deletar(id);
    }
}