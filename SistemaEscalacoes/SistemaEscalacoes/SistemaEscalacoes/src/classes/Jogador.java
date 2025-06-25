package classes;

public abstract class Jogador {
    protected int id;
    protected String nome;
    protected int numero;
    protected String posicao;
    protected int timeId;

    public Jogador() {
    }

    public Jogador(String nome, int numero) {
        this.nome = nome;
        this.numero = numero;
    }

    public Jogador(int id, String nome, int numero, int timeId) {
        this.id = id;
        this.nome = nome;
        this.numero = numero;
        this.timeId = timeId;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getTimeId() {
        return timeId;
    }

    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }

    public abstract String getPosicao();

    @Override
    public String toString() {
        return nome + " (#" + numero + ") - " + getPosicao();
    }
}