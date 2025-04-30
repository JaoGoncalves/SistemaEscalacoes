package classes;

public class MeioCampo extends Jogador {
    // criação do construtor sobrecarregado
    public MeioCampo(String nome, int numero)  {
        super(nome, numero);
    }

    @Override
    public String getPosicao() {// metodo sobrescrito da Classe jogador
        return "Meio Campo"; // irá retornar a posicao do jogador
    }
}
