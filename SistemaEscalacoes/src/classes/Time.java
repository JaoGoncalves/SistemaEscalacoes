package classes;

import java.util.ArrayList;

public class Time implements Escalacao {
    private String nomeEquipe;
    private ArrayList<Jogador> escalacao = new ArrayList<>();

    public Time() {
        this.nomeEquipe = getNomeEquipe();
    }

    public void setNomeEquipe(String nomeEquipe) {
        this.nomeEquipe = nomeEquipe;
    }

    public String getNomeEquipe() {
        return nomeEquipe;
    }

    @Override
    public void escalar(Jogador jogador) throws ExceptionEscalacao {
        if (escalacao.size() > 11) {
            throw new ExceptionEscalacao("Não é permitido escalar mais de 11 jogadores.");
        }
        escalacao.add(jogador);
        System.out.println("Jogador escalado:" + jogador.getNome() + ",Pos:" + jogador.getPosicao() + ",N:"
                + jogador.getNumero());

    }

    @Override
    public void removerJogador(int numero) throws ExceptionEscalacao {
        if (numero > 0 && numero < 100) {
            escalacao.remove(numero);
        } else {
            throw new ExceptionEscalacao("Jogador não encontrado.");
        }
        System.out.println("Jogador número: " + numero + " removido");

    }

    @Override
    public void imprimeEscalacao() {
        System.out.println("A escalação " + getNomeEquipe() + ", vem com os seguintes jogadores: ");
        for (Jogador jogador : escalacao) {
            System.out.println(jogador.getNome() + ",N:" + jogador.getNumero());
        }
    }

    @Override
    public String toString() {
        return "Time [nomeEquipe=" + nomeEquipe + "]";
    }

}
