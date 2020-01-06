package Principal;

import GUI.JanelaChat;
import java.io.IOException;
import javax.swing.SwingUtilities;

/**
 * Essa classe inicia o cliente e cria sua interface gráfica.
 *
 * @author lucas
 */
public class Principal {

    /**
     * Esse é o método principal do programa relativo ao cliente.
     *
     * @param args os argumentos da linha de comando
     * @throws java.io.IOException
     */
    @SuppressWarnings("Convert2Lambda")
    public static void main(String[] args) throws IOException {
        //cria a janela na event dispatch thread:
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JanelaChat chat = new JanelaChat();
                chat.setVisible(true);
            }
        });
    }

}
