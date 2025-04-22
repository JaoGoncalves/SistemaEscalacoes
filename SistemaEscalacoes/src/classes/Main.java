package classes;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) throws ExceptionEscalacao, NumeroInvalidoException {
        Time equipe = new Time();

        String nomeEquipe = JOptionPane.showInputDialog("Digite o nome do time: ");
        equipe.setNomeEquipe(nomeEquipe);

        try {
            equipe.escalar(new Goleiro("Alisson", 1));
            equipe.escalar(new Zagueiro("Virgil Van Dijk", 4));
            equipe.escalar(new Zagueiro("Zé Gabriel", 3));
            equipe.escalar(new Lateral("Bernabei", 24));
            equipe.escalar(new Lateral("Trent Alexander Arnold", 66));
            equipe.escalar(new MeioCampo("Casemiro", 8));
            equipe.escalar(new MeioCampo("Neymar", 11));
            equipe.escalar(new MeioCampo("AlanPa", 10));
            equipe.escalar(new Atacante("Raphinha", 12));
            equipe.escalar(new Atacante("Borré", 9));
            equipe.escalar(new Atacante("Vinicius Jr", 7));

            // equipe.removerJogador(04);
            equipe.imprimeEscalacao();
        } catch (ExceptionEscalacao e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            System.out.println("Operação finalizada");
        }
    }
}
