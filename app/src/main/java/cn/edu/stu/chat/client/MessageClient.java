package cn.edu.stu.chat.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.*;
import java.io.*;
import java.net.*;

public class MessageClient {
	public static String server = "119.29.206.150";
	public static int port = 8189;
	private Thread clientThread = null;
	
	volatile private Queue<String> sendQ;
	volatile private Queue<String> recvQ;
	public Thread getClientThread(){
		return clientThread;
	}
	public void setSendQ(Queue<String> que){
		this.sendQ = que;
	}
	public Queue<String> getRecvQ(){
		return recvQ;
	}
	public void sendMessage(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Scanner inputBox = new Scanner(System.in);
				while(inputBox.hasNextLine()){
					sendQ.add(inputBox.nextLine());
				}
			}
		}).start();
	}
	public MessageClient(){
		sendQ = new LinkedList<String>();
		recvQ = new LinkedList<String>();
		//sendMessage();
		clientThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				try(Socket socket = new Socket(server, port)){
					InputStream is = socket.getInputStream();
					OutputStream os = socket.getOutputStream();
					final Scanner recvBox = new Scanner(is,"UTF-8");
					PrintWriter sendBox = new PrintWriter(new OutputStreamWriter(os,"UTF-8"),true);
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							while(recvBox.hasNextLine()){
								String line = recvBox.nextLine();
								recvQ.add(line);
								//System.out.println(line);
							}
						}
					}).start();
					boolean done = true;
					while( done){
						if(!sendQ.isEmpty()){
							sendBox.println(sendQ.poll());
						}
						
						//System.out.println("in");
					}
						
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					System.out.println("socket close");
				}
			}
		});
		clientThread.start();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new MessageClient();
		
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("over");
		
	}

}
