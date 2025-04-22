package classes;

public class Atacante extends Jogador {

    public Atacante(String nome, int numero) throws NumeroInvalidoException {
        super(nome, numero);
    }

    @Override
    public String getPosicao() {
        return "Jogador";
    }
}
