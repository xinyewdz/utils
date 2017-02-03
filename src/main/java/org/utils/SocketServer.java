package org.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO
 * 
 * @author Aaron
 * @date 2017年1月6日
 */
public class SocketServer {

	public static void main(String[] args) throws IOException {
		SocketServer ss = new SocketServer();
		ss.startServer(80);
		System.out.println("start server...");

	}

	private void startServer(int port) throws IOException {
		ServerSocketChannel ssChannel = ServerSocketChannel.open();
		ssChannel.configureBlocking(false);
		ssChannel.socket().bind(new InetSocketAddress(port));
		Selector selector = Selector.open();
		ssChannel.register(selector, SelectionKey.OP_ACCEPT);
		ExecutorService service = Executors.newCachedThreadPool();
		while (selector.select() > 0) {
			Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			while (iterator.hasNext()) {
				SelectionKey key = iterator.next();
				iterator.remove();
				if (key.isAcceptable()) {
					System.out.println("key isAcceptable");
					AcceptWorker acceptWorker = new AcceptWorker(key);
					service.execute(acceptWorker);
				} else if (key.isReadable()) {
					System.out.println("key isReadable");
					ReadWorker worker = new ReadWorker(key);
					service.execute(worker);
				} else if (key.isConnectable()) {
					System.out.println("key isConnectable");
				} else if (key.isWritable()) {
					System.out.println("key isWritable");
					WriteWorker worker = new WriteWorker(key);
					service.execute(worker);
				}
			}
		}
	}

}

class AcceptWorker implements Runnable {

	private SelectionKey key;

	public AcceptWorker(SelectionKey key) {
		this.key = key;
	}

	@Override
	public void run() {
		try {
			ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
			SocketChannel channel = ssChannel.accept();
			if (channel != null) {
				channel.configureBlocking(false);
				InetSocketAddress address = (InetSocketAddress) channel.getRemoteAddress();
				String hostName = address.getHostName();
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				String message = "hello," + hostName + "\r\n";
				buffer.put(message.getBytes("utf-8"));
				buffer.flip();
				channel.write(buffer);
				System.out.println("write hello message...");
				channel.register(key.selector(), SelectionKey.OP_WRITE);
				channel.register(key.selector(), SelectionKey.OP_READ);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

class ReadWorker implements Runnable {

	private SelectionKey key;

	public ReadWorker(SelectionKey key) {
		this.key = key;
	}

	@Override
	public void run() {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		try {
			SocketChannel channel = (SocketChannel) key.channel();
			channel.read(buffer);
			int len = buffer.position();
			buffer.flip();
			byte[] msg = new byte[len];
			buffer.get(msg);
			String message = new String(msg, "utf-8");
			message = "hello " + message + "\r\n";
			buffer.clear();
			buffer.put(message.getBytes("utf-8"));
			buffer.flip();
			channel.write(buffer);
			System.out.println("echo message...." + message);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

class WriteWorker implements Runnable {

	private SelectionKey key = null;

	public WriteWorker(SelectionKey key) {
		this.key = key;
	}

	@Override
	public void run() {
		System.out.println("write message ....");
		ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
		byteBuffer.flip();
		SocketChannel socketChannel = (SocketChannel) key.channel();
		try {
			socketChannel.write(byteBuffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
