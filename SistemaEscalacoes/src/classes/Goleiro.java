package classes;

public class Goleiro extends Jogador {

    // criação do construtor sobrecarregado
    public Goleiro(String nome, int numero)  {
        super(nome, numero);
    }

    @Override
    public String getPosicao() { // metodo sobrescrito da Classe jogador
        return "Goleiro"; // irá retornar a posicao do jogador
    }
}
