import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;


public class Browser extends JFrame  {
	
// Path to the home page
	
public static String pagehistory = "History.txt";
//Constructor for creating JFrame
	public Browser(){
		super();
		
		this.setSize(1300, 700);
		this.setLocation(450, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);

	
		
	}
	
	public static void main(String[] args) throws Exception {
		
		//Setting the homepage, when opening the browser
		
		String file_name = "homeurl.txt";
		FileReader file = new FileReader( file_name );
		BufferedReader textReader = new BufferedReader(file);
		String homeurl = textReader.readLine();
		textReader.close();
		Browser a = new Browser();
		Pane pane = new Pane(homeurl);	
		Toolbar bar = new Toolbar(pane);
		pane.setBar(bar);
		//Adding the toolbar to the frame
		a.add(bar, BorderLayout.NORTH);
		//Adding the scrollpane to the frame
		a.add(new JScrollPane(pane), BorderLayout.CENTER);
		//adding the homepage to the arraylist
		Toolbar.urlList.add(homeurl);
		//Checking the back and forward button
		bar.backUpdate();
		bar.forwardUpdate();
		
	
	}





	

}