import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Client2 extends JFrame {

	private JPanel contentPane;
	public static JTextArea ClientTextArea = new JTextArea();
	public static JTextField ClientTextField = new JTextField();	
	
	public static PrintWriter ClientWriter;
	public static BufferedReader ClientReader;
	public static String RetriveMessageFromServer;
	public static String SendMessageForServer;
	public static String Name = "Client_2: ";
	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client2 frame = new Client2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		try {
			Socket SocketForClient = new Socket("127.0.0.1", 5555);		
			ClientReader = new BufferedReader(new InputStreamReader(SocketForClient.getInputStream()));			               
			ClientWriter = new PrintWriter(SocketForClient.getOutputStream(), true);			
			while(true)
			{
				RetriveMessageFromServer=null;
				if((RetriveMessageFromServer = ClientReader.readLine()) != null)  
			  	{
			  		ClientTextArea.append(RetriveMessageFromServer + "\n" );
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
	
	public Client2() {
		setTitle("Client_2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ClientTextField = new JTextField();
		ClientTextField.setText("");
		ClientTextField.setBounds(12, 207, 200, 33);
		contentPane.add(ClientTextField);
		ClientTextField.setColumns(10);
		
		ClientTextArea.setBounds(12, 13, 408, 169);
		ClientTextArea.enable(false);
		contentPane.add(ClientTextArea);
		
		JButton ClientButton = new JButton("Send");
		ClientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if ( ClientTextField.getText() != null && ClientTextField.getText() != "" )
				{
					SendMessageForServer = ClientTextField.getText();
					ClientTextArea.append(Name+SendMessageForServer + "\n");
					ClientTextField.setText("");
					ClientWriter.println(Name+SendMessageForServer);             
		    		ClientWriter.flush();
				}
			}
		});
		ClientButton.setBounds(308, 207, 112, 35);
		contentPane.add(ClientButton);
	}
}
