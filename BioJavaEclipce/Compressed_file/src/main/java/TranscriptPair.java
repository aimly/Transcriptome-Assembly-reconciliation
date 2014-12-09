

public class TranscriptPair {
	
	private Transcript firstTranscript, secondTranscript;
	
	public TranscriptPair (Transcript first, Transcript second){
		this.firstTranscript = first;
		this.secondTranscript = second;
	}
	
	public Transcript getTranscript1 (){
			return this.firstTranscript;
	}
	
	public Transcript getTranscript2 (){
		return this.firstTranscript;
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
