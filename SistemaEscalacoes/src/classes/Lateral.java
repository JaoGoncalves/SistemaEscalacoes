package classes;

public class Lateral extends Jogador {

    public Lateral() {
        super();
    }

    public Lateral(String nome, int numero) {
        super(nome, numero);
    }

    public Lateral(int id, String nome, int numero, int timeId) {
        super(id, nome, numero, timeId);
    }

    @Override
    public String getPosicao() {
        return "Lateral";
    }
}