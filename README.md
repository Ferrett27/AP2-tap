# Documentação: API REST - Sistema de Competição (Seleções e Partidas)

* **Disciplina:** Técnicas Avançadas de Programação
* **Professor:** Thiago Souza
* **Aluno:** Rodrigo de Sousa Ferrett

---

## 1. Visão Geral e Especificações do Sistema

A **API de Competição** é um sistema back-end construído para gerenciar informações de seleções, seus respectivos jogadores e o histórico de partidas de um torneio (como a Copa do Mundo). O projeto adota uma arquitetura limpa com separação rigorosa de responsabilidades (Controllers para rotas HTTP, Gerenciadores para regras de negócio e acesso a dados, e Models para mapeamento).

**Stack Tecnológico:**

* **Linguagem:** Java (versão 21 - Suporte de Longo Prazo)
* **Framework Web:** Spring Boot 3+ (Spring WebMVC)
* **Banco de Dados:** MySQL (Hospedado em nuvem via Clever Cloud)
* **Acesso a Dados:** JDBC Puro (Sem uso de ORM como Hibernate, visando controle total e performance nas queries SQL)
* **Ferramentas e Bibliotecas:**
* **Lombok:** Redução de código boilerplate (Getters, Setters, Construtores).
* **Swagger (OpenAPI 3):** Interface gráfica interativa para documentação e teste dos endpoints.


* **Deploy:** Render (Hospedagem em Nuvem da API).
  * Link da nuvem: https://ap2-tap.onrender.com/swagger-ui/index.html#/

---

## 2. Estrutura do Banco de Dados (MySQL)

O banco de dados relacional foi modelado para garantir a integridade e normalização dos dados, refletindo as regras de negócio do minimundo.

### Relações (Modelagem de Entidade-Relacionamento)

1. **Seleção (1) para (N) Jogador:** Uma seleção possui vários jogadores, mas um jogador atua por apenas uma seleção.
2. **Seleção (N) para (M) Partida:** Uma partida possui várias seleções participantes (duas), e uma seleção participa de várias partidas ao longo do torneio. Isso gera a tabela associativa `Selecao_Partida`.

### Códigos SQL (DDL - Data Definition Language)

Scripts utilizados para a criação e estruturação física do banco de dados na nuvem:

```sql
-- 1. Tabela Selecao
CREATE TABLE Selecao (
    id_selecao INT AUTO_INCREMENT PRIMARY KEY,
    nome_pais VARCHAR(100) NOT NULL UNIQUE,
    tecnico VARCHAR(100) NOT NULL,
    ranking_fifa INT NOT NULL
);

-- 2. Tabela Jogador (Depende de Selecao - Relação 1:N)
CREATE TABLE Jogador (
    id_jogador INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    numero_camisa INT NOT NULL,
    posicao VARCHAR(50) NOT NULL,
    idade INT NOT NULL,
    id_selecao INT NOT NULL,
    CONSTRAINT fk_selecao_jogador FOREIGN KEY (id_selecao) REFERENCES Selecao(id_selecao) ON DELETE CASCADE
);

-- 3. Tabela Partida
CREATE TABLE Partida (
    id_partida INT AUTO_INCREMENT PRIMARY KEY,
    data_partida DATE NOT NULL,
    estadio VARCHAR(150) NOT NULL,
    fase_competicao VARCHAR(50) NOT NULL,
    placar VARCHAR(20) NOT NULL
);

-- 4. Tabela Associativa (Relação N:M entre Selecao e Partida)
CREATE TABLE Selecao_Partida (
    id_selecao INT NOT NULL,
    id_partida INT NOT NULL,
    PRIMARY KEY (id_selecao, id_partida),
    CONSTRAINT fk_sp_selecao FOREIGN KEY (id_selecao) REFERENCES Selecao(id_selecao) ON DELETE CASCADE,
    CONSTRAINT fk_sp_partida FOREIGN KEY (id_partida) REFERENCES Partida(id_partida) ON DELETE CASCADE
);

```

---

## 3. Design Patterns Implementados

### 3.1. Singleton (Padrão Criacional)

* **Objetivo:** Garantir que uma classe tenha apenas uma única instância rodando na memória durante todo o ciclo de vida da aplicação e fornecer um ponto global de acesso a ela.
* **Onde foi aplicado:** Na classe `ConexaoBanco` (pacote `conexao`) para garantir a inicialização do driver do MySQL apenas uma vez, de forma que todos os gerenciadores solicitem a conexão a essa mesma instância.

### 3.2. Observer (Padrão Comportamental)

* **Objetivo:** Definir uma dependência "um-para-muitos" entre objetos, de modo que, quando um objeto muda de estado, todos os seus dependentes são notificados e atualizados automaticamente.
* **Onde foi aplicado:** Na inserção de novos registros (Classe `GerenciadorPartida` e pacote `observer`). O padrão foi intencionalmente implementado de volta no Gerenciador para evitar que o Controller assuma funções além do seu escopo HTTP. Faz mais sentido o observador estar centralizado no Gerenciador, pois é ele quem efetivamente cria a nova partida no banco de dados e, logo em seguida, avisa aos dependentes que um jogo novo foi criado para que as notificações ocorram adequadamente.
