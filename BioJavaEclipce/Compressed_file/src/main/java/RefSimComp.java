
public interface RefSimComp {
	public void computeSimilarity(TranscriptomeSimilarityComputer transcriptomeSimComp, 
			ReturnOfWorkMode resTranscriptome, 
			WorkWithFiles workWithFiles,
			InputTranscriptomes inpTr,
			TranscriptSimilarityComputer trSim,
			AssembliesSimilarityRefiner asmSimRef) throws Exception;
}
