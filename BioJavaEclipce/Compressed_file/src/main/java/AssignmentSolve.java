import java.io.IOException;
import java.util.HashMap;


public class AssignmentSolve {

	public static HashMap<TranscriptPair, Double> solve (SimilarityMatrix matrix) 
			throws IOException{
		double[][] sim = matrix.getMatrix();
		Transcriptome tr1 = matrix.getFirstTranscriptome();
		Transcriptome tr2 = matrix.getSecondTranscriptome();
		double[][] reverseMatrix = new double[sim.length]
				[sim[0].length];
		
		int[] solution = new int[sim.length];
		
		HungarianAlgorithm solver = new HungarianAlgorithm(reverseMatrix);
		solution = solver.execute();
		
		HashMap<TranscriptPair, Double> compliances = 
				new HashMap<TranscriptPair, Double>();
		
		for (String index : tr1.getAllSeq()){
			
		}
		
		return null;
	}
	
}
