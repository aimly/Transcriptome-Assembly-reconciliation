import weka.classifiers.Classifier;


public class TranscriptomesRefiner  implements WorkMode{
	
	@Override
	public void work (AssembliesSimilarityRefiner simref,
			Classifiers classifiers,
			Params params,
			TranscriptomeAssembly tr1,
			WorkWithData dataForClassifier1,
			TranscriptomeAssembly tr2,
			WorkWithData dataForClassifier2,
			Assignment asgn) throws Exception {
		
		Transcriptome transcriptome1 = simref.getSimilarTranscripts(asgn, params);
		
		Transcriptome transcriptome21 = 
				MLRefiner.createTranscript(classifiers.getClassifier1(), 
						params, dataForClassifier1, tr1);
		Transcriptome transcriptome22 = 
				MLRefiner.createTranscript(classifiers.getClassifier2(), 
						params, dataForClassifier2, tr2);
		transcriptome21.merge(transcriptome22);
		
		transcriptome1.merge(transcriptome21);
		System.out.println("Size of final transcriptome is " + transcriptome1.getAllSeq().size());
	}


}
