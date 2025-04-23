package classes;

public class Lateral extends Jogador {
    // criação do construtor sobrecarregado
    public Lateral(String nome, int numero) throws NumeroInvalidoException { // lança a exceção, caso o número digitado
                                                                             // não atenda os requisitos
        super(nome, numero);
    }

    @Override
    public String getPosicao() { // metodo sobrescrito da Classe jogador
        return "Lateral"; // irá retornar a posicao do jogador
    }

}
