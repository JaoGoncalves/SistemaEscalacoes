package classes;

abstract class Jogador {
    // adiciona os atributos da classe Jogador
    protected String nome;
    protected int numero;

    // cria o construtor, lançando uma exceção
    public Jogador(String nome, int numero) throws NumeroInvalidoException {
        this.nome = nome;
        if (numero < 0 || numero > 99) { // Exceção caso o numero seja negativo, ou maior que 99, segundo regras os
                                         // números devem ir de 1 a 99.
            throw new NumeroInvalidoException(
                    "Não é permitido esse tipo de numeração. O número do jogador deve estar entre 1 e 99.");
        }
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
