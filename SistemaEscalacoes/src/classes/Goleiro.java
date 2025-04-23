package classes;

public class Goleiro extends Jogador {

    // criação do construtor sobrecarregado
    public Goleiro(String nome, int numero) throws NumeroInvalidoException { // lança exceção caso o número digitado não
                                                                             // atenda os requisitos
        super(nome, numero);
    }

    @Override
    public String getPosicao() { // aqui houve a sobrecarga do metodo da Classe jogador
        return "Goleiro"; // irá retornar a posicao do jogador
    }
}
