import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/****************************************************************************
 * FreqTone Class - For Pitch-by-frequency creation. 
 * 	-takes in frequency value, outputs sine-wave in binary
 * 	-sample rate: 16KHz (don't try to use values > 16,000 hz)
 *  -
 * 
 * @author christianadams
 *
 ***************************************************************************/
public class FreqTone {
	public static int SAMPLE_RATE = 16 * 1024; // ~16KHz
    public static int SECONDS = 2;
    private static byte[] sin;
    public static final AudioFormat af =
            new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, true);
    private static SourceDataLine line;
    
    /**
     * Takes in frequency, and calls the play method with a fixed value of 5 seconds
     * @param f
     * @throws LineUnavailableException
     */
    public FreqTone(int f) throws LineUnavailableException {
    	play(f, 5000);
    }
    
    public static void main(String[] args) throws LineUnavailableException {
    	
    	// Local Tester
    	FreqTone fr = new FreqTone(1200);
    }

    public void play(double f, int ms) throws LineUnavailableException {
    	sin = new byte[SECONDS * SAMPLE_RATE];
        line = AudioSystem.getSourceDataLine(af);
        line.open(af, Note.SAMPLE_RATE);
        line.start();

                for (int i = 0; i < sin.length; i++) {
                    double period = (double)SAMPLE_RATE / f;
                    double angle = 2.0 * Math.PI * i / period;
                    sin[i] = (byte)(Math.sin(angle) * 127f);              

                }
                System.out.println("This is the frequency: "+f);
                ms = Math.min(ms, Note.SECONDS * 1000);
                int length = Note.SAMPLE_RATE * ms / 1000;
                int count = line.write(sin, 0, length);
                
                line.drain();
                line.close();
    	}
    
    
  public byte[] data() {
	  return sin;
  	}
    
}
