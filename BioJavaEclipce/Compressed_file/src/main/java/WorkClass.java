import weka.classifiers.Classifier;


public class WorkClass {
	public static void work(AssembliesSimilarityRefiner simref,
			Classifier classifier,
			Params params,
			WorkMode workMode,
			TranscriptomeAssembly tr1,
			TranscriptomeAssembly tr2,
			ReadsForTraining reads,
			Assignment asgn) throws Exception{
		DatasetCreator datasetCreator = new DatasetCreator(reads);
		PairOfDataSets data = datasetCreator.create(reads);
		workMode.work(simref, 
				classifier, 
				data, 
				datasetCreator, 
				params,
				tr1,
				tr2,
				asgn);
	};
}
