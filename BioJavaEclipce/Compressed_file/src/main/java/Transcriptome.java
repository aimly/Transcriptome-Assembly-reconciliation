/**
 * Created by volta on 09.09.14.
 */


import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.HashBiMap;


public class Transcriptome {

    protected HashBiMap<String,String> transcripts;
    
    public Transcriptome (HashBiMap<String, String> setOfTr) throws IOException {
    	this.transcripts = setOfTr;
    }
	
	public Transcript getTranscript (String transcriptName) {
    	return null;
    }
	
	public void print () {
		System.out.println("Set of transcripts /n");
		int i = 1;
		
		for (Map.Entry<String,String> item : transcripts.entrySet()){
			System.out.println("Transcript №" + i);
			Transcript tr = new Transcript (item.getKey(), item.getValue());
			tr.print();
			i++;
		}
	}
	
	public String getSeq (String name){
		return transcripts.get(name);
	}
	
	public Collection<String> getAllSeq(){
		return transcripts.values();
	}
	
	
    
}
