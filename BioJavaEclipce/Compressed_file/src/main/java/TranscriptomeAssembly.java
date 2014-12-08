import java.util.ArrayList;
import java.util.Set;

import com.google.common.collect.HashMultimap;


public class TranscriptomeAssembly {
	
	private HashMultimap<Read, Transcript> readsToTranscripts;
	private HashMultimap<Transcript, Read> transcriptsToReads;
	private String nameOfTranscriptome;
	
	public TranscriptomeAssembly () {
		
	}
	
	public TranscriptomeAssembly (HashMultimap<Read, Transcript> readsToTranscripts,
			HashMultimap<Transcript, Read> transcriptsToReads,
			String nameOfTranscriptome) {
		
		this.readsToTranscripts = readsToTranscripts;
		this.transcriptsToReads = transcriptsToReads;
		this.nameOfTranscriptome = nameOfTranscriptome;
	}
	
	public ArrayList<Read> getReads (ArrayList<Transcript> transcripts){
		ArrayList<Read> reads = new ArrayList<Read>();
		
		for (Transcript index : transcripts)
			reads.addAll(transcriptsToReads.get(index));
		
		return reads;
	}
	
	public ArrayList<Read> getReadsOfTr (Transcript transcript){
		ArrayList<Read> reads = new ArrayList<Read>();
		
		reads.addAll(transcriptsToReads.get(transcript));
		
		return reads;
	}
	
	public Set<Transcript> getAllTranscripts() {
		return transcriptsToReads.keySet();
	}
	
	public HashMultimap<Read, Transcript> getMapRdTr () {
		return this.readsToTranscripts;
	}
	
	public HashMultimap<Transcript, Read> getMapTrRd () {
		return this.transcriptsToReads;
	}
	
	public String getNameOfTranscriptome (){
		return this.nameOfTranscriptome;
	}
	
}
