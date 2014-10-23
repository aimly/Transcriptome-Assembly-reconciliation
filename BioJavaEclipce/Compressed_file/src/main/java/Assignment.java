import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Assignment {
	
	protected HashMap<TranscriptPair, Double> bestSimilarities;
	
	public Assignment(SimilarityMatrix matrix){
		this.bestSimilarities = AssignmentSolver.solve(matrix);
	}
	
	public List<TranscriptPair> getUpper(double topBound){
		return null;
		
	}
	
	public List<TranscriptPair> getLower(double bottomBound){
		return null;
		
	}
	
	public void print(){
		
		System.out.println(bestSimilarities);
		
	}
	
}
