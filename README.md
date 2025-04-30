# Sistema de Escalação de Jogadores de Futebol ⚽

Este projeto Java é um sistema de escalação de jogadores de futebol que permite ao usuário montar a escalação de um time, adicionar jogadores por posição, listar os escalados, e remover jogadores por número. O sistema foi desenvolvido aplicando os principais conceitos de **Programação Orientada a Objetos**, como **Herança**, **Polimorfismo**, **interfaces**, **Collections(`ArrayList`)** e **Tratamento de Exceções**.

---

## 📌 Funcionalidades

- Inserir nome do time no início da execução.
- Escalar jogadores em posições específicas (Goleiro, Zagueiro, Lateral, Meio Campo, Atacante).
- Remover jogadores por número.
- Imprimir lista com jogadores escalados
- Validação de número do jogador (apenas entre 1 e 99).
- Tratamento de exceções.

---

## 🚀 Como Executar
1- Copie os Arquivos ou clone o repositório na sua IDE\
2- Abra na sua IDE\
3- Compile e execute a classe `EscalarTime.java`  
4- Insira os dados solicitados  
5- Lista dos dados de entrada do usuário

---

## 🧱 Estrutura de Classes

```

├── src/classes
│   ├── Jogador.java                   # Classe Abstrata base
│   ├── Goleiro.java                   # SubClasse
│   ├── Zagueiro.java                  # SubClasse 
│   ├── Lateral.java                   # SubClasse
│   ├── MeioCampo.java                 # SubClasse
│   ├── Atacante.java                  # SubClasse
│   ├── Time.java                      # Classe para implementar métodos da interface Escalação
│   ├── Escalação.java                 # Interface com métodos
│   ├── EscalacaoException.java        # Exceção Personalizada
│   ├── NumeroInvalidoException.java   # Exceção personalizada
│   └── EscalarTime.java               # Classe principal (main)
└── README.md
```

---

## 📊 Diagrama UML

 (![image](https://github.com/user-attachments/assets/eb9a2f83-0cbc-4a3c-b493-9e9f2c267fdd)




---

## ✅ Conceitos Aplicados

- ✅ Herança (Implementado na classe `Jogador`)
- ✅ Polimorfismo (implementação diversa em cada classe herdada da classe Jogador)
- ✅ Interface (`Escalacao`)
- ✅ Classe abstrata (`Jogador`)
- ✅ Collections (`ArrayList`)
- ✅ Métodos adiciona e remove jogadores
- ✅ Interface gráfica (`JOptionPane`)
- ✅ Exceptions Personalizadas (`NumeroInvalidoException`, `ExceptionEscalacao`)

---
## ⚒️Ferramentas utilizadas

  - Java SE 21
  - VSCode
  - Astah UML
  - Swing `JOoptionPane`

---




