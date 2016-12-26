import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

/**
 * 
 * @author christianadams
 *
 *The MenuFrame class is the main class which initiates the program.  It creates a Frame with menu options and the GamePanel,
 *Statistics, Play/Reset, and Border Panels (presented in BorderLayout)
 *
 *The Tone class creates the sine wave when called by the Play Button.  The NoteComponent class creates buttons with all of 
 *the possible frequency options on each and populates the GamePanel with them.  
 */

public class MenuFrame extends JFrame implements ActionListener {
	public static MenuFrame mainFrame = new MenuFrame();
	JMenu menu, submenu;
	JMenuItem menuItem;
	public static int[] easyFreqArray = new int[] { 160, 250, 500, 800, 4000, 6000 };
	public static int[] medFreqArray = { 100, 160, 250, 400, 630, 1000, 2500, 4000, 6000, 8000 };
	public static int[] hardFreqArray = { 60, 80, 100, 125, 160, 200, 250, 300, 400, 500, 630, 700, 800, 1000, 1200,
			1600, 2000, 2500, 3000, 4000, 5000, 6000, 7100, 8000, 10000, 12500, 16000, 18000 };
	public static int diff = 1;
	static GamePanel panel = new GamePanel(easyFreqArray);
	static Random rand = new Random();
	static JTextArea statsOut = new JTextArea();
	static int ansIndex;
	public static int playFreq;
	public static int correct = 0;
	public static int incorrect = 0;

/**
 * MenuFrame extends JFrame to add menu options and formatting information to the class
 */
	
	public MenuFrame() {

		setTitle("Auto Ring");
		setSize(150, 150);

		// Creates a menubar for a JFrame
		JMenuBar menuBar = new JMenuBar();

		// Add the menubar to the frame
		setJMenuBar(menuBar);

		// Add exit option
		menu = new JMenu("Exit");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription("Clicking this option ends the game");
		menuItem = new JMenuItem("Exit Program", KeyEvent.VK_ESCAPE);
		menuItem.setActionCommand("exit");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		JMenuItem sMenuItem = new JMenuItem("Save");
		sMenuItem.setMnemonic(KeyEvent.VK_S);
		sMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		sMenuItem.setToolTipText("Save Image");
		sMenuItem.addActionListener((ActionEvent event) -> {
			// saves current contrast-image to the Images folder
			System.out.println("This is where save would have gone");

			JFrame f = new JFrame();
			f.setSize(500, 500);
			f.setTitle("Save Image");
			f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			//
			// // When blank button is pressed change the button text to also
			// display the question
			TextField enterFname = new TextField("Your Name: ", 40);
			enterFname.setEnabled(true);

			enterFname.addKeyListener(new KeyListener() {

				@Override
				public void keyPressed(KeyEvent e) {
					int keyCode = (((KeyEvent) e)).getKeyCode();
					if ((keyCode == KeyEvent.VK_ENTER)) {
						String saveName = enterFname.getText(); // Stores String
																// from
																// enterAnswer
																// text
						//Makes Stats Folder in current directory
						File dir = new File("STATS");
						// attempt to create the directory here
						System.out.println("Making STATS Folder");
						boolean successful = dir.mkdir();
						if (successful) {
							// creating the directory succeeded
							System.out.println("directory was created successfully");
						} else {
							// creating the directory failed
							System.out.println("STATS folder already Exists");
						}

						//creates text file in STATS folder
						File statsFile = new File("STATS//Statistics.txt");

						//Writes statistics to file with highscore name
	    		    	 BufferedWriter writer = null;
	    		         try {
	    		             //create a temporary file
	    		             String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	    		             File logFile = new File("STATS//Statistics.txt");

	    		             // This will output the full path where the file will be written to...
	    		             System.out.println(logFile.getCanonicalPath());

	    		             writer = new BufferedWriter(new FileWriter(logFile,true));
	    		             writer.write("Player: "+saveName+" guessed "+correct+", and "+incorrect+".\n");
	    		         } catch (Exception e1) {
	    		             e1.printStackTrace();
	    		         } finally {
	    		             try {
	    		                 // Close the writer regardless of what happens...
	    		                 writer.close();
	    		             } catch (Exception e1) {
		    		    		 System.out.println("Write error for " + statsFile.getPath() +
		    		                     ": " + e1.getMessage()+"\n");	
	    		             }
	    		         }
						// get Statics text and save to file HERE!
						System.out.println(saveName
								+ "'s Statistics for this game were saved to the Highscore page in the Statistics Folder");

						
						f.setVisible(false);
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub

				}

			});
			f.add(new Label("Save: "));
			f.add(enterFname);
			f.pack();
			f.setVisible(true);
		});

		menu.add(sMenuItem);

		menuBar.add(menu);

		JMenu menu2 = new JMenu("Modes");
		JMenuItem MenuItem = new JMenuItem("Trainer");
		MenuItem.setMnemonic(KeyEvent.VK_1);
		MenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		MenuItem.addActionListener((ActionEvent event) -> {
			mainFrame.remove(panel);
			System.out.println("You are in Trainer Mode");
		});
		menu2.add(MenuItem);
		menuBar.add(menu2);

		JMenu menu3 = new JMenu("Difficulty");
		MenuItem = new JMenuItem("Easy");
		MenuItem.setMnemonic(KeyEvent.VK_1);
		MenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		MenuItem.setToolTipText("Level 1: Easy");
		MenuItem.addActionListener((ActionEvent event) -> {
			// Changes 'diff' value to 1, revalidates and repaints mainFrame
			diff = 1;
			mainFrame.remove(panel);
			panel = new GamePanel(easyFreqArray);
			mainFrame.add(panel, BorderLayout.CENTER);
			mainFrame.revalidate();
			mainFrame.repaint();
			System.out.println("Here we are in Easy");
		});
		menu3.add(MenuItem);

		MenuItem = new JMenuItem("Medium");
		MenuItem.setMnemonic(KeyEvent.VK_2);
		MenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		MenuItem.setToolTipText("Level 2: Medium");
		MenuItem.addActionListener((ActionEvent event) -> {
			// Changes 'diff' value to 2, revalidates and repaints mainFrame
			diff = 2;
			mainFrame.remove(panel);
			panel = new GamePanel(medFreqArray);
			mainFrame.add(panel, BorderLayout.CENTER);
			mainFrame.revalidate();
			mainFrame.repaint();
			System.out.println("Here we are in Medium");
		});
		menu3.add(MenuItem);

		MenuItem = new JMenuItem("Hard");
		MenuItem.setMnemonic(KeyEvent.VK_3);
		MenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK));
		MenuItem.setToolTipText("Level 3: Hard");
		MenuItem.addActionListener((ActionEvent event) -> {
			// Changes 'diff' value to 3, revalidates and repaints mainFrame
			diff = 3;
			mainFrame.remove(panel);

			panel = new GamePanel(hardFreqArray);
			mainFrame.add(panel, BorderLayout.CENTER);
			mainFrame.revalidate();
			mainFrame.repaint();
			System.out.println("Here we are in Hard");
		});
		menu3.add(MenuItem);

