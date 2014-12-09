import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import com.google.common.collect.HashMultimap;


public class ReadsMapper {
	
	public static TranscriptomeAssembly map (Reads setOfReads, 
			Transcriptome setOfTranscripts, 
			String fileOfDependencies,
			String fileOfReads,
			String fileOfTranscripts) throws Exception {
		
		HashMap<String, String> readsHashOfNames = Fasta.read(fileOfReads, "names");
		HashMap<String, String> transcriptHashOfNames = Fasta.read(fileOfTranscripts, "names");
		
		HashMultimap<Read, Transcript> readsToTranscripts = HashMultimap.create();
		HashMultimap<Transcript, Read> transcriptsToReads = HashMultimap.create();
		
		BufferedReader reader = new BufferedReader(new FileReader(fileOfDependencies));
		String ln;
		while ((ln = reader.readLine()) != null) {
			String[] values = ln.split("\t");
			if (values[2].length() < 2 ){
				continue;
			}
			Read rd = new Read (values[0], readsHashOfNames.get(values[0]));
			Transcript tr = new Transcript(values[2], transcriptHashOfNames.get(values[2]));
			readsToTranscripts.put(rd, tr);
			transcriptsToReads.put(tr, rd);
		}
		reader.close();
		
		TranscriptomeAssembly mapRdTr = 
				new TranscriptomeAssembly(readsToTranscripts, 
						transcriptsToReads,
						setOfTranscripts.getNameOfSet());
		
		return mapRdTr;
	}

}
