package classes;

import javax.swing.JOptionPane;

public class EscalarTime {
    public static void main(String[] args) throws NumeroInvalidoException, ExceptionEscalacao {
        // instanciando o objeto equipe
        Time equipe = new Time();
        // entrada de dados
        String nomeEquipe = JOptionPane.showInputDialog("Digite o nome do time:");
        // adicionando o nome da equipe
        equipe.setNomeEquipe(nomeEquipe);
        // quantidade de jogadores a serem escalados
        String quantidadetxt = JOptionPane.showInputDialog("Quantos jogadores deseja escalar?");
        int quantidade = Integer.parseInt(quantidadetxt); // converte a string para inteiro
        // validação da quantidade de jogadores
        if (quantidade < 11 || quantidade > 11) {

            JOptionPane.showMessageDialog(null, "Sua escalação deve conter 11 jogadores.");
        } else {
            // for para escalar jogadores
            for (int i = 0; i < quantidade; i++) {
                // pergunta o nome do jogador
                // JOptionPane.showInputDialog é um método que exibe uma caixa de diálogo para o
                // usuário inserir um valor
                String nome = JOptionPane.showInputDialog("Jogador " + (i + 1) + " - Nome:");

                int numero = 0;
                // loop para validar o número do jogador
                while (true) {
                    // tenta capturar o número do jogador
                    try {
                        // pergunta o número do jogador
                        numero = Integer
                                .parseInt(JOptionPane.showInputDialog("Digite o  número do jogador: ", "(1 a 99)"));
                        // verifica se o número é válido
                        if (numero < 1 || numero > 99) {
                            // lança a exceção personalizada
                            throw new NumeroInvalidoException("Número inválido! Deve estar entre 1 e 99.");
                        }
                        break;
                    } catch (NumeroInvalidoException e) {
                        // Exibe a mensagem de erro personalizada
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Erro. Número inválido",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                // pergunta a posição do jogador
                if (quantidade > 0) {
                    String posicao = JOptionPane.showInputDialog(
                            "Qual a posição do jogador: ", "(Goleiro, Zagueiro, Lateral, Meio Campo, Atacante)")
                            .toLowerCase(); //
                    try {
                        switch (posicao) {
                            case "goleiro":
                                equipe.escalarJogador(new Goleiro(nome, numero)); // adiciona o goleiro
                                break;
                            case "zagueiro":
                                equipe.escalarJogador(new Zagueiro(nome, numero)); // adiciona o zagueiro
                                break;
                            case "lateral":
                                equipe.escalarJogador(new Lateral(nome, numero)); // adiciona o lateral
                                break;
                            case "meio campo":
                                equipe.escalarJogador(new MeioCampo(nome, numero)); // adiciona o meio campo
                                break;
                            case "atacante":
                                equipe.escalarJogador(new Atacante(nome, numero)); // adiciona o atacante
                                break;
                            default:
                                // Exibe uma mensagem de erro se a posição não for válida
                                JOptionPane.showMessageDialog(null, "Posição inválida! Jogador não adicionado.", "Erro",
                                        JOptionPane.WARNING_MESSAGE);// JOptionPane.WARNING_MESSAGE é um tipo de
                                                                     // mensagem de
                                                                     // aviso
                                continue;
                        }
                    } catch (ExceptionEscalacao e) {
                        // Exibe a mensagem de erro personalizada
                        JOptionPane.showMessageDialog(null, "Erro ao escalar jogador: " + e.getMessage(), "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
            // remove o jogador
            String numeroRemoverTxt = (JOptionPane
                    .showInputDialog("Quer remover um jogador? Se sim, digite o número do jogador, se não digite 0: "));
            int numeroRemover = Integer.parseInt(numeroRemoverTxt); // converte a string para inteiro
            while (numeroRemover != 0) {
                try {
                    equipe.removerJogador(numeroRemover); // remove o jogador
                } catch (ExceptionEscalacao e) {
                    // Exibe a mensagem de erro personalizada
                    JOptionPane.showMessageDialog(null, "Erro ao remover jogador: " + e.getMessage(), "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        }
        // imprime a escalação
        equipe.imprimeEscalacao();
    }
}
