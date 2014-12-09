/**
 * Created by volta on 09.09.14.
 */


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class Transcriptome implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nameOfSet;
	private HashMap<String,String> transcripts;
    
	public Transcriptome (String name){
		this.nameOfSet = name;
		this.transcripts = new HashMap<String, String>();
	}
	
    public Transcriptome (HashMap<String, String> setOfTr, String nameOfSet) throws IOException {
    	this.transcripts = setOfTr;
    	this.nameOfSet = nameOfSet;
    }
	
	public Transcript getTranscript (String transcriptName) {
    	return null;
    }
	
	public void print () {
		System.out.println("Set of transcripts /n");
		int i = 1;
		
		for (Map.Entry<String,String> item : transcripts.entrySet()){
			System.out.println("Transcript №" + i);
			Transcript tr = new Transcript (item.getValue(), item.getKey());
			tr.print();
			i++;
		}
	}
	
	public String getName (String seq){
		return transcripts.get(seq);
	}
	
	public Collection<String> getAllSeq(){
		return transcripts.keySet();
	}
	
	public String getNameOfSet () {
		return this.nameOfSet;
	}
	
	public void addTranscript (Transcript transcript) throws IOException{
		transcripts.put(transcript.getData(), 
				transcript.getName());
	}
	
	public void addAllTranscript (ArrayList<Transcript> trSet) throws IOException{
		if (trSet.isEmpty())
			System.out.println("Can't add transcripts: array is empty");
		else
			for (Transcript transcript : trSet)
				transcripts.put(transcript.getData(), 
						transcript.getName());
	}
	
	public void merge (Transcriptome tr2){
		for (String seq : tr2.getAllSeq())
			this.transcripts.put(seq, tr2.getName(seq));
		}
	
	public void setTranscriptomeName(String name){
		this.nameOfSet = name;
	}
    
}