		menuBar.add(menu3);
		
		JMenu menu5 = new JMenu("Reset");
		MenuItem = new JMenuItem("Reset Score");
		MenuItem.setMnemonic(KeyEvent.VK_3);
		MenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK));
		MenuItem.setToolTipText("Reset Score");
		MenuItem.addActionListener((ActionEvent event) -> {
			// Resets score to zero
			correct = 0;
			incorrect = 0;
			MenuFrame.statsOut.setText("Statistics:  # Correct: "+MenuFrame.correct+"   # Wrong: "+MenuFrame.incorrect);
			System.out.println("Score was Reset");
		});
		menu5.add(MenuItem);

		menuBar.add(menu5);
		

		JMenu menu4 = new JMenu("Help");
		JMenuItem menuItem = new JMenuItem("How To Guide");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.ALT_MASK));
		menuItem.setToolTipText("Click here for a Help Guide.");
		menuItem.addActionListener((ActionEvent event) -> {
			// saves current contrast-image to the Images folder
			System.out.println("This is the Help Manual.  Super helpful right?");
			// saves current contrast-image to the Images folder

			//New pop-up frame for help info
			JFrame f = new JFrame();
			f.setSize(500, 300);
			f.setTitle("AutoRing Tutorial");
			f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

			//This outputs text for manual on a new frame (created above /\)
			JTextArea output = new JTextArea();
			output.append(" \n\n  Here's how to get started quickly with AutoRing!\n\n");
			output.append("     When in the 'Train' mode, click the 'PLAY' button and listen to the tone.\n");
			output.append("  Guess what tone it is by clicking on one of the buttons in the middle of \n");
			output.append("  the screen.  Your guess statistics will be displayed at the bottom of the \n");
			output.append("  window.  When you feel like you have mastered the easy level, change your \n");
			output.append("  difficulty level using the drop-down menu.\n\n");
			output.append("     You can save you statistics at any point to a text file by clicking File->Save\n");
			output.append("  Also, remember that you can exit the program at anytime using the shortcut \n");
			output.append("  Alt+ESC, or by selecting Exit Program from the File menu.");

			// // When blank button is pressed change the button text to also
			// display the question
			f.add(output);
			f.setVisible(true);
		});
		menu4.add(menuItem);

		menuBar.add(menu4);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());
		if (e.getActionCommand() == "exit") {
			System.out.println("Goodbye!");
			System.exit(0);
		}
	}

	public static void main(String[] args) throws IOException, LineUnavailableException {

		EventQueue.invokeLater(() -> {
			mainFrame.setSize(560, 375);
			mainFrame.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			mainFrame.setVisible(true);
			// mainFrame.setLayout(new GridLayout(2,2));

			mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

			//Creates Play button which chooses current array (diff) and plays a random frequency from it.  
			JButton play = new JButton("PLAY");
			play.setPreferredSize(new Dimension(75, 86));
			play.setMnemonic(MouseEvent.MOUSE_CLICKED);
			play.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						// will choose a random number within the lenth of the
						// current array
						// and thus randomly generate a pitch.
						if (diff == 1) {
							ansIndex = rand.nextInt(easyFreqArray.length);
							new FreqTone(easyFreqArray[ansIndex]);
							playFreq = easyFreqArray[ansIndex];
						} else if (diff == 2) {
							int ansIndex = rand.nextInt(medFreqArray.length);
							new FreqTone(medFreqArray[ansIndex]);
							playFreq = medFreqArray[ansIndex];
						} else if (diff == 3) {
							int ansIndex = rand.nextInt(hardFreqArray.length);
							new FreqTone(hardFreqArray[ansIndex]);
							playFreq = hardFreqArray[ansIndex];
						} else {
							System.out.println("You have reached this in Error");
						}
					} catch (LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			});
			
			//Creates a reset button which resets the stats to 0
			JButton reset = new JButton("RESET");
			reset.setPreferredSize(new Dimension(75, 86));
			reset.setMnemonic(MouseEvent.MOUSE_CLICKED);
			reset.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e3) {
					// Resets score to zero
					correct = 0;
					incorrect = 0;
					MenuFrame.statsOut.setText("Statistics:  # Correct: "+MenuFrame.correct+"   # Wrong: "+MenuFrame.incorrect);
					System.out.println("Score was Reset");
				}	
				});
			
			//Creates a nested panel to hold play and reset buttons.  
			JPanel leftPanel = new JPanel(new BorderLayout());
			leftPanel.add(play,BorderLayout.NORTH);
			leftPanel.add(reset,BorderLayout.SOUTH);
			mainFrame.add(leftPanel, BorderLayout.WEST);
			//Formats game panel and displays current NoteComponents
			panel.setLayout(new GridLayout(1, 1));
			if (diff == 1) {
				panel = new GamePanel(easyFreqArray);
			} else if (diff == 2) {
				panel = new GamePanel(medFreqArray);
			} else if (diff == 3) {
				panel = new GamePanel(hardFreqArray);
			} else {
				System.out.println("You have reached this in Error");
			}

			mainFrame.add(panel, BorderLayout.CENTER);

			JPanel topPane = new JPanel();
			JPanel bottomPane = new JPanel();
			JPanel statsPane = new JPanel();
			// JLabel stats = new JLabel("Stats Stats Stats");
			statsOut = new JTextArea("Statistics:  # Correct: " + correct + "   # Wrong: " + incorrect + "   ");
			statsPane.add(statsOut);
			bottomPane.add(statsPane, BorderLayout.SOUTH);

			 //Reads in image and displays it in bottom right corner
			 JPanel iconPane = new JPanel();
			
			 BufferedImage image = null;
			 try
			 {
			 image = ImageIO.read(new File("topBorder.jpg"));
			 }
			 catch (Exception e)
			 {
			 e.printStackTrace();
			 System.exit(1);
			 }
			 ImageIcon imageIcon = new ImageIcon(image);
			 JLabel jLabel = new JLabel();
			 jLabel.setIcon(imageIcon);
//			 iconPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			 iconPane.add(jLabel, BorderLayout.NORTH);
			 topPane.add(iconPane);
			 topPane.setAlignmentX(TOP_ALIGNMENT);

			mainFrame.add(topPane, BorderLayout.NORTH);
			mainFrame.add(bottomPane, BorderLayout.SOUTH);

		});

	}
}