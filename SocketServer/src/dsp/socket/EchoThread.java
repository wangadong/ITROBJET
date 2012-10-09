/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dsp.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * EchoServerThread中新建线程来处理客户端请求 EchoThread作为这样的处理线程的基类，主要是把socket通信部分再封装一下
 * 在run函数中把客户端请求的消息交给ClientRequestHandler来处理，本类只负责socket的消息接收和发送
 */
public class EchoThread extends Thread {

	protected PrintStream out;
	protected String message;
	protected Socket clientSocket;

	public EchoThread(Socket clientSocket) {
		super();
		try {
			// 接受数据，但不允许有中文，因为会乱码
			DataInputStream in = new DataInputStream(
					clientSocket.getInputStream());
			byte[] buffer = new byte[10000]; // 缓冲区的大小
			in.read(buffer); // 处理接收到的报文，转换成字符串
			/**
			 * C++传递过来的中文字，需要转化一下。C++默认使用GBK。
			 * GB2312是GBK的子集，只有简体中文。因为数据库用GB2312，所以这里直接转为GB2312
			 * */
			message = new String(buffer, "utf-8");
			System.out.println(message);
			this.clientSocket = clientSocket;
			// 获得输出输出流
			out = new PrintStream(clientSocket.getOutputStream());
		} catch (IOException ex) {
			Logger.getLogger(EchoServerThread.class.getName()).log(
					Level.SEVERE, null, ex);
			try {
				clientSocket.close();
			} catch (IOException ex1) {
				Logger.getLogger(EchoServerThread.class.getName()).log(
						Level.SEVERE, null, ex1);
			}
		}
	}

	public void run() {
		try {
			/**
			 * 这里需要使用底层的byte方法来传递。因为即使直接写字符串，到底层还是调用了传递byte的方法。
			 * 这里涉及到编码问题。C++默认使用GBK，而GB2312是GBK的子集。
			 */
			byte[] responseBuffer = new ClientRequestHandler(message)
					.response().getBytes("GB2312");
			out.write(responseBuffer, 0, responseBuffer.length);
			String test = new ClientRequestHandler(message).response();
			out.print(test);// 直接UTF8输出，最终底层每个中文用3个字节传输
			// out.print(new
			// String(test.getBytes(),"GBK"));//转GBK失败，实际每个中文字用了4到5个字节传递
			// out.print(new
			// String(test.getBytes("GBK"),"GBK"));//转GBK，但底层还是要拆成字节数组，当然最终还是跟UTF8一样

		} catch (Exception ex) {
			Logger.getLogger(EchoThread.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		try {
			clientSocket.close();
		} catch (IOException ex) {
			Logger.getLogger(EchoThread.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	public String getMessage() {
		return message;
	}

	/**
	 * 获取输出流
	 * 
	 * @return
	 */
	public PrintStream getOutputStream() {
		return out;
	}
}
