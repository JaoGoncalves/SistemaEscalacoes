package classes;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Time implements Escalacao {
    // Atributos
    private String nomeEquipe;
    // Criação do ArrayList, já instanciado
    private ArrayList<Jogador> escalacao = new ArrayList<>();

    // construtor
    public Time() {
        this.nomeEquipe = getNomeEquipe();
    }

    // set para adicionar o nome do time
    public void setNomeEquipe(String nomeEquipe) {
        this.nomeEquipe = nomeEquipe;
    }

    // get, irá retornar o nome da equipe
    public String getNomeEquipe() {
        return nomeEquipe;
    }

    @Override
    // método sobrescrito, passando um paramêtro da classe Jogador
    public void escalarJogador(Jogador jogador) throws ExceptionEscalacao { // chamada da exceção personalizada

        escalacao.add(jogador); // adiciona o jogador no ArrayList

        // imprime o nome do jogador, sua posição e número
        System.out.println("Jogador escalado:" + jogador.getNome() + ",Pos:" + jogador.getPosicao() + ",N:"
                + jogador.getNumero());

    }

    @Override
    // metodo sobrescrito. recebendo um paramêtro do tipo int
    public void removerJogador(int numero) throws ExceptionEscalacao { // chamada da exceção personalizada
        if (numero > 0 && numero < 100) {
            escalacao.removeIf(jogador -> jogador.getNumero() == numero); // remove o elemento digitado pelo usuário
            System.out.println("Jogador número " + numero + " removido"); // mostra a mensagem do nº jogador removido
        } else { // caso nao encontre o nº do jogador, o sistema mostrá a mensagem abaixo
            throw new ExceptionEscalacao("Jogador não encontrado. Remoção não realizada"); // // lança uma exceção se
                                                                                           // não encontrar o jogador
        }

    }

    @Override
    // método sobrescrito da interface Escalacao
    public void imprimeEscalacao() { // irá imprimir a escalação

        if (escalacao.size() == 11) {
            StringBuilder mensagem = new StringBuilder();
            mensagem.append("A escalação do time ").append(getNomeEquipe())
                    .append(" vem com os seguintes jogadores:\n\n");

            for (Jogador jogador : escalacao) {
                mensagem.append(jogador.getNome() + " Numero:" + jogador.getNumero()).append("\n"); // imprime o jogador
                // escalado
            }

            JOptionPane.showMessageDialog(null, mensagem.toString());
        } else {
            JOptionPane.showMessageDialog(null, "Escalação Incompleta. Seu time deve conter 11 jogadores!");
        }
    }

    @Override
    public String toString() {
        return "Time [nomeEquipe=" + nomeEquipe + "]";
    }

}
