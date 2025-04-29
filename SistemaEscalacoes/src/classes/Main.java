package classes;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) throws ExceptionEscalacao, NumeroInvalidoException {
        // instanciando o objeto equipe
        Time equipe = new Time();
        // entrada de dados
        String nomeEquipe = JOptionPane.showInputDialog("Digite o nome do time: ");
        // adicionando o nome da equipe
        equipe.setNomeEquipe(nomeEquipe);

        try {
            equipe.escalarJogador(new Goleiro("Alisson", 1));
            equipe.escalarJogador(new Zagueiro("Virgil Van Dijk", 4));
            equipe.escalarJogador(new Zagueiro("Zé Gabriel", 3));
            equipe.escalarJogador(new Lateral("Bernabei", 24));
            equipe.escalarJogador(new Lateral("Trent Alexander Arnold", 66));
            equipe.escalarJogador(new Lateral("Trent Alexander Arnold", 66));
            equipe.escalarJogador(new MeioCampo("Casemiro", 8));
            equipe.escalarJogador(new MeioCampo("Neymar", 11));
            equipe.escalarJogador(new MeioCampo("AlanPa", 10));
            equipe.escalarJogador(new Atacante("Raphinha", 12));
            equipe.escalarJogador(new Atacante("Borré", 9));
            equipe.escalarJogador(new Atacante("Vinicius Jr", 7));

            equipe.removerJogador(04);
            equipe.imprimeEscalacao();
        } catch (ExceptionEscalacao e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            System.out.println("Operação finalizada");
        }
    }
}
