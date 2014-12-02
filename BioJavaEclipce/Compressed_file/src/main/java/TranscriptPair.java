

public class TranscriptPair {
	
	private Transcript firstTranscript, secondTranscript;
	
	public TranscriptPair (Transcript first, Transcript second){
		this.firstTranscript = first;
		this.secondTranscript = second;
	}
	
	public Transcript getTranscript (Integer i){
		if (i == 0)
			return this.firstTranscript;
		if (i == 1)
			return this.secondTranscript;
		
		System.out.println("Can not perfom. Please, use index 0 or 1");
		return null;
	}
	
	public void print () {
		System.out.println("Transcript pair:");
		System.out.println("First transcript");
		this.firstTranscript.print();
		System.out.println("Second Transcript");
		if (this.secondTranscript != null) 
			this.secondTranscript.print();
		else 
			System.out.println("No second transcript");
	}
	
	
	
}
