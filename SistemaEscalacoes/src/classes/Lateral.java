package classes;

public class Lateral extends Jogador {

    public Lateral(String nome, int numero) throws NumeroInvalidoException {
        super(nome, numero);
    }

    @Override
    public String getPosicao() {
        return "Lateral";
    }

}
