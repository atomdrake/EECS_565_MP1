//Coded by: Adam Drake
//For EECS 565: Mini-Project 1
//Submitted: 2/8/15
//Language Used: Java




import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Cipher_Interface {

	String originalText, resultText, keyText;  
	String resetText= "";
	long start, timeTaken; 
	private JFrame frmVigenereCipherApplet;
	private JTextField textField;
	private JTextField keyField;
	private JTextField outputField;
	private JTextField timeField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cipher_Interface window = new Cipher_Interface();
					window.frmVigenereCipherApplet.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Cipher_Interface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmVigenereCipherApplet = new JFrame();
		frmVigenereCipherApplet.setTitle("Vigenere Cipher Applet - A Drake 2015");
		frmVigenereCipherApplet.setBounds(100, 100, 450, 681);
		frmVigenereCipherApplet.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmVigenereCipherApplet.getContentPane().setLayout(null);
		
		JButton encrypt_button = new JButton("Encrypt");
		encrypt_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				gatherStrings();
				
				//Using a timer to judge how long this takes
				start = System.nanoTime();
				resultText = runEncrypt();
				timeTaken = System.nanoTime() - start;
				
				pushResultString();
			}
		});
		encrypt_button.setFont(new Font("Tahoma", Font.BOLD, 16));
		encrypt_button.setBounds(62, 277, 115, 62);
		frmVigenereCipherApplet.getContentPane().add(encrypt_button);
		
		JButton decrypt_button = new JButton("Decrypt");
		decrypt_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				gatherStrings();
				
				//Using a timer to judge how long this takes
				start = System.nanoTime();
				resultText = runDecrypt();
				timeTaken = System.nanoTime() - start;
				
				pushResultString();
			}
		});
		decrypt_button.setFont(new Font("Tahoma", Font.BOLD, 16));
		decrypt_button.setBounds(247, 277, 115, 62);
		frmVigenereCipherApplet.getContentPane().add(decrypt_button);
		
		textField = new JTextField();
		textField.setToolTipText("Enter Cipher or plaintext here...");
		textField.setBounds(62, 44, 300, 110);
		frmVigenereCipherApplet.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel cipherFieldLabel = new JLabel("Plaintext/Ciphertext:");
		cipherFieldLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		cipherFieldLabel.setBounds(59, 19, 146, 23);
		frmVigenereCipherApplet.getContentPane().add(cipherFieldLabel);
		
		keyField = new JTextField();
		keyField.setToolTipText("Enter Key to use here...");
		keyField.setBounds(62, 190, 303, 23);
		frmVigenereCipherApplet.getContentPane().add(keyField);
		keyField.setColumns(10);
		
		JLabel keyFieldLabel = new JLabel("Key:");
		keyFieldLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		keyFieldLabel.setBounds(62, 165, 46, 14);
		frmVigenereCipherApplet.getContentPane().add(keyFieldLabel);
		
		outputField = new JTextField();
		outputField.setToolTipText("Output from operation will appear here...");
		outputField.setBounds(59, 364, 303, 122);
		frmVigenereCipherApplet.getContentPane().add(outputField);
		outputField.setColumns(10);
		
		JLabel outputLabel = new JLabel("Output:");
		outputLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		outputLabel.setBounds(62, 350, 46, 14);
		frmVigenereCipherApplet.getContentPane().add(outputLabel);
		
		JButton btnResetFields = new JButton("Reset Fields");
		btnResetFields.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				resetAllFields();
			}


		});
		btnResetFields.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnResetFields.setBounds(161, 596, 110, 36);
		frmVigenereCipherApplet.getContentPane().add(btnResetFields);
		
		timeField = new JTextField();
		timeField.setToolTipText("Time taken for operation to complete ...");
		timeField.setBounds(167, 530, 86, 20);
		frmVigenereCipherApplet.getContentPane().add(timeField);
		timeField.setColumns(10);
		
		JLabel lblTimeTaken = new JLabel("Time Taken:");
		lblTimeTaken.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTimeTaken.setBounds(91, 522, 86, 36);
		frmVigenereCipherApplet.getContentPane().add(lblTimeTaken);
		
		JLabel lblNs = new JLabel("ns");
		lblNs.setBounds(263, 533, 46, 14);
		frmVigenereCipherApplet.getContentPane().add(lblNs);
	}
	
	//Get the string, remove spaces, and convert to capital letters
	private void gatherStrings() {
		
		//Set the originalText and keyText with the contents of the textField
		originalText =  textField.getText();
		keyText = keyField.getText();
		
		//Remove all spaces in the originalText and keyText (shouldn't need it)
		originalText = originalText.replaceAll("\\s+","");
		keyText = keyText.replaceAll("\\s+","");
		
		//Convert all characters to upper case characters for both strings
		originalText = originalText.toUpperCase();
		keyText = keyText.toUpperCase();
		
		
	}	
	
	//Run the Decrypt EK(m) = m - K mod 26
	String runDecrypt() {
		
		String DecryptText = "";
		
		 for (int i = 0, j = 0; i < originalText.length(); i++) {
	            char c = originalText.charAt(i);
	            DecryptText += (char)( ( c - keyText.charAt(j) + 130) % 26 + 65 );
	            j = ++j % keyText.length();		//Move up the key as needed 
	        }		
		
		return DecryptText;		
	}
	
	//Run the Encrypt Using EK(m) = m + K mod 26
	String runEncrypt() {
		
		String EncryptText = "";
		
		

		for (int i = 0, j = 0; i < originalText.length(); i++) {
            char c = originalText.charAt(i);
            EncryptText += (char)( ( c + keyText.charAt(j) - 130 ) % 26 + 65 );
            j = ++j % keyText.length();		//Move up the key as needed 
        }


		return EncryptText;		
	}
	
	//Push the result from the encryption or decryption to the result field
	private void pushResultString() {

		
		//Set the outputField to the encrypted or the decrypted text
		outputField.setText(resultText);		
		
		//Also display the time taken for this in nanoseconds
		String timeResult = String.valueOf(timeTaken);		
		timeField.setText(timeResult);
		
	}
	
	//Reset all the text fields to blanks
	private void resetAllFields() {
		
		outputField.setText(resetText);
		textField.setText(resetText);
		keyField.setText(resetText);
		timeField.setText(resetText);
		
	}
}
