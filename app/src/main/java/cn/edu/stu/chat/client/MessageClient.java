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
		clientThread = new ClientThread(sendQ, recvQ);
		clientThread.start();
	}

	public Queue<String> getSendQ() {
		return sendQ;
	}
}
