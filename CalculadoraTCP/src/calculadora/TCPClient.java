package calculadora;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class TCPClient {
	public static void main(String args[]) {
		Socket s = null;
		try {
			String calculo = null;
			int serverPort = 7896;
			s = new Socket("localhost", serverPort);
			DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			
			Scanner entrada = new Scanner(System.in);
			System.out.println("Calculadora: " + "Sintaxe --> Valor 1 Operador Valor 2");
			calculo = entrada.nextLine();
			out.writeUTF(calculo);
			String resposta = in.readUTF();
			System.out.println("Received: " + resposta);
		} catch (UnknownHostException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (EOFException e) {
			System.out.println("EOF: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("readLine: " + e.getMessage());
		} finally {
				if (s != null) 
					try {
						s.close();
					} catch (IOException e) {
						System.out.println("close: " + e.getMessage());
					}
		}
	}
}