import com.google.common.collect.HashMultimap;


public class TranscriptomeAssembly {
	
	protected HashMultimap<Read, Transcript> readsToTranscripts;
	protected HashMultimap<Transcript, Read> transcriptsToReads;
	
	public TranscriptomeAssembly () {
		
	}
	
	public TranscriptomeAssembly (HashMultimap<Read, Transcript> readsToTranscripts,
			HashMultimap<Transcript, Read> transcriptsToReads) {
		
		this.readsToTranscripts = readsToTranscripts;
		this.transcriptsToReads = transcriptsToReads;
		
	}
	
	
}
