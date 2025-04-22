package classes;

public class Zagueiro extends Jogador {

    public Zagueiro(String nome, int numero) throws NumeroInvalidoException {
        super(nome, numero);
    }

    @Override
    public String getPosicao() {
        return "Zagueiro";
    }
}
