package classes;

public class NumeroInvalidoException extends Exception { // criação da exceção personalizada

    public NumeroInvalidoException(String enviaMensagem) { // criação do construtor, com um parametro do tipo String
        super(enviaMensagem); // como essa classe herda da Classe Exception
                              // fazemos a chamada do metodo super, para enviar a mensagem
    }
}
