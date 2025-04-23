package classes;

public class Zagueiro extends Jogador {
    // criação do construtor sobrecarregado
    public Zagueiro(String nome, int numero) throws NumeroInvalidoException { // lança a exceção caso o número digitado
                                                                              // nao atenda os requisitos
        super(nome, numero);
    }

    @Override
    public String getPosicao() { // aqui houve a sobrecarga do metodo da Classe jogador
        return "Zagueiro"; // irá retornar a posicao do jogador
    }
}
