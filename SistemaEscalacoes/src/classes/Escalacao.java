package classes;

public interface Escalacao {

    public void escalar(Jogador jogador) throws ExceptionEscalacao;

    public void removerJogador(int numero) throws ExceptionEscalacao;

    public void imprimeEscalacao();
}
