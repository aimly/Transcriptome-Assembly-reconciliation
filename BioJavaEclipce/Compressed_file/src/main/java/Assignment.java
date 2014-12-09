import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class Assignment {
	
	private String nameOfFirstTranscriptome, nameOfSecondTranscriptome;
	private int countOfTranscriptsInSet1, countOfTranscriptsInSet2;
	private HashMap<TranscriptPair, Double> bestSimilarities;
	
	
	public Assignment(Transcriptome tr1, 
			Transcriptome tr2, 
			TranscriptSimilarityComputer nw,
			WorkWithFiles workWithFiles) throws Exception {
		if (tr1 != null && tr2 != null){
		
			SimilarityMatrix matrix = new SimilarityMatrix(tr1, tr2, nw,workWithFiles);
			
			this.bestSimilarities = 
					AssignmentSolve.solve(matrix);
			this.nameOfFirstTranscriptome = 
					matrix.getFirstTranscriptome().getNameOfSet();
			this.nameOfSecondTranscriptome = 
					matrix.getSecondTranscriptome().getNameOfSet();
			this.countOfTranscriptsInSet1 = 
					matrix.getFirstTranscriptome().getAllSeq().size();
			this.countOfTranscriptsInSet2 = 
					matrix.getSecondTranscriptome().getAllSeq().size();
		}
	}
	
	public ArrayList<Transcript> getUpper(double topBound, 
			String transcriptome) throws IOException{
		
		if (!transcriptome.equals(nameOfFirstTranscriptome) 
				&& !transcriptome.equals(nameOfSecondTranscriptome)){
			System.out.println("Fail in getUpper method of class Assignment!");
			return null;
		}
		
		ArrayList<Transcript> listOfUpperTranscripts = new ArrayList<Transcript>();
		
		for (TranscriptPair index : bestSimilarities.keySet()){
			
			if (bestSimilarities.get(index) > topBound){
				if (transcriptome.equals(this.nameOfFirstTranscriptome) 
						&& index.getTranscript1() != null)
					listOfUpperTranscripts.add(index.getTranscript1());
				else 
					if (index.getTranscript2() != null)
						listOfUpperTranscripts.add(index.getTranscript2());
			}
			
		}
		
		return listOfUpperTranscripts;
		
	}
	
	public ArrayList<Transcript> getLower(double bottomBound, 
			String transcriptome){
		
		ArrayList<Transcript> listOfLowerTranscripts = new ArrayList<Transcript>();
		
		for (TranscriptPair index : bestSimilarities.keySet()){
			
			if (bestSimilarities.get(index) < bottomBound)
				if (transcriptome.equals(this.nameOfFirstTranscriptome) 
						&& index.getTranscript1() != null)
					listOfLowerTranscripts.add(index.getTranscript1());
				else 
					if (index.getTranscript2() != null)
						listOfLowerTranscripts.add(index.getTranscript2());
			
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
	
	// Need to optimize!
	

	// Fast assigment for results
	// just for look to aproximate results
	// Не работает:)
	
	/*
	
	public Assignment (Transcriptome tr1, 
			Transcriptome tr2,			
			Assignment asgn1,
			Assignment asgn2) throws IOException {
		
		this.nameOfFirstTranscriptome = 
				tr1.getNameOfSet();
		this.nameOfSecondTranscriptome = 
				asgn1.nameOfSecondTranscriptome;
		this.countOfTranscriptsInSet1 = 
				tr1.getAllSeq().size();
		this.countOfTranscriptsInSet2 = 
				asgn1.countOfTranscriptsInSet2;
		
		for (String index : tr1.getAllSeq()){
			
			if (asgn1.search(index) != null)
				this.bestSimilarities.put(asgn1.search(index),
						asgn1.getSim(asgn1.search(index)));
			
			else 
				if (asgn2.search(index) != null)
					this.bestSimilarities.put(asgn2.search(index),
							asgn2.getSim(asgn2.search(index)));
			
				else {
					Transcript tr = 
							new Transcript (tr1.getName(index), index);
					TranscriptPair pair = new TranscriptPair(tr, null);
					this.bestSimilarities.put(pair, 0.0);
				}
			double sim = (asgn1.search(index) == null) ? 
						asgn2.getSim(asgn2.search(index)) : 
						asgn1.getSim(asgn1.search(index));
			
			TranscriptPair pair = (asgn1.search(index) == null) ? 
						asgn2.search(index) : 
						asgn1.search(index);
						
			this.bestSimilarities.put(pair, sim);
		}
	}


	
	public TranscriptPair search (String trSeq) throws IOException{
		for (TranscriptPair index : bestSimilarities.keySet())
			if (index.getTranscript(0).getData().equals(trSeq) || 
			index.getTranscript(1).getData().equals(trSeq))
				return index;
		
		return null;
	}
	
	public double getSim (TranscriptPair pair){
		return bestSimilarities.get(pair);
	}
	
	*/
	
}
