import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Assignment {
	
	private String nameOfFirstTranscriptome, nameOfSecondTranscriptome;
	private int countOfTranscriptsInSet1, countOfTranscriptsInSet2;
	private HashMap<TranscriptPair, Double> bestSimilarities;
	
	public Assignment(SimilarityMatrix matrix) throws IOException, ClassNotFoundException{
		this.bestSimilarities = AssignmentSolver.solve(matrix);
		this.nameOfFirstTranscriptome = matrix.getFirstTranscriptome().getNameOfSet();
		this.nameOfSecondTranscriptome = matrix.getSecondTranscriptome().getNameOfSet();
		this.countOfTranscriptsInSet1 = matrix.getFirstTranscriptome().getAllSeq().size();
		this.countOfTranscriptsInSet2 = matrix.getSecondTranscriptome().getAllSeq().size();
	}
	
	public ArrayList<TranscriptPair> getUpper(double topBound){
		
		ArrayList<TranscriptPair> listOfUpperTranscripts = new ArrayList<TranscriptPair>();
		
		for (TranscriptPair index : bestSimilarities.keySet()){
			
			if (bestSimilarities.get(index) > topBound)
				listOfUpperTranscripts.add(index);
			
		}
		
		return listOfUpperTranscripts;
		
	}
	
	public List<TranscriptPair> getLower(double bottomBound){
		
		ArrayList<TranscriptPair> listOfLowerTranscripts = new ArrayList<TranscriptPair>();
		
		for (TranscriptPair index : bestSimilarities.keySet()){
			
			if (bestSimilarities.get(index) > bottomBound)
				listOfLowerTranscripts.add(index);
			
		}
		
		return listOfLowerTranscripts;
		
	}
	
	public void print(){
		System.out.println("Assignment of " + nameOfFirstTranscriptome + 
				" and " + nameOfSecondTranscriptome);
		for (TranscriptPair i : bestSimilarities.keySet()){
			i.print();
			System.out.println(bestSimilarities.get(i));
		}
		
	}
	
	public int getCountOfSet1 (){
		return this.countOfTranscriptsInSet1;
	}
	
	public int getCountOfSet2 (){
		return this.countOfTranscriptsInSet2;
	}
	
	public String getNameOfSet1(){
		return this.nameOfFirstTranscriptome;
	}
	
	public String getNameOfSet2(){
		return this.nameOfSecondTranscriptome;
	}
	
	public HashMap<TranscriptPair, Double> getMap () {
		return this.bestSimilarities;
	}
	
}
