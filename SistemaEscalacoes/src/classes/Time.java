package classes;

import java.util.ArrayList;
import java.util.List;

public class Time {
    private int id;
    private String nome;
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
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public List<Jogador> getEscalacao() { return escalacao; }
    public void setEscalacao(List<Jogador> escalacao) { this.escalacao = escalacao; }

    public void adicionarJogador(Jogador jogador) {
        this.escalacao.add(jogador);
    }

    public void removerJogador(Jogador jogador) {
        this.escalacao.remove(jogador);
    }

    @Override
    public String toString() {
        return nome;
    }
}