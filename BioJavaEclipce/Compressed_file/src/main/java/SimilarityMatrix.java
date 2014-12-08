
public class SimilarityMatrix {
	private double[][] sim;
	private Transcriptome tr1;
	private Transcriptome tr2;
	
	public SimilarityMatrix(Transcriptome tr1, 
			Transcriptome tr2,
			TranscriptSimilarityComputer simComp,
			WorkWithFiles workWithFiles) throws Exception{
		
		this.tr1 = tr1;
		this.tr2 = tr2;
		
		sim = simComp.computeSimilarity(tr1, tr2, workWithFiles);
	}
	
	public double[][] getMatrix (){
		return this.sim;
	}
	
	public Transcriptome getFirstTranscriptome (){
		return this.tr1;
	}
	
	public Transcriptome getSecondTranscriptome (){
		return this.tr2;
	}
}
