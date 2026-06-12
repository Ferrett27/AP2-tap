package com.example.lojaJogos.controller;

import com.example.lojaJogos.gerenciador.OperacoesCrud;
import com.example.lojaJogos.gerenciador.OperacoesJogador;
import com.example.lojaJogos.model.Jogador;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jogador")
@Tag(name = "Gerenciamento de Jogadores", description = "Operações CRUD para as operações de Jogadores")
public class JogadorController {

    private final OperacoesJogador gerenciadorJogador;

    public JogadorController(OperacoesJogador gerenciadorJogador) {
        this.gerenciadorJogador = gerenciadorJogador;
    }

    @PostMapping
    @Operation(summary = "Inserir Jogador", description = "Adiciona um jogador à base e dispara um Observer.")
    public String inserir(
            @RequestParam String nome,
            @RequestParam Integer numeroCamisa,
            @RequestParam String posicao,
            @RequestParam Integer idade,
            @RequestParam Integer idSelecao) {

        Jogador novoJogador = Jogador.builder()
                .nome(nome)
                .numeroCamisa(numeroCamisa)
                .posicao(posicao)
                .idade(idade)
                .idSelecao(idSelecao)
                .build();

        List<String> logsDosObservadores = gerenciadorJogador.inserir(novoJogador);

        StringBuilder respostaSwagger = new StringBuilder();
        respostaSwagger.append("Jogador cadastrado com sucesso no banco de dados!\n\n");
        respostaSwagger.append("RESULTADO DO PADRÃO OBSERVER:\n");

        for (String log : logsDosObservadores) {
            respostaSwagger.append(log).append("\n");
        }

        return respostaSwagger.toString();
    }

    @GetMapping
    @Operation(summary = "Listar Elenco de Jogadores", description = "Retorna a listagem de todos os jogadores.")
    public List<Jogador> listar() {
        return gerenciadorJogador.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Jogador por ID", description = "Encontra detalhes de um jogador na base pelo id.")
    public Jogador buscar(@PathVariable Integer id) {
        return gerenciadorJogador.buscarPorId(id);
    }

    @PutMapping
    @Operation(summary = "Editar dados do Jogador", description = "Modifica nome, camisa, posição, idade ou seleção vinculada.")
    public String editar(
            @RequestParam Integer id,
            @RequestParam String nome,
            @RequestParam Integer numeroCamisa,
            @RequestParam String posicao,
            @RequestParam Integer idade,
            @RequestParam Integer idSelecao) {

        Jogador jogadorModificado = Jogador.builder()
                .idJogador(id)
                .nome(nome)
                .numeroCamisa(numeroCamisa)
                .posicao(posicao)
                .idade(idade)
                .idSelecao(idSelecao)
                .build();

        gerenciadorJogador.editar(jogadorModificado);
        return "Jogador atualizado com sucesso!";
    }

    @DeleteMapping
    @Operation(summary = "Deletar Jogador", description = "Remove um jogador do banco de dados.")
    public void deletar(@RequestParam Integer id) {
        gerenciadorJogador.deletar(id);
    }

    @GetMapping("/selecao/{idSelecao}")
    @Operation(summary = "Elenco da Seleção", description = "Retorna todos os jogadores convocados por uma seleção específica.")
    public List<Jogador> buscarJogadoresDaSelecao(@PathVariable Integer idSelecao) {
        return gerenciadorJogador.buscarPorSelecao(idSelecao);
    }
}