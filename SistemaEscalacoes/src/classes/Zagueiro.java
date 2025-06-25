package classes;

public class Zagueiro extends Jogador {

    public Zagueiro() {
        super();
    }

    public Zagueiro(String nome, int numero) {
        super(nome, numero);
    }

    public Zagueiro(int id, String nome, int numero, int timeId) {
        super(id, nome, numero, timeId);
    }

    @Override
    public String getPosicao() {
        return "Zagueiro";
    }
}