
public class TranscriptomeSimilarity {
	private double accuracy;
	private double count_proportion;
	private String nameOfTranscriptome;
	
	public TranscriptomeSimilarity (Assignment as,
			TranscriptomeSimilarityComputer method){
		if (as != null){
			this.accuracy = method.compute(as);
			this.count_proportion = ((double)as.getCountOfSet1())/as.getCountOfSet2();
			this.nameOfTranscriptome = as.getNameOfSet1();
		}
	}
	
	public TranscriptomeSimilarity (Transcriptome tr1,
			Transcriptome tr2,
			AssembliesSimilarityRefiner asmSimRef,
			TranscriptSimilarityComputer simComp,
			TranscriptomeSimilarityComputer method,
			WorkWithFiles workWithFiles) throws Exception{
		
		Assignment as = new Assignment(tr1, tr2, simComp, workWithFiles);
		if (as != null){
			this.accuracy = method.compute(as);
			this.count_proportion = ((double)as.getCountOfSet1())/as.getCountOfSet2();
			this.nameOfTranscriptome = as.getNameOfSet1();
		}
	}
	
	public double getAccuracy(){
		return this.accuracy;
	}
	
	public double getCountProportion (){
		return this.count_proportion;
	}
	
	public String getNameOfTranscriptome (){
		return this.getNameOfTranscriptome();
	}
	
}
