import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class Pane extends JEditorPane implements HyperlinkListener{
	
	Toolbar bar;
	
	public Toolbar getBar(){
		return bar;
	}
	
	public void setBar(Toolbar bar){
		this.bar = bar;
	}
	
	//Setting the JEditorPane settings
	public Pane(String homeurl) throws IOException{
		super(homeurl);
		
		this.setEditable(false);
		this.addHyperlinkListener(this);
	}
	
	//Making the hyperlinks work
	
	@Override
	public void hyperlinkUpdate(HyperlinkEvent r) {	
		
		try{
			
		 if(r.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
			 
			 this.setPage(r.getURL());
         		
			 	Toolbar.currentIndex++;
         		
				Toolbar.urlList.add(r.getURL().toString());
				this.bar.getEmptyTextField().setText(r.getURL().toString());
				bar.addHistory();
				bar.backUpdate();
				bar.forwardUpdate();
				
		 }
		} catch(Exception r1){
		 
		 
             }
		}
      		
             
		
	}


