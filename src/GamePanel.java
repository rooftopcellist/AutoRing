import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

	public NoteComponent noteComp;

	
	
	public GamePanel(int[] fArray) {
		setName("Game Panel");
		setLayout(new FlowLayout());
		for(int f : fArray){
			add(new NoteComponent(f));
			
		};
		
	}	
	
	public static void main(String[] args) {
//		GamePanel(easyFreqArray);
	}

}
