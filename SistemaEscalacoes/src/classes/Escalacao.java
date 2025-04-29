package classes;

// criação da interface
public interface Escalacao {
    // criação do metodo escalar(irá adicionar um novo jogador), que sera chamado na
    // classe Time
    public void escalarJogador(Jogador jogador) throws ExceptionEscalacao;

    // nesse método, iremos remover um jogador especifico
    public void removerJogador(int numero) throws ExceptionEscalacao;

    // no metodo imprimeEscalacao, ira imprimir os jogadores escalados e seus nomes
    public void imprimeEscalacao();
}
