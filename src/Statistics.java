import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class Statistics {
	static JPanel statPane = new JPanel();
	
	public Statistics(){
		
	}
	
	private static String[] processData() {
		String[] statArray = null;
		
		return statArray;
	}
	
	public static JPanel display(){
		statPane.setLayout(new BoxLayout(statPane, BoxLayout.PAGE_AXIS));
		
		
		return statPane;
		
	}
}
