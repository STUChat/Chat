package cn.edu.stu.chat.client;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Queue;
import java.util.Scanner;

import cn.edu.stu.chat.model.Constant;

public  class ClientThread extends Thread{
//	public static String server = "192.168.178.72";
	public static String server = "119.29.206.150";
	public static int port = 8189;
	volatile private Queue<String> sendQ;
	volatile private Queue<String> recvQ;
	public ClientThread(Queue<String> sendQ, Queue<String> recvQ){
		super();
		this.sendQ = sendQ;
		this.recvQ = recvQ;
	}
	@Override
	public void run() {
		while (true) {
			try (Socket socket = new Socket(server, port)) {
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				final Scanner recvBox = new Scanner(is, "UTF-8");
				PrintWriter sendBox = new PrintWriter(new OutputStreamWriter(os, "UTF-8"), true);
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						while (!socket.isClosed() && recvBox.hasNextLine()) {
							String line = recvBox.nextLine();
							recvQ.add(line);
							//System.out.println(line);
						}
					}
				}).start();
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						while (true) {
							try {
								Thread.sleep(Constant.DETECTED_TIME);
								socket.sendUrgentData(0x111);
								//System.out.println("is nomal");
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								Log.e("time out","is going to reconnect!");
								try {
									socket.close();
									return;
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								e.printStackTrace();
							}
						}

					}
				}).start();
				boolean done = true;
				while (done && !socket.isClosed()) {
					if (!sendQ.isEmpty()) {
						Log.e("send", sendQ.peek());
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
			} finally {
				Log.e("socket close","out");

			}
		}
	}
}