package calculadora;

import java.net.*;
import java.io.*;

public class TCPServer {
	public static void main(String args[]) {
		try {
			int ServerPort = 7896; 
			ServerSocket listenSocket = new ServerSocket(ServerPort);
			while (true) {
				Socket clientSocket = listenSocket.accept();
				Connection c = new Connection(clientSocket);
			}
		} catch (IOException e) {
			System.out.println("Listen socket: " + e.getMessage()); 
		}
	}
}

class Connection extends Thread {
	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;
	Calculadora calculadora = new Calculadora();
	
	public Connection(Socket aClientSocket) {
		try {
			clientSocket = aClientSocket;
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
			this.start();
		} catch (IOException e) {
			System.out.println("Connecton: " + e.getMessage());
		}
	}
	
	public void run() {
		try {
			float resultado = 0;
			String data = in.readUTF();
			String[] valores = data.split(" ");
			float v1 = Float.parseFloat(valores[0]);
			String operador = String.valueOf(valores[1]);
			float v2 = Float.parseFloat(valores[2]);
			
			switch (operador) {
			case "+":
				resultado = calculadora.soma(v1, v2);
				break;
			case "-":
				resultado = calculadora.subtracao(v1, v2);
				break;
			case "*":
				resultado = calculadora.multiplicacao(v1, v2);
				break;
			case "/":
				resultado = calculadora.divisao(v1, v2);
				break;
			}
			String resposta = String.valueOf(resultado);
			out.writeUTF(resposta);
		} catch (EOFException e) {
			System.out.println("EOF:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("readline:" + e.getMessage());
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				/* */
			}
		}
	}
}