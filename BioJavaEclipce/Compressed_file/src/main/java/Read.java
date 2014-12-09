import java.io.IOException;

/**
 * Created by volta on 09.09.14.
 */


public class Read extends NucleicSequence {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// From parent class (because we can't cast classes)
	public Read(NucleicSequence obj) throws IOException {
		super (obj);
	}

	public Read() {
		// TODO Auto-generated constructor stub
	}
	
	public Read(String var1, String var2){
		super (var1, var2);
	}
	
}
