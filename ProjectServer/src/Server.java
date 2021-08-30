import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.TextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Server extends JFrame {

	private JPanel contentPane;
	public static JTextField ServerTextField = new JTextField();
	public static JTextArea ServerTextArea = new JTextArea();

	public static int Port = 5555;
	public static PrintWriter ServerWriter;
	public static PrintStream Print;
	public static BufferedReader ServerReader;
	public static Scanner Scan;
	public static String RetriveMessageFromClient;
	public static String SendMessageForClient;
	public static String Name = "Server: ";
	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server frame = new Server();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		try {
			ServerSocket SocketForServer = new ServerSocket(Port);
			while(true)
			{
				Socket SocketForClient = SocketForServer.accept();
				 Thread MyThreadClass = new Thread() {
					 @Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								RecieveSocketOfClient(SocketForClient);
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						}
				 };
				 MyThreadClass.start();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void RecieveSocketOfClient(Socket SocketForClient) 
	{
		try {
			while(true)
			{
				Scan = new Scanner(SocketForClient.getInputStream());
				//ServerReader = new BufferedReader(new InputStreamReader(SocketForClient.getInputStream()));
				Print = new PrintStream(SocketForClient.getOutputStream());
				//ServerWriter = new PrintWriter(SocketForClient.getOutputStream(), true);
				RetriveMessageFromClient=null;
				/*if((RetriveMessageFromClient = ServerReader.readLine()) != null)  
				{
					ServerTextArea.append(RetriveMessageFromClient + "\n");
				}*/
				if((RetriveMessageFromClient = Scan.nextLine()) != null)  
				{
					ServerTextArea.append(RetriveMessageFromClient + "\n");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the frame.
	 */
	
	public Server() {
		setTitle("Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ServerTextField.setBounds(12, 213, 211, 25);
		contentPane.add(ServerTextField);
		ServerTextField.setColumns(10);
		
		ServerTextArea.setBounds(12, 13, 408, 175);
		ServerTextArea.enable(false);
		contentPane.add(ServerTextArea);
		
		JButton ServerButton = new JButton("Send");
		ServerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ( ServerTextField.getText() != null && ServerTextField.getText() != "" )
				{
					SendMessageForClient = ServerTextField.getText();
					ServerTextArea.append(Name+SendMessageForClient + "\n");
					ServerTextField.setText("");
					/*ServerWriter.println(Name+SendMessageForClient);             
					ServerWriter.flush();*/
					Print.println(Name+SendMessageForClient);
					Print.flush();
				}
			}
		});
		ServerButton.setBounds(302, 201, 118, 39);
		contentPane.add(ServerButton);
	}
}