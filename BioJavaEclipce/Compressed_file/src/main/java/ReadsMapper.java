import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import com.google.common.collect.HashMultimap;


public class ReadsMapper {
	
	public static TranscriptomeAssembly map (Reads setOfReads, 
			Transcriptome setOfTranscripts, 
			String fileOfDependencies,
			String fileOfReads,
			String fileOfTranscripts) throws Exception {
		
		String typeOfReads = fileOfReads.split(".")[fileOfReads.split(".").length - 1];
		HashMap<String, String> readsHashOfNames = Fasta.read(fileOfReads, typeOfReads, "names");
		
		String typeOfTranscripts = fileOfReads.split(".")[fileOfReads.split(".").length - 1];
		HashMap<String, String> transcriptHashOfNames = Fasta.read(fileOfTranscripts, typeOfTranscripts, "names");
		
		
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
			Read rd = new Read (values[0], readsHashOfNames.get(values[0]));
			Transcript tr = new Transcript(values[2], transcriptHashOfNames.get(values[2]));
			readsToTranscripts.put(rd, tr);
			transcriptsToReads.put(tr, rd);
//			i++;
		}
		reader.close();
		
		return new TranscriptomeAssembly(readsToTranscripts, transcriptsToReads);
	}

}
