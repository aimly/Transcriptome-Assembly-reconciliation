import weka.classifiers.Classifier;


public interface WorkMode {
	public void work (AssembliesSimilarityRefiner simref,
			Classifiers classifiers,
			Params params,
			TranscriptomeAssembly tr1,
			WorkWithData dataForClassifier1,
			TranscriptomeAssembly tr2,
			WorkWithData dataForClassifier2,
			Assignment asgn) throws Exception;
}
