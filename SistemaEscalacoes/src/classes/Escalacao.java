package classes;

import java.util.List;

public interface Escalacao {
    void escalarJogador(Jogador jogador) throws ExceptionEscalacao;

    void removerJogador(int numero) throws ExceptionEscalacao;

    List<Jogador> listarEscalacao();

    void validarEscalacao() throws ExceptionEscalacao;
}
