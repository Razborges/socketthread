package cliente;

import servidor.Servidor;

import java.io.*;
import java.net.*;
public class Cliente extends Thread {

    private static boolean done = false;
    private Socket conexao;

    public Cliente(Socket s) {
        conexao = s;
    }

    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            String msg;
            while(true) {
                msg = entrada.readLine();
                if(msg == null) {
                    break;
                }
                System.out.println(msg);
            }
            System.out.print("Digite 1 para desconectar: ");
            String linha = teclado.readLine();
            if (linha.equals("1")) {
                System.out.println("\n...> Sua conexão foi encerrada!");
                this.done = true;
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }

    public static void main(String args[]) {
        try {
            Socket conexao = new Socket("127.0.0.1", Servidor.PORT);
            PrintStream saida = new PrintStream(conexao.getOutputStream());

            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Entre com o seu nome: ");
            String meuNome = teclado.readLine();
            saida.println(meuNome);

            Thread t = new Cliente(conexao);
            t.start();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }
}
