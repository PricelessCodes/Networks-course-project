import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.TexturePaint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Client1 extends JFrame {

	private JPanel contentPane;
	public static JTextArea ClientTextArea = new JTextArea();
	public static JTextField ClientTextField = new JTextField();	
	
	public static PrintWriter ClientWriter;
	public static BufferedReader ClientReader;
	public static String RetriveMessageFromServer;
	public static String SendMessageForServer;
	public static String Name = "Client_1: ";
	
	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client1 frame = new Client1();
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
	
	public Client1() {
		setTitle("Client_1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ClientTextArea.setBounds(12, 13, 408, 159);
		ClientTextArea.enable(false);
		contentPane.add(ClientTextArea);
				
		ClientTextField.setBounds(12, 209, 217, 31);
		contentPane.add(ClientTextField);
		ClientTextField.setColumns(10);
		
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
		ClientButton.setBounds(311, 209, 109, 31);
		contentPane.add(ClientButton);
	}
}
