package classes;

public class Atacante extends Jogador {
    // criação do construtor sobrecarregado
    public Atacante(String nome, int numero) throws NumeroInvalidoException { // lança a exceção caso o número digitado
                                                                              // nao atenda os requisitos
        super(nome, numero);
    }

    @Override
    public String getPosicao() { // metodo sobrescrito da Classe jogador
        return "Atacante"; // irá retornar a posicao do jogador
    }
}
