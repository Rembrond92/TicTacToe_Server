package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
				
				// открываемый порт сервера 
				private static final int port = 6666;
				private String TEMPL_CONN = "The client '%d' closed the connection";
				private Socket socket;
				private int num;
				
				public static DataInputStream dis;
				public static DataOutputStream dos;
				
				public Server() {}
				
				public void setSocket(int num, Socket socket) {
								
								// Определение значений
								this.num = num;
								this.socket = socket;
								
								// Установка daemon-потока
								setDaemon(true);
								
       /*
        * Определение стандартного приоритета главного потока
        * int java.lang.Thread.NORM_PRIORITY = 5-the default
        * priority that is assigned to a thread.
        */
								setPriority(NORM_PRIORITY);
								
								// Старт потока
								start();
				}
				
				public static void println(Object text) {
								try {
												dos.writeUTF(text.toString() + "\n");
												dos.flush();
												
								} catch(Exception e) {e.printStackTrace();}
				}
				
				public static void println() {
								try {
												dos.writeUTF("\n");
												dos.flush();
												
								} catch(Exception e) {e.printStackTrace();}
				}
				
				public static void print(Object text) {
								try {
												dos.writeUTF(text.toString());
												dos.flush();
												
								} catch(Exception e) {e.printStackTrace();}
				}
				
				public static String readLine() {
							 
								String result = null;
								try {
												result = dis.readUTF();
												
								} catch(Exception e) {e.printStackTrace();}

								return result;
				}
				
				public void run() {
								
								try {
												
												// Определяем входной и выходной потоки сокета
          // для обмена данными с клиентом
												InputStream sin = socket.getInputStream();
												OutputStream sout = socket.getOutputStream();
												
												dis = new DataInputStream (sin );
												dos = new DataOutputStream(sout);
												
												TicTacToe.game = null;
											 Main.main();
												
												// завершаем соединение
												socket.close();
												System.out.println(String.format(TEMPL_CONN, num));
											 
								} catch(Exception e) {
												
												System.out.println("Exception : " + e);
								}
				}
				//---------------------------------------------------------
				
				public static void main(String[] ar) {
								
								ServerSocket srvSocket = null;
								
								try {
												try {
																int i = 0;// Счётчик подключений

             // Подключение сокета к localhost
																InetAddress ia;
																ia = InetAddress.getByName("localhost");
																srvSocket = new ServerSocket(port, 5, ia);
																System.out.println("Server " + srvSocket.getInetAddress() + ":" + srvSocket.getLocalPort() + " started\n\n");
																
																while(true) {
																				// ожидание подключения
																				Socket socket = srvSocket.accept();
																				
																				System.err.println("Client accepted");
																				
																				// Стартуем обработку клиента
                // в отдельном потоке
																				new Server().setSocket(i++, socket);
																}
												} catch(Exception e) {
																
																System.out.println("Exception : " + e);
												}
								}
								
								finally {
												try {
																
																if (srvSocket != null)
																				srvSocket.close();
												} catch (IOException e) {e.printStackTrace();}
												
								}
								
								System.exit(0);
				}
}