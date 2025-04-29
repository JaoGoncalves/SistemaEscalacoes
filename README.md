# Sistema de Escalação de Jogadores de Futebol ⚽

Este projeto Java é um sistema de escalação de jogadores de futebol que permite ao usuário montar a escalação de um time, adicionar jogadores por posição, listar os escalados, e remover jogadores por número. O sistema foi desenvolvido aplicando os principais conceitos de Programação Orientada a Objetos.

---

## 📌 Funcionalidades

- Inserir nome do time no início da execução.
- Escalar jogadores em posições específicas (Goleiro, Zagueiro, Atacante).
- Listar jogadores escalados.
- Remover jogadores por número.
- Validação de número do jogador (apenas entre 1 e 99).
- Tratamento de exceções.

---

## 🚀 Como Executar



## 🧱 Estrutura de Diretórios


├── src/classes
├── Jogador.java               # Classe abstrata base
├── Escalacao.java             # Interface comum com métodos de simulação
├── ExceptionEscalacao.java    # Classe que contém a Exceção Personalizada
├── Goleiro.java               # Subclasse que Herda da Classe Abstrata (Jogador)
├── Lateral.java               # Subclasse que Herda da Classe Abstrata (Jogador)
├── MeioCampo.java             # Subclasse que Herda da Classe Abstrata (Jogador)
├── Atacante.java              # Subclasse que Herda da Classe Abstrata (Jogador)
├── NumeroInvalidoException.java # Exceção Personalida
└── EscalarTime.java # Classe principal (main)




