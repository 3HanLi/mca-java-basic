package com.wy.mca.concurrent.basic.sync;

import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 管道输入/输出流（了解）
 * 	1	用于线程之间的数据传输，传输的媒介是内存
 * 	2	包含四个类：
 * 		2.1	PipedInputStream、PipedOutputStream
 * 		2.2	PipedReader、PipedWriter
 * 		2.3	以上四个类，在读写流的时候，如果缓冲区为空，那么读取流的时候会阻塞（wait），等输出流写数据的时候会调用notify方法通知输入流线程进行读取
 * 	3	使用：
 * 		PipedWriter out=new PipedWriter();
 * 		PipedReader in=new PipedReader();
 * 		out.connect(in);
 * @author wangyong
 * @date 2019年2月18日 上午11:14:30
 */
public class Piped {

	public static void main(String[] args) throws Exception {
		PipedWriter out=new PipedWriter();
		PipedReader in=new PipedReader();
		//连接输入输出流
		out.connect(in);
		Thread printThread = new Thread(new Print(in), "PrintThread");
		printThread.start();
	
		int receive=0;
		try {
			//System.in.read:读取不到数据会阻塞，并不会返回-1
			while((receive =  System.in.read())!= -1){
				out.write(receive);
			}
		} catch (Exception e) {
		}finally {
			out.close();
		}
	}
	
	static class Print implements Runnable{
		private PipedReader in;
		public Print(PipedReader in){
			this.in=in;
		}
		
		@Override
		public void run() {
			int receive = 0 ;
			try {
				//read:读取不到数据会阻塞，并不会返回-1
				while((receive = in.read())!= -1){
					System.out.print((char)receive);
				}
			} catch (Exception e) {
			}
		}
		
	}
}
