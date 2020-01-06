package Principal;

import GUI.JanelaChat;
import Tradutor.Tradutor;
import java.net.*;
import java.io.*;
import javax.swing.*;

/**
 * Fica esperando receber uma mensagem e imprime no cliente.
 *
 * @author lucas
 */
public class Recebedor implements Runnable {

    private Socket conexao;
    private ObjectInputStream input;
    private String msgRecebida;
    private JanelaChat cliente;  //cada cliente tem sua janela, por isso, uma janela representa um cliente
    private Tradutor trad;

    public Recebedor(JanelaChat cliente, Socket conexao, Tradutor trad) throws UnknownHostException, IOException {
        this.cliente = cliente;
        this.trad = trad;

        //cria a conexão com o servidor:
        this.conexao = conexao;

        //cria os fluxos de i/o:
        input = new ObjectInputStream(conexao.getInputStream());
    }

    /**
     * Escreve a mensagem recebida no chat
     */
    @SuppressWarnings("Convert2Lambda")
    private void escreveChat() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                msgRecebida =  trad.fromMorse(msgRecebida);  //traduz a mensagem e a imprime
                cliente.getCaixaMensagens().append("Cliente: " + msgRecebida + "\n");
            }
        });
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                //fica em listening:
                msgRecebida = input.readUTF();
                escreveChat();
            } catch (IOException ex) {
                try {
                    input.close();
                } catch (IOException ex1) {
                    System.out.println("Erro durante a desconexão.");
                }
                return;
            }
        }
    }
}
