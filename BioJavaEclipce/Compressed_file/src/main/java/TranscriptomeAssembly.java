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
	
	
}
