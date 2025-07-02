
package model;

import java.util.ArrayList;
import java.util.List;

// 1. Adicione "implements Escalacao"
public class Time implements Escalacao {
    private int id;
    private String nome; // Renomeado de nomeEquipe para nome
    private List<Jogador> escalacao;

    public Time() {
        this.escalacao = new ArrayList<>();
    }

    public Time(String nome) {
        this.nome = nome;
        this.escalacao = new ArrayList<>();
    }

    public Time(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.escalacao = new ArrayList<>();
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    } // Renomeado de setNomeEquipe

    public List<Jogador> getEscalacao() {
        return escalacao;
    }

    public void setEscalacao(List<Jogador> escalacao) {
        this.escalacao = escalacao;
    }

    // 2. Renomeie o método e adicione @Override para implementar a interface
    @Override
    public void escalarJogador(Jogador jogador) throws ExceptionEscalacao {
        if (this.escalacao.size() >= 11 && !(this instanceof Time)) { // Adicionei uma verificação extra para o contexto
                                                                      // do time
            throw new ExceptionEscalacao("Um time pode ter apenas 11 jogadores escalados.");
        }
        for (Jogador j : escalacao) {
            if (j.getNumero() == jogador.getNumero()) {
                throw new ExceptionEscalacao("Já existe um jogador com o número " + jogador.getNumero());
            }
        }
        this.escalacao.add(jogador);
    }

    // 3. Renomeie o método e adicione @Override
    @Override
    public void removerJogador(int numero) throws ExceptionEscalacao {
        Jogador jogadorParaRemover = null;
        for (Jogador j : this.escalacao) {
            if (j.getNumero() == numero) {
                jogadorParaRemover = j;
                break;
            }
        }
        if (jogadorParaRemover != null) {
            this.escalacao.remove(jogadorParaRemover);
        } else {
            throw new ExceptionEscalacao("Jogador com número " + numero + " não encontrado na escalação.");
        }
    }

    // 4. Implemente os outros métodos da interface
    @Override
    public List<Jogador> listarEscalacao() {
        return this.escalacao;
    }

    @Override
    public void validarEscalacao() throws ExceptionEscalacao {
        if (this.escalacao.size() != 11) {
            throw new ExceptionEscalacao("A escalação final deve ter exatamente 11 jogadores.");
        }
        // Outras validações (ex: 1 goleiro, etc.) podem ser adicionadas aqui
    }

    @Override
    public String toString() {
        return nome;
    }

    // Método que estava faltando no seu DAO
    public void imprimeEscalacao() {
        // Implemente a lógica de impressão se necessário, ou remova a chamada em
        // EscalarTime.java
        System.out.println("Escalação do time: " + this.nome);
        for (Jogador j : this.escalacao) {
            System.out.println(j.toString());
        }
    }
}