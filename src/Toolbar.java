import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;

public class Toolbar extends JToolBar implements ActionListener {
	// Setting the path to bookmarks
	public static String bookmarks = "Bookmarks.txt";

	private JPanel panel;
	private JButton backButton;
	private JButton homeButton;
	private JButton forwardButton;
	private JButton goButton;
	private JMenuItem exitButton;
	private JMenuItem clearhistory;
	private JButton refreshButton;
	private JButton bookmarkButton;
	private JTextField emptyTextField;
	private JLabel label;
	private Pane pane;
	private JMenuBar menuBar;
	private JMenu file;
	public static int currentIndex = 0;
	public static ArrayList<String> urlList = new ArrayList<String>();

	// Crearting the new objects

	public Toolbar(Pane pane) {

		super();
		panel = new JPanel();
		bookmarkButton = new JButton("Bookmark");
		bookmarkButton.addActionListener(this);
		backButton = new JButton("Back");
		backButton.setEnabled(false);
		backButton.addActionListener(this);
		forwardButton = new JButton("Forward");
		forwardButton.setEnabled(false);
		forwardButton.addActionListener(this);
		homeButton = new JButton("Home");
		homeButton.addActionListener(this);
		refreshButton = new JButton("\u21BB");
		refreshButton.addActionListener(this);
		label = new JLabel("URL:");
		emptyTextField = new JTextField("Homepage", 50);
		menuBar = new JMenuBar();
		file = new JMenu("File");
		exitButton = new JMenuItem("Exit");
		clearhistory = new JMenuItem("Clear history");
		clearhistory.addActionListener(this);
		exitButton.addActionListener(e -> System.exit(0));
		file.add(exitButton);
		file.add(clearhistory);
		menuBar.add(file);
		goButton = new JButton("Go");
		goButton.addActionListener(this);
		label.setLabelFor(emptyTextField);
		this.setLayout(new FlowLayout()); // Adding everything to the panel
		this.setFloatable(false);
		this.add(menuBar);
		this.add(backButton);
		this.add(forwardButton);
		this.add(homeButton);
		this.add(label);
		this.add(emptyTextField);
		this.add(goButton);
		this.add(refreshButton);
		this.add(bookmarkButton);
		this.pane = pane;

		// Adding keylistener to the "Entter" button from keyboard
		emptyTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					String url = emptyTextField.getText();

					try {

						currentIndex = currentIndex + 1;
						urlList.add(url);
						addHistory();
						backUpdate();
						forwardUpdate();
						pane.setPage(url);
					} catch (MalformedURLException e1) {
						JOptionPane.showMessageDialog(Toolbar.this, "Please use http://", "Error",
								JOptionPane.ERROR_MESSAGE);
					} catch (IOException e1) {

						e1.printStackTrace();
					}

				}
			}

		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// homebutton, setting the page from the url set in the homeurl.txt

		if (e.getSource() == homeButton) {

			try {

				String file_name = "H:/workspace/WebBrowser/homeurl.txt";
				FileReader file = new FileReader(file_name);
				BufferedReader textReader = new BufferedReader(file);
				String homeurl = textReader.readLine();
				textReader.close();
				pane.setPage(homeurl);
				emptyTextField.setText(homeurl);
				addHistory();
				urlList.add(homeurl);
				currentIndex++;
				backUpdate();
				forwardUpdate();
			} catch (MalformedURLException e1) {
				JOptionPane.showMessageDialog(Toolbar.this, "Inavlid URL", "Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e1) {

				e1.printStackTrace();
			}
		}

		// Setting the "Go" button
		if (e.getSource() == goButton) {

			String url = emptyTextField.getText();

			try {
				urlList.add(url);
				currentIndex++;
				addHistory();
				backUpdate();
				forwardUpdate();
				pane.setPage(url);
			} catch (MalformedURLException e1) {

				JOptionPane.showMessageDialog(Toolbar.this, "Please use http://", "Error", JOptionPane.ERROR_MESSAGE);

			} catch (IOException e1) {

				e1.printStackTrace();
			}
		}

		// Setting button for reloading the current page
		if (e.getSource() == refreshButton) {

			String url = emptyTextField.getText();

			try {
				emptyTextField.setText(urlList.get(currentIndex));
				if (urlList.get(currentIndex).equals(url)) {
					this.pane.setPage(url);
				} else {
					pane.setPage(urlList.get(currentIndex));
				}
			} catch (IOException e1) {

				e1.printStackTrace();
			}
		}

		// Setting back button, setting the page to the previous one

		if (e.getSource() == backButton) {
			currentIndex = currentIndex - 1;

			try {
				pane.setPage(urlList.get(currentIndex));
				emptyTextField.setText(urlList.get(currentIndex));
				addHistory();
				backUpdate();
				forwardUpdate();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
		}

		// Setting the forward button if backbutton is pressed

		if (e.getSource() == forwardButton) {
			currentIndex = currentIndex + 1;
			try {

				pane.setPage(urlList.get(currentIndex));
				emptyTextField.setText(urlList.get(currentIndex));
				addHistory();
				backUpdate();
				forwardUpdate();

			} catch (IOException e1) {

				e1.printStackTrace();
			}

		}
		// Button, when clicked adding the current url to a bookmarks.txt
		if (e.getSource() == bookmarkButton) {

			try {
				addBookmark();
			} catch (IOException e1) {

				e1.printStackTrace();
			}

		}

		if (e.getSource() == clearhistory) {
			try {
				clearHistory();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	

	// method, which adds current url to the bookmaks.txt
	public void addBookmark() throws IOException {

		String url = emptyTextField.getText();
		FileWriter file2 = new FileWriter(bookmarks, true);
		BufferedWriter textWriter = new BufferedWriter(file2);
		textWriter.write(url);
		textWriter.newLine();
		textWriter.close();
	}

	// method which adds every url, you entered, to pagehistoey.txt
	public void addHistory() throws IOException {
		String url = emptyTextField.getText();
		FileWriter file2 = new FileWriter(Browser.pagehistory, true);
		BufferedWriter textWriter = new BufferedWriter(file2);
		textWriter.write(url);
		textWriter.newLine();
		textWriter.close();
	}
	public void clearHistory() throws IOException {
		
		FileWriter file2 = new FileWriter(Browser.pagehistory, false);
		BufferedWriter textWriter = new BufferedWriter(file2);
	        textWriter.flush();
	        file2.close();
	        textWriter.close();
	        
	        JOptionPane.showMessageDialog(this, 
					"History has been cleared!", "History Clear", 
					JOptionPane.PLAIN_MESSAGE);
		
	}
	// Making checks if the back button and forward button should be enabled

	public void backUpdate() {
		if (currentIndex == 0) {

			getBackButton().setEnabled(false);
		} else {
			getBackButton().setEnabled(true);
		}
	}

	public void forwardUpdate() {
		if (currentIndex == urlList.size() - 1) {
			getForwardButton().setEnabled(false);
		} else {
			getForwardButton().setEnabled(true);
		}
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public JButton getHomeButton() {
		return homeButton;
	}

	public void setHomeButton(JButton homeButton) {
		this.homeButton = homeButton;
	}

	public JButton getForwardButton() {
		return forwardButton;
	}

	public void setForwardButton(JButton forwardButton) {
		this.forwardButton = forwardButton;
	}

	public JButton getGoButton() {
		return goButton;
	}

	public void setGoButton(JButton goButton) {
		this.goButton = goButton;
	}

	public JMenuItem getExitButton() {
		return exitButton;
	}

	public void setExitButton(JMenuItem exitButton) {
		this.exitButton = exitButton;
	}

	public JTextField getEmptyTextField() {
		return emptyTextField;
	}

	public void setEmptyTextField(JTextField emptyTextField) {
		this.emptyTextField = emptyTextField;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public JButton getBackButton() {
		return backButton;
	}

	public JButton getRefreshButton() {
		return refreshButton;
	}

	public void setRefreshButton(JButton refreshButton) {
		this.refreshButton = refreshButton;
	}

	public void setBackButton(JButton backButton) {
		this.backButton = backButton;
	}

}
