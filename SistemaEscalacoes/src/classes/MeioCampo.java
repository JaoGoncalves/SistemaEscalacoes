package classes;

public class MeioCampo extends Jogador {

    public MeioCampo(String nome, int numero) throws NumeroInvalidoException {
        super(nome, numero);
    }

    @Override
    public String getPosicao() {
        return "Meio Campo";
    }
}
