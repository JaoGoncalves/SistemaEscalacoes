package classes;

public class ExceptionEscalacao extends Exception { // criação da exceção personalizada

    // criação do construtor sobrecarregado enviando um paramêtro do tipo String
    public ExceptionEscalacao(String enviarMensagem) {
        super(enviarMensagem); // quando chamado, irá enviar uma mensagem, explicando o erro,etc.
    }
}
