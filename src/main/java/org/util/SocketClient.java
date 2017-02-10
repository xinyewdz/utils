package org.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * TODO
 * 
 * @author Aaron
 * @date 2017年1月6日
 */
public class SocketClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		SocketClient client = new SocketClient();
		String host = "localhost";
		int port = 80;
		client.start(host, port);

	}

	private void start(String host, int port) throws UnknownHostException, IOException {
		SocketChannel sc = SocketChannel.open();
		sc.connect(new InetSocketAddress(host, port));
		// SocketChannel sc = SocketChannel.open(new InetSocketAddress(host,
		// port));
		Socket socket = sc.socket();
		InputStream input = socket.getInputStream();
		new Thread(new Runnable() {

			@Override
			public void run() {
				String message;
				try {
					message = readInputStream(input);
					System.out.println(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
		boolean flag = true;
		OutputStream output = socket.getOutputStream();
		do {
			Scanner scanner = new Scanner(System.in);
			String message = scanner.nextLine();
			if (message == null || message.equals("exit")) {
				flag = false;
				continue;
			}
			output.write(message.getBytes("utf-8"));
			output.flush();
		} while (flag);
	}

	public static String readInputStream(InputStream input) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		StringBuilder builder = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}
		return builder.toString();
	}

}
