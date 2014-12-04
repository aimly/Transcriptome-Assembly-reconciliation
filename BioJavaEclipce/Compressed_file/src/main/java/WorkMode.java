import weka.classifiers.Classifier;


public interface WorkMode {
	public void work (AssembliesSimilarityRefiner simref,
			Classifier classifier,
			PairOfDataSets data,
			DatasetCreator datasetCreator,
			Params params,
			TranscriptomeAssembly tr1,
			TranscriptomeAssembly tr2,
			Assignment asgn) throws Exception;
}
