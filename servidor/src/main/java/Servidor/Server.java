package Servidor;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Corresponde ao proprio servidor.
 *
 * @author lucas
 */
public class Server {
    public static void main(String[] args) throws IOException{
        ServerSocket conexaoServidor = new ServerSocket(12345, 10);  //corresponde ao próprio servidor, sendo que 10 clientes podem se conectar a ele.    
        Vector<Repassador> clientes = new Vector();  //cada cliente tem o seu próprio repassador de mensagens
        Socket conexaoCliente;  //armazena uma conexão com um cliente

        while (true) {
            System.out.println("Esperando conexões.");
            conexaoCliente = conexaoServidor.accept();
            System.out.println("Um novo cliente foi conectado.");

            //cria o repassador do novo cliente e o adiciona ao Vector:
            Repassador novoCliente = new Repassador(clientes, conexaoCliente);
            clientes.add(novoCliente);
            
            Thread t = new Thread(novoCliente);
            t.start();
        }
    }
}
