package com.example.lojaJogos.controller;

import com.example.lojaJogos.gerenciador.GerenciadorJogo;
import com.example.lojaJogos.model.Jogo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/jogo")
@Tag(name = "Gerenciamento de Jogos", description = "Operações CRUD para o catálogo de jogos da loja")
public class JogoController {

    private final GerenciadorJogo gerenciadorJogo;
    private final DateTimeFormatter formatoDataBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public JogoController(GerenciadorJogo gerenciadorJogo) {
        this.gerenciadorJogo = gerenciadorJogo;
    }

    @PostMapping
    @Operation(summary = "Inserir Jogo", description = "Adiciona um jogo ao catálogo e dispara um Observer.")
    public String inserir(
            @RequestParam String titulo,
            @RequestParam BigDecimal preco,
            @Parameter(description = "Formato: DD/MM/AAAA") @RequestParam String dataLancamento,
            @RequestParam Integer editoraId) {

        Jogo novoJogo = Jogo.builder()
                .titulo(titulo)
                .preco(preco)
                .dataLancamento(LocalDate.parse(dataLancamento, formatoDataBR))
                .editoraId(editoraId)
                .build();

        List<String> logsDosObservadores = gerenciadorJogo.insercao(novoJogo);

        StringBuilder respostaSwagger = new StringBuilder();
        respostaSwagger.append("Jogo cadastrado com sucesso no banco de dados!\n\n");
        respostaSwagger.append("RESULTADO DO PADRÃO OBSERVER:\n");

        for (String log : logsDosObservadores) {
            respostaSwagger.append(log).append("\n");
        }

        return respostaSwagger.toString();
    }

    @GetMapping
    @Operation(summary = "Listar Catálogo de Jogos", description = "Retorna a listagem de todos os jogos.")
    public List<Jogo> listar() {
        return gerenciadorJogo.resultado();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Jogo por ID", description = "Encontra detalhes de um jogo na base por id.")
    public Jogo buscar(@PathVariable Integer id) {
        return gerenciadorJogo.buscarPorId(id);
    }

    @PutMapping
    @Operation(summary = "Editar dados do Jogo", description = "Modifica título, preço, data ou editora vinculada.")
    public String editar(@RequestParam Integer id, @RequestParam String titulo, @RequestParam BigDecimal preco, @RequestParam String dataLancamento, @RequestParam Integer editoraId) {
        Jogo jogoModificado = Jogo.builder()
                .id(id)
                .titulo(titulo)
                .preco(preco)
                .dataLancamento(LocalDate.parse(dataLancamento, formatoDataBR))
                .editoraId(editoraId)
                .build();
        gerenciadorJogo.editar(jogoModificado);
        return "Jogo atualizado com sucesso!";
    }

    @DeleteMapping
    @Operation(summary = "Deletar Jogo", description = "Remove um jogo.")
    public void deletar(@RequestParam Integer id) {
        gerenciadorJogo.deletar(id);
    }
}