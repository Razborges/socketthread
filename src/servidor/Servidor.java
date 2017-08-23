package servidor;

import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor extends Thread {

    public final static int PORT = 2222;

    private Socket conexao;
    private String meuNome;

    public Servidor(Socket socket) {
        this.conexao = socket;
    }

    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            PrintStream saida = new PrintStream(conexao.getOutputStream());

            nome(entrada);

            saida.println(this.meuNome + " você se conectou ao servidor.");
            saida.println("Data: " + new Date().toString());

            System.out.println("...> Com a identificação de " + this.meuNome);

            conexao.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }

    private void nome(BufferedReader entrada) throws IOException {
        while(this.meuNome == null) {
            this.meuNome = entrada.readLine();
        }
    }

    public static void main(String args[]) {
        try {
            ServerSocket s = new ServerSocket(Servidor.PORT);
            System.out.println(">>> Servidor escutando na porta " + Servidor.PORT + " <<<");

            while (true) {
                Socket conexao = s.accept();
                System.out.println(" Host " + conexao.getInetAddress().getHostAddress() + " conectado.");
                Thread t = new Servidor(conexao);
                t.start();
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }
}