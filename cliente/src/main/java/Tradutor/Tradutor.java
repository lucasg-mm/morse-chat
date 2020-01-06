package Tradutor;

import java.io.*;
import java.util.*;

/**
 * Essa classe realiza a tradução de uma mensagem em linguagem usual para morse
 * e vice-versa.
 *
 * @author lucas
 */
public class Tradutor {

    private HashMap<String, String> tabelaToMorse;  //a chave é um valor usual
    private HashMap<String, String> tabelaFromMorse;  //a chave é um valor em morse

    public Tradutor() {
        tabelaToMorse = new HashMap();
        tabelaFromMorse = new HashMap();
        
        try {
            leTabela();
        } catch (IOException ex) {
            System.out.println("Erro durante a leitura da tabela morse.");
        }
    }

    private void leTabela() throws IOException {  //esse método lê a tabela de um arquivo externo (codigo_morse.txt).
        String chaveAux;  //variáveis auxiliares para colocar valores no HashMap
        String valorAux;

        File doc = new File("codigo_morse.txt");
        Scanner sc = new Scanner(doc);

        while (sc.hasNext()) {
            chaveAux = sc.next();
            valorAux = sc.next();

            tabelaToMorse.put(chaveAux, valorAux);
            tabelaFromMorse.put(valorAux, chaveAux);
        }
    }

    public String toMorse(String mensagem) {
        String mensagemTraduzida = "";
        String substringAux;

        mensagem = mensagem.toUpperCase();  //converte tudo para maiúsculas, pois a tabela está em maiúsculas
        String[] palavras = mensagem.split("\\s+");

        for (int i = 0; i < palavras.length; i++) {  //traduz uma substring e adiciona três espaços
            substringAux = palavras[i];
            for (int j = 0; j < substringAux.length(); j++) {
                if (tabelaToMorse.containsKey(String.valueOf(substringAux.charAt(j)))) {
                    mensagemTraduzida += tabelaToMorse.get(String.valueOf(substringAux.charAt(j)));
                    if (j != substringAux.length() - 1) {
                        mensagemTraduzida += " ";  //adiciona um espaço entre cada letra
                    }
                }
            }

            if (i != palavras.length - 1) {
                mensagemTraduzida += "  ";  //adiciona três espaços entre cada palavra
            }
        }

        return mensagemTraduzida;
    }

    public String fromMorse(String mensagem) {
        String mensagemTraduzida = "";
        String[] digitos = mensagem.split("\\s");  //cada digito da mensagem (A-Z, 0-9)
        String digitoAux;

        for (int i = 0; i < digitos.length; i++) {
            digitoAux = digitos[i];
            if (tabelaFromMorse.containsKey(digitos[i])) {
                digitoAux = tabelaFromMorse.get(digitos[i]);
            }
            
            if(digitoAux.equals("")){
                digitoAux = " ";
            }
            
            mensagemTraduzida += digitoAux;
        }

        return mensagemTraduzida;
    }

}
