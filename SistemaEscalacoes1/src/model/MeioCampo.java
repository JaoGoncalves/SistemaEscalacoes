package model;

public class MeioCampo extends Jogador {

    public MeioCampo() {
        super();
    }

    public MeioCampo(String nome, int numero) {
        super(nome, numero);
    }

    public MeioCampo(int id, String nome, int numero, int timeId) {
        super(id, nome, numero, timeId);
    }

    @Override
    public String getPosicao() {
        return "Meio Campo";
    }
}