
public class TranscriptomeSimilarity {
	private double accuracy;
	private double count_proportion;
	private String nameOfTranscriptome;
	
	public TranscriptomeSimilarity (Assignment as){
		DefaultMethod solver = new DefaultMethod();
		this.accuracy = solver.compute(as);
		this.count_proportion = ((double)as.getCountOfSet1())/as.getCountOfSet2();
		this.nameOfTranscriptome = as.getNameOfSet1();
	}
}
