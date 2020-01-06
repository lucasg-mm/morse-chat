package Servidor;

import java.net.*;
import java.util.*;
import java.io.*;

/**
 * classe responsável por receber uma mensagem de um cliente e repassá-la para
 * todos os outros
 *
 * @author lucas
 */
public class Repassador implements Runnable {

    @SuppressWarnings("FieldMayBeFinal")

    private Vector<Repassador> clientes;
    private Socket conexaoCliente;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    String mensagem;

    public Repassador(Vector<Repassador> clientes, Socket conexaoCliente) throws IOException {
        this.clientes = clientes;
        this.conexaoCliente = conexaoCliente;
        input = new ObjectInputStream(conexaoCliente.getInputStream());
        output = new ObjectOutputStream(conexaoCliente.getOutputStream());
        mensagem = "";
    }

    public void recebeMensagem() throws IOException {  //o repassador recebe uma mensagem do seu respectivo cliente  
        mensagem = input.readUTF();
    }

    public void repassaMensagem() throws IOException {  //repassa a mensagem para todos os outros clientes
        for (Repassador i : clientes) {
            if (i != this) {
                i.output.writeUTF(mensagem);
                i.output.flush();
            }
        }
    }

    @Override
    public void run() {
        do {
            try {
                //recebe uma mensagem e depois a repassa:
                recebeMensagem();
                repassaMensagem();
            } catch (IOException ex) {
            }
        } while (!mensagem.equals(". -..- .. -"));  //enquanto a  mensagem não for "exit"

        try {
            //fecha a conexão e os fluxos de i/o:
            conexaoCliente.close();
            input.close();
            output.close();
        } catch (IOException ex) {
            System.out.println("Erro durante o encerramento da conexão.");
        }
    }

}
