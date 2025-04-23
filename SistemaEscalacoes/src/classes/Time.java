package classes;

import java.util.ArrayList;

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
    public void escalar(Jogador jogador) throws ExceptionEscalacao { // chamada da exceção personalizada

        if (escalacao.size() > 11) { // caso o usuário adicione mais que 11 jogadores, entra nessa condição
            // lança a exceção
            throw new ExceptionEscalacao("Não é permitido escalar mais de 11 jogadores.");
        }
        // se a condição acima nao for requisitada
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
        if (escalacao.size() == 11) { // caso o nº de jogadores seja menor que 11, ele nao imprime e cai no else
            System.out.println("A escalação " + getNomeEquipe() + ", vem com os seguintes jogadores: ");
            for (Jogador jogador : escalacao) { // foreach, para percorrer a lista e imprimir cada jogador e seu nº
                System.out.println(jogador.getNome() + ",N:" + jogador.getNumero());
            }
        } else { // irá imprimir a mensagem abaixo
            System.out.println("Escalação Incompleta. Seu time deve conter 11 jogadores!");
        }
    }

    @Override
    public String toString() {
        return "Time [nomeEquipe=" + nomeEquipe + "]";
    }

}
