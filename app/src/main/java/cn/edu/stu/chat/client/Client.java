package cn.edu.stu.chat.client;

import android.util.Log;

import java.io.*;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.util.*;
public class Client {

	public static void handleMessage() throws Exception{
		// TODO Auto-generated method stub
//		String host = "123.206.32.192";
		int port = 8189;
		String host = "119.29.206.150";
//		int port = 13;
		try(SocketChannel s = SocketChannel.open(new InetSocketAddress(host,port))){
			OutputStream os = Channels.newOutputStream(s);
			PrintWriter out = new PrintWriter(os, true);
			while(!Thread.currentThread().isInterrupted()){
				Scanner sc = new Scanner(s);
				Scanner stdin = new Scanner(System.in);
				while(true){
					if(sc.hasNextLine()){
						String line = sc.nextLine();
						Log.e("message",line);
					}
					if(stdin.hasNextLine()){
						
						String str = stdin.nextLine();
						if(str.equals("xxx"))
							Thread.currentThread().interrupt();
						out.println(str);
					}
					
				}
			}
			
		}finally{
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Log.e("lawliex","Channel closed\n");
				}
			}).start();
		}
	}

}
