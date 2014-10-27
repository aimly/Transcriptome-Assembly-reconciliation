/**
 * Created by volta on 09.09.14.
 */


import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.HashBiMap;


public class Transcriptome {
	
	private String nameOfSet;
	private HashMap<String,String> transcripts;
    
    
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
    
}
