package classes;

public class Atacante extends Jogador {

    public Atacante() {
        super();
    }

    public Atacante(String nome, int numero) {
        super(nome, numero);
    }

    public Atacante(int id, String nome, int numero, int timeId) {
        super(id, nome, numero, timeId);
    }

    @Override
    public String getPosicao() {
        return "Atacante";
    }
}