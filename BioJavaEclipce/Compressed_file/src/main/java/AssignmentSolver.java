import java.util.HashMap;
import java.util.Iterator;

import com.google.common.collect.BiMap;


public class AssignmentSolver {
	
	public static HashMap<TranscriptPair, Double> solve (SimilarityMatrix matrix){
		
		HashMap<TranscriptPair, Double> compliances = 
				new HashMap<TranscriptPair, Double>();
		
		HungarianAlgorithm solver = new HungarianAlgorithm(matrix.sim);
		int[] solution = solver.execute();
		
		BiMap<String, String> nameToStr1 = matrix.tr1.transcripts.inverse();
		BiMap<String, String> nameToStr2 = matrix.tr2.transcripts.inverse();
		
		
		String[] arr1 = new String[matrix.tr1.getAllSeq().size()];
		String[] arr2 = new String[matrix.tr2.getAllSeq().size()];
		
		Iterator<String> i1 = matrix.tr1.getAllSeq().iterator();
		Iterator<String> i2 = matrix.tr2.getAllSeq().iterator();
		int i = 0;
		while (i1.hasNext()){
			arr1[i] = i1.next();
			i++;
		}
		i = 0;
		while (i2.hasNext()){
			arr2[i] = i2.next();
			i++;
		}
		
		
		/*
		 * 
		 *  
		 * 	
		 */
		
		for (i = 0; i < solution.length; i++){
			
			Transcript t1 = new Transcript(nameToStr1.get(arr1[i]), 
					arr1[i]);
			
			if(solution[i] == -1){
				Transcript t2 = null;
				Double similarity = 0.0;
				TranscriptPair pair = new TranscriptPair(t1, t2);
				compliances.put(pair, similarity);
			}
			else {
				Transcript t2 = new Transcript(nameToStr2.get(arr2[solution[i]]), 
						arr2[solution[i]]);
				Double similarity = new Double(matrix.sim[i][solution[i]]);
				TranscriptPair pair = new TranscriptPair(t1, t2);
				compliances.put(pair, similarity);
			}
			
		}
		
		return compliances;
		
	}
	
}
