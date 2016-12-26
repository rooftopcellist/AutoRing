import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
/**
 * 
 * @author christianadams
 *
 */
public class NoteComponent extends JButton {
	JButton noteButton;
	private static int f;
	
	/**************************************************************************
	 * NoteComponent makes Pitch Buttons which will later be added to the panel.
	 * 	-Called From the Game Panel
	 * 	-Includes action listeners on each button, which check if the answer is
	 * 		right or wrong
	 * 
	 *  @author christianadams
	 *  
	 *************************************************************************/
	public NoteComponent(int f) {
		this.f = f;
		
		String fString = Integer.toString(f);
		System.out.println(fString);
		setText(fString);
		
        setMnemonic(MouseEvent.MOUSE_CLICKED);
        addActionListener(new ActionListener() { 
        	  public void actionPerformed(ActionEvent e) { 
        		  	System.out.print("You Guessed "+f+" Hz.\n");
        		  	if (f == MenuFrame.playFreq) {
        		  		System.out.println("CORRECT!");
        		  		MenuFrame.correct += 1;
        		  		MenuFrame.statsOut.setText("Statistics:  # Correct: "+MenuFrame.correct+"   # Wrong: "+MenuFrame.incorrect);
        		  		MenuFrame.playFreq = 0;
        		  		System.out.println("This is the playFreq: "+MenuFrame.playFreq);
        		  	
        		  	}
        		  	else if (MenuFrame.playFreq == 0) {
        		  		//Does not change the stats at all.  
        		  		MenuFrame.playFreq = 0;
        		  	}
        		  	else {
        		  		System.out.println("INCORRECT, TRY AGAIN");
        		  		MenuFrame.incorrect += 1;
        		  		MenuFrame.statsOut.setText("Statistics:  # Correct: "+MenuFrame.correct+"   # Wrong: "+MenuFrame.incorrect);
        		  		System.out.println("This is the playFreq: "+MenuFrame.playFreq);
        		  	}
        		  	
        		  	
        	  }
        	  });
	}
}
