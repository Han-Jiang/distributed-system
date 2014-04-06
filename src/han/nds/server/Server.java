package han.nds.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket welcomeSocket = null;
		ServerSocket dataSocket = null;
		boolean running = true;
		try {
			welcomeSocket = new ServerSocket(50100);
			System.out.println("Server started at port: 50100");

		} catch (IOException e) {
			System.err.println("Could not listen on port: 50100.");
			System.exit(-1);
		}
		while (running) {
			Socket connectionSocket = welcomeSocket.accept();
			Thread serverThread = new WorkerThread(connectionSocket);
			serverThread.start();
		}
		welcomeSocket.close();
	}
}