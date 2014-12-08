
public interface TranscriptSimilarityComputer {
	
	public double[][] computeSimilarity(Transcriptome firstSet, 
			Transcriptome secondSet,
			WorkWithFiles workWithFiles) throws Exception ;
	
}
