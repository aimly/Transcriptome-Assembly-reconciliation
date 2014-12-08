
public interface WorkMode {
	public Transcriptome work (AssembliesSimilarityRefiner simref,
			Classifiers classifiers,
			Params params,
			TranscriptomeAssembly tr1,
			WorkWithData dataForClassifier1,
			TranscriptomeAssembly tr2,
			WorkWithData dataForClassifier2,
			Assignment asgn,
			WorkWithFiles workWithFiles) throws Exception;
}
