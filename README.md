# Sistema de Escalação de Jogadores de Futebol ⚽

Este projeto Java é um sistema de escalação de jogadores de futebol que permite ao usuário montar a escalação de um time, adicionar jogadores por posição, listar os escalados, e remover jogadores por número. O sistema foi desenvolvido aplicando os principais conceitos de Programação Orientada a Objetos, como Herança, Polimorfismo, interfaces, listas genéricas e tratamento de Exceções.

---

## 📌 Funcionalidades

- Inserir nome do time no início da execução.
- Escalar jogadores em posições específicas (Goleiro, Zagueiro, Lateral, Meio Campo, Atacante).
- Listar jogadores escalados.
- Remover jogadores por número.
- Validação de número do jogador (apenas entre 1 e 99).
- Tratamento de exceções.

---

## 🚀 Como Executar
1- Copie os Arquivos ou clone o repositório na sua IDE\
2- Abra na sua IDE\
3- Compile e execute a classe EscalarTime.java  
4- Insira os dados solicitados  
5- Lista dos dados de entrada do usuário


## 🧱 Estrutura de Classes

```
EscalacaoFutebol/
├── src/classes
│   ├── Jogador.java                   # Classe Abstrata base
│   ├── Goleiro.java                   # SubClasse
│   ├── Zagueiro.java                  # SubClasse 
|   ├── Lateral.java                   # SubClasse
|   ├── MeioCampo.java                 # SubClasse
│   ├── Atacante.java                  # SubClasse
│   ├── Time.java                      # Classe para implementar métodos da interface Escalação
│   ├── Escalação.java                 # Interface com métodos
│   ├── EscalacaoException.java        # Exceção Personalizada
│   ├── NumeroInvalidoException.java   # Exceção personalizada
│   └── EscalarTime.java               # Classe principal (main)
└── README.md

```

## 📊 Diagrama UML



## ✅ Conceitos Aplicados




