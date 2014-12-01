import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import com.google.common.collect.HashMultimap;


public class ReadsMapper {
	
	public static TranscriptomeAssembly map (Reads setOfReads, 
			Transcriptome setOfTranscripts, 
			String fileOfDependencies,
			String fileOfReads,
			String fileOfTranscripts) throws Exception {
		
		String path = "/home/volta/another/test/Maps/";
		
		String transcriptomeName = fileOfTranscripts.split("\\/")[fileOfTranscripts.split("\\/").length - 1].split("\\.")[0];
    	
		String readsName = fileOfReads.split("\\/")[fileOfReads.split("\\/").length - 1].split("\\.")[0];
    	
		
		File file = new File(path + 
				transcriptomeName + 
				" + " + readsName + 
				" map");
		
		if (file.exists()){
			
			FileInputStream fis = new FileInputStream(path + 
					transcriptomeName + 
					" + " + readsName + 
					" map");
			ObjectInputStream in = new ObjectInputStream(fis);
			HashMultimap<Read, Transcript> rdtr = HashMultimap.create();
			HashMultimap<Transcript, Read> trrd = HashMultimap.create();
			
			rdtr = (HashMultimap<Read, Transcript>) in.readObject();
			trrd = (HashMultimap<Transcript, Read>) in.readObject();

			TranscriptomeAssembly map = new TranscriptomeAssembly(rdtr,trrd);
			in.close();
			return map;
		}
		
		String typeOfReads = fileOfReads.split("\\.")[fileOfReads.split("\\.").length - 1];
		HashMap<String, String> readsHashOfNames = Fasta.read(fileOfReads, typeOfReads, "names");
		
		String typeOfTranscripts = fileOfTranscripts.split("\\.")[fileOfReads.split("\\.").length - 1];
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
		
		System.out.println("Reads for transcript " + setOfTranscripts.getNameOfSet()
				+ " mapped successfully");
		
		TranscriptomeAssembly mapRdTr = new TranscriptomeAssembly(readsToTranscripts, transcriptsToReads);
		
		FileOutputStream fos = new FileOutputStream(path + 
				transcriptomeName + 
				" + " + readsName + 
				" map");
		ObjectOutputStream out = new ObjectOutputStream(fos);
		out.writeObject(mapRdTr.getMapRdTr());
		out.writeObject(mapRdTr.getMapTrRd());
		out.flush();
		out.close();
		
		return mapRdTr;
	}

}
