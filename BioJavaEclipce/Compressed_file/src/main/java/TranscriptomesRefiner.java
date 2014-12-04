import weka.classifiers.Classifier;


public class TranscriptomesRefiner  implements WorkMode{
	
	@Override
	public void work (AssembliesSimilarityRefiner simref,
			Classifier classifier,
			PairOfDataSets data,
			DatasetCreator datasetCreator,
			Params params,
			TranscriptomeAssembly tr1,
			TranscriptomeAssembly tr2,
			Assignment asgn) throws Exception {
		
		Transcriptome transcriptome1 = simref.getSimilarTranscripts(asgn, params);
		
		Transcriptome transcriptome21 = 
				MLRefiner.createTranscript(classifier, 
						data.getDatasetForFirstTranscriptome(), datasetCreator, params, tr1, tr2);
		Transcriptome transcriptome22 = 
				MLRefiner.createTranscript(classifier, 
						data.getDatasetForSecondTranscriptome(), datasetCreator, params, tr2, tr1);
		transcriptome21.merge(transcriptome22);
		
		transcriptome1.merge(transcriptome21);
		System.out.println("Size of final transcriptome is " + transcriptome1.getAllSeq().size());
	}


}
