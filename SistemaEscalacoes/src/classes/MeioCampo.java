package classes;

public class MeioCampo extends Jogador {
    // criação do construtor sobrecarregado
    public MeioCampo(String nome, int numero) throws NumeroInvalidoException { // lança a exceção, caso o número
                                                                               // digitado não atenda os requisitos
        super(nome, numero);
    }

    @Override
    public String getPosicao() {// aqui houve a sobrecarga do metodo da Classe jogador
        return "Meio Campo"; // irá retornar a posicao do jogador
    }
}
