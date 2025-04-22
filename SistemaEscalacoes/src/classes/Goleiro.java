package classes;

public class Goleiro extends Jogador {

    public Goleiro(String nome, int numero) throws NumeroInvalidoException {
        super(nome, numero);
    }

    @Override
    public String getPosicao() {
        return "Goleiro";
    }
}
