import java.io.IOException;

/**
 * Created by volta on 09.09.14.
 */

public class Transcript extends NucleicSequence {

	// From parent class (because we can't cast classes)

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Transcript(NucleicSequence obj) throws IOException {
		super (obj);
	}

	public Transcript() {
		// TODO Auto-generated constructor stub
	}
	

	public Transcript(String string, String string2) {
		super(string, string2);
	}
	
	
	
}
