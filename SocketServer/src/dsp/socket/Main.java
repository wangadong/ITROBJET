/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dsp.socket;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class Main {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		try {
			// TODO code application logic here
			EchoServerThread echoThread = new EchoServerThread(1024);
//			String test = echoThread.g;
			// System.out.println(System.getProperty("file.encoding"));//
			// java默认编码是UTF-8
			// System.out.println(test);
			// System.out.println(test.length());
			// System.out.println(test.getBytes("GB2312").length);
			// System.out.println(test.getBytes("UTF8").length);
			// System.out.println(test.getBytes("GBK").length);
//			System.out.println(new String(test.getBytes("GB2312"), "GB2312"));// 用什么拆就用什么组装，否则显示乱码
			// System.out.println(new
			// String(test.getBytes("GB2312"),"GB2312").length());//用什么拆就用什么组装，否则显示乱码
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
