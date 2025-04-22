package classes;

abstract class Jogador {
    private String nome;
    private int numero;

    public Jogador(String nome, int numero) throws NumeroInvalidoException {
        this.nome = nome;
        if (numero < 0 || numero > 99) {
            throw new NumeroInvalidoException(
                    "Não é permitido esse tipo de numeração. O número do jogador deve estar entre 1 e 99.");
        }
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public int getNumero() {
        return numero;
    }

    public abstract String getPosicao();

}
