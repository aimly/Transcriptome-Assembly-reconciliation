import java.util.ArrayList;
import java.util.HashSet;

import com.google.common.collect.HashMultimap;


public class TranscriptomeAssembly {
	
	private HashMultimap<Read, Transcript> readsToTranscripts;
	private HashMultimap<Transcript, Read> transcriptsToReads;
	
	public TranscriptomeAssembly () {
		
	}
	
	public TranscriptomeAssembly (HashMultimap<Read, Transcript> readsToTranscripts,
			HashMultimap<Transcript, Read> transcriptsToReads) {
		
		this.readsToTranscripts = readsToTranscripts;
		this.transcriptsToReads = transcriptsToReads;
		
	}
	
	public ArrayList<Read> getReads (ArrayList<Transcript> transcripts){
		ArrayList<Read> reads = new ArrayList<Read>();
		
		for (Transcript index : transcripts)
			reads.addAll(transcriptsToReads.get(index));
		
		return reads;
	}
	
}
