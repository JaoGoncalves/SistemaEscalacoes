package classes;

public class Lateral extends Jogador {
    // criação do construtor sobrecarregado
    public Lateral(String nome, int numero){ 
        super(nome, numero);
    }

    @Override
    public String getPosicao() { // metodo sobrescrito da Classe jogador
        return "Lateral"; // irá retornar a posicao do jogador
    }

}
