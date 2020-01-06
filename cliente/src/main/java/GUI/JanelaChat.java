package GUI;

import Principal.Recebedor;
import Tradutor.Tradutor;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.*;

/**
 * Essa classe monta a janela de chat e lida com os seus eventos.
 *
 * @author lucas
 */
public class JanelaChat extends JFrame {

    /**
     * Corresponde à área em que as mensagens enviadas pelos clientes são
     * exibidas.
     */
    private JTextArea caixaMensagens;

    /**
     * Corresponde ao campo onde uma mensagem é escrita.
     */
    private JTextField campoEnviar;
    private Socket conexao;
    private ObjectOutputStream output;
    private Thread recebeMsg;  //thread para receber mensagens
    private Tradutor trad;

    /**
     * Monta a janela de chat.
     */
    public JanelaChat() {
        super("Cliente");

        //instancia caixaMensagens e campoEnviar:
        caixaMensagens = new JTextArea();
        campoEnviar = new JTextField();
        campoEnviar.addActionListener(new campoEnviarListener());

        //a caixaMensagens é não editável:
        caixaMensagens.setEditable(false);

        //adiciona os dois componentes criados à janela:
        this.add(new JScrollPane(caixaMensagens), BorderLayout.CENTER);
        this.add(campoEnviar, BorderLayout.NORTH);

        //conecta e instancia um tradutor:
        trad = new Tradutor();
        conecta();

        //define alguns parâmetros da janela e a deixa visível:
        this.setSize(300, 150);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public JTextArea getCaixaMensagens() {
        return caixaMensagens;
    }
    
    private void conecta() {  //conecta ao servidor
        caixaMensagens.setText(">Conectando-se ao servidor.\n");
        try {
            conexao = new Socket("127.0.0.1", 12345);
            output = new ObjectOutputStream(conexao.getOutputStream());
            recebeMsg = new Thread(new Recebedor(this, conexao, trad));
            recebeMsg.start();
            caixaMensagens.append(">Conectado com sucesso.\n");
        } catch (IOException ex) {
            caixaMensagens.setText("#Erro ao conectar-se ao servidor.\n");
        }
    }
    
    private void desconectar() {
        recebeMsg.interrupt();
        try {
            conexao.close();
            output.close();
        } catch (IOException ex) {
            System.out.println("Erro durante a desconexão.");
        }
    }
    
    private void enviarMsg(String msg) {
        try {
            //envia uma mensagem para os outros clientes:
            caixaMensagens.append("Você: " + msg + "\n");
            msg = trad.toMorse(msg);
            output.writeUTF(msg);
            output.flush();
            
            if (msg.equals(". -..- .. -")) {
                desconectar();
                caixaMensagens.append(">Você foi desconectado do servidor.");
                campoEnviar.setEditable(false);
            }
        } catch (IOException ex) {
            caixaMensagens.append("#Erro durante o envio da mensagem.\n");
        }        
    }
    
    public class campoEnviarListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            enviarMsg(ae.getActionCommand());
            campoEnviar.setText("");
        }
    }
}
