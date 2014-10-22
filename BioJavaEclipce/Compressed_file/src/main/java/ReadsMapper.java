import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.common.collect.HashMultimap;


public class ReadsMapper {
	
	public static TranscriptomeAssembly map (Reads setOfReads, 
			Transcriptome setOfTranscripts, 
			String fileOfDependencies) throws IOException {
		
		HashMultimap<Read, Transcript> readsToTranscripts = HashMultimap.create();
		HashMultimap<Transcript, Read> transcriptsToReads = HashMultimap.create();
		
		BufferedReader reader = new BufferedReader(new FileReader(fileOfDependencies));
		String ln;
//		int i = 0;
		while ((ln = reader.readLine()) != null) {
			String[] values = ln.split("\t");
			if (values[2].length() < 2 ){
				continue;
			}
			Read rd = new Read (values[0], setOfReads.getSeq(values[0]));
			Transcript tr = new Transcript(values[2], setOfTranscripts.getSeq(values[2]));
			readsToTranscripts.put(rd, tr);
			transcriptsToReads.put(tr, rd);
//			i++;
		}
		reader.close();
		
		return new TranscriptomeAssembly(readsToTranscripts, transcriptsToReads);
	}

}
