package classes;

abstract class Jogador {
    // adiciona os atributos da classe Jogador
    protected String nome;
    protected int numero;

    // cria o construtor
    public Jogador(String nome, int numero) {
        this.nome = nome;
        this.numero = numero;
    }

    public String getNome() { // criação do get, caso solicite o nome do jogador solicitado
        return nome;
    }

    public int getNumero() { // igual o de cima, aqui irá retornar o número do jogador solicitado
        return numero;
    }

    // método abstrato, cada subclasse irá apresentar uma diferente mensagem
    public abstract String getPosicao();

}
