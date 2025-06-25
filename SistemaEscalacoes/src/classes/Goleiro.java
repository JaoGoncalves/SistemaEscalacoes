package classes;

public class Goleiro extends Jogador {

    public Goleiro() {
        super();
    }

    public Goleiro(String nome, int numero) {
        super(nome, numero);
    }

    public Goleiro(int id, String nome, int numero, int timeId) {
        super(id, nome, numero, timeId);
    }

    @Override
    public String getPosicao() {
        return "Goleiro";
    }
}