# Documentação: API REST - Loja de Jogos

- Disciplina: Técnicas Avançadas de Programação
- Professor: Thiago Souza
- Aluno: Rodrigo de Sousa Ferrett

## 1. Visão Geral e Especificações do Sistema

A **API Loja de Jogos** é um sistema back-end construído para gerenciar o catálogo de uma loja digital de videogames. O projeto adota uma arquitetura limpa com separação rigorosa de responsabilidades (Controllers para rotas HTTP, Gerenciadores para regras de negócio e acesso a dados, e Models para mapeamento).

**Stack Tecnológico:**

* **Linguagem:** Java (versão 21 - Suporte de Longo Prazo)
* **Framework Web:** Spring Boot 3+ (Spring WebMVC)
* **Banco de Dados:** MySQL (Hospedado em nuvem via Clever Cloud)
* **Acesso a Dados:** JDBC Puro (Sem uso de ORM como Hibernate, visando controle total e performance nas queries SQL)
* **Ferramentas e Bibliotecas:** **Lombok:** Redução de código boilerplate (Getters, Setters, Construtores).
* **Swagger (OpenAPI 3):** Interface gráfica interativa para documentação e teste dos endpoints.


* **Deploy:** Render (Hospedagem em Nuvem da API).
  * **Link do projeto em nuvem:** https://api-loja-jogos.onrender.com/swagger-ui/index.html
---

## 2. Estrutura do Banco de Dados (MySQL)

O banco de dados relacional foi modelado para garantir a integridade e normalização dos dados.

### Relações (Modelagem de Entidade-Relacionamento)

1. **Editora (1) para (N) Jogo:** Uma editora pode publicar vários jogos, mas um jogo possui apenas uma editora principal.
2. **Plataforma (N) para (M) Jogo:** Um jogo pode ser lançado para várias plataformas (PC, PS5, Xbox), e uma plataforma possui vários jogos. Isso gera a tabela associativa `Jogo_Plataforma`.

### Códigos SQL (DDL - Data Definition Language)

Scripts utilizados para a criação e estruturação física do banco de dados na nuvem:

```sql
-- 1. Tabela Editora 
CREATE TABLE Editora (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    ano_fundacao INT,
    pais_origem VARCHAR(50)
);

-- 2. Tabela Plataforma
CREATE TABLE Plataforma (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    fabricante VARCHAR(50)
);

-- 3. Tabela Jogo (Depende de Editora - Relação 1:N)
CREATE TABLE Jogo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    data_lancamento DATE,
    editora_id INT NOT NULL,
    CONSTRAINT fk_editora FOREIGN KEY (editora_id) REFERENCES Editora(id) ON DELETE RESTRICT
);

-- 4. Tabela Associativa (Relação N:M entre Jogo e Plataforma)
CREATE TABLE Jogo_Plataforma (
    jogo_id INT NOT NULL,
    plataforma_id INT NOT NULL,
    PRIMARY KEY (jogo_id, plataforma_id),
    CONSTRAINT fk_jogo FOREIGN KEY (jogo_id) REFERENCES Jogo(id) ON DELETE CASCADE,
    CONSTRAINT fk_plataforma FOREIGN KEY (plataforma_id) REFERENCES Plataforma(id) ON DELETE CASCADE
);

```

---

## 3. Design Patterns Implementados 

### 3.1. Singleton (Padrão Criacional)

* **Objetivo:** Garantir que uma classe tenha apenas uma única instância rodando na memória durante todo o ciclo de vida da aplicação e fornecer um ponto global de acesso a ela.
* **Onde foi aplicado:** Na classe `ConexaoBanco` (pacote `conexao`) para garantir a inicialização do driver do MySQL apenas uma vez, e todos os gerenciadores solicitam a conexão a essa mesma instância.

### 3.2. Observer (Padrão Comportamental)

* **Objetivo:** Definir uma dependência "um-para-muitos" entre objetos, de modo que, quando um objeto muda de estado, todos os seus dependentes são notificados e atualizados automaticamente.
* **Onde foi aplicado:** Na inserção de novos jogos no catálogo (Classe `GerenciadorJogo` e pacote `observer`) para sempre que um novo jogo é salvo com sucesso no banco de dados, o Gerenciador notifica todos os observadores cadastrados.
