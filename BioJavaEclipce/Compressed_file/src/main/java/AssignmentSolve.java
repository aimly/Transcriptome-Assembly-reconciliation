import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;


public class AssignmentSolve {

	public static HashMap<TranscriptPair, Double> solve (SimilarityMatrix matrix) 
			throws IOException{
		double[][] sim = matrix.getMatrix();
		if (sim.length < sim[0].length)
			sim = AssignmentSolve.transpose(sim);
		double[][] reverseMatrix = AssignmentSolve.reverse(sim);
		Transcriptome tr1 = AssignmentSolve.
				getMax(matrix.getFirstTranscriptome(),
						matrix.getSecondTranscriptome());
		Transcriptome tr2 = AssignmentSolve.
				getMin(matrix.getFirstTranscriptome(),
						matrix.getSecondTranscriptome());
		String[] set1 = AssignmentSolve.toArr(tr1);
		String[] set2 = AssignmentSolve.toArr(tr2);
		int[] solution = new int[sim.length];
		
		HungarianAlgorithm solver = new HungarianAlgorithm(reverseMatrix);
		solution = solver.execute();
		
		HashMap<TranscriptPair, Double> compliances = 
				new HashMap<TranscriptPair, Double>();
		int i = 0;
		
		System.out.println("set1 :" + set1.length);
		System.out.println("set2 :" + set2.length);
		System.out.println("tr1 :" + tr1.getAllSeq().size());
		System.out.println("tr2 :" + tr2.getAllSeq().size());
		System.out.println("solu :" + solution.length);
		System.out.println("sim numLin :" + sim.length);
		System.out.println("sim numCol :" + sim[0].length);
		
		for (String index : set1){
			Transcript t1 = new Transcript(tr1.getName(index), 
					index);
			Transcript t2;
			Double similarity;
			if(solution[i] == -1){
				t2 = new Transcript();
				similarity = 0.0;
			}
			else {
				t2 = new Transcript( tr2.getName(set2[solution[i]]),
						set2[solution[i]]);
				similarity = new Double(sim[i][solution[i]]);
			}
			TranscriptPair pair = new TranscriptPair(t1, t2);
			compliances.put(pair, similarity);
			i++;
		}
		return compliances;
	}
	

	private static double[][] transpose(double[][] sim) {
		double[][] simTransp = new double[sim[0].length][sim.length];
		
		for (int i = 0; i < sim.length; i++)
			for (int j = 0; j < sim[0].length; j++)
				simTransp[j][i] = sim[i][j];
		
		return simTransp;
	}


	private static double[][] reverse (double[][] matrix){
		double[][] reverseMatrix = new double[matrix.length]
				[matrix[0].length];
		
		for (int t = 0; t < reverseMatrix.length; t++)
			for (int l = 0; l < reverseMatrix[0].length; l++)
				reverseMatrix[t][l] = -matrix[t][l];
		
		return reverseMatrix;
	}
	
	private static String[] toArr (Transcriptome transcriptome){
		Collection<String> set = transcriptome.getAllSeq();
		String[] tr = set.toArray(new String[set.size()]);
		return tr;
	}
	
	private static Transcriptome getMax(Transcriptome tr1, 
			Transcriptome tr2){
		if (tr1.getAllSeq().size()>=tr2.getAllSeq().size()){
			return tr1;
		} else {
			return tr2;
		}
	}

	private static Transcriptome getMin(Transcriptome tr1, 
			Transcriptome tr2) {
		if (tr1.getAllSeq().size()>=tr2.getAllSeq().size()){
			return tr2;
		} else {
			return tr1;
		}
	}
	
}
