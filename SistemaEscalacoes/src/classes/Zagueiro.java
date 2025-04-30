package classes;

public class Zagueiro extends Jogador {
    // criação do construtor sobrecarregado
    public Zagueiro(String nome, int numero) { 
        super(nome, numero);
    }

    @Override
    public String getPosicao() { // metodo sobrescrito da Classe jogador
        return "Zagueiro"; // irá retornar a posicao do jogador
    }
}
