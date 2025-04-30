package classes;

public class Atacante extends Jogador {
    // criação do construtor sobrecarregado
    public Atacante(String nome, int numero)  {
        super(nome, numero);
    }

    @Override
    public String getPosicao() { // metodo sobrescrito da Classe jogador
        return "Atacante"; // irá retornar a posicao do jogador
    }
}
