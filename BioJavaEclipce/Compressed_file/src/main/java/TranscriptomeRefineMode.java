
public class TranscriptomeRefineMode  implements WorkMode{
	
	@Override
	public ReturnOfWorkMode work (AssembliesSimilarityRefiner simref,
			Classifiers classifiers,
			Params params,
			TranscriptomeAssembly tr1,
			WorkWithData dataForClassifier1,
			TranscriptomeAssembly tr2,
			WorkWithData dataForClassifier2,
			Assignment asgn,
			WorkWithFiles workWithFiles) throws Exception {
		
		Transcriptome transcriptome1 = simref.getSimilarTranscripts(asgn, params);
		
		Transcriptome transcriptome21 = 
				MLRefiner.createTranscript(classifiers.getClassifier1(), 
						params, dataForClassifier1, tr1, workWithFiles);
		Transcriptome transcriptome22 = 
				MLRefiner.createTranscript(classifiers.getClassifier2(), 
						params, dataForClassifier2, tr2, workWithFiles);
		transcriptome21.merge(transcriptome22);
		
		transcriptome1.merge(transcriptome21);
		
		workWithFiles.writeToFile(workWithFiles.getRefinedTrID(), 
				transcriptome1, tr1.getNameOfTranscriptome(), 
				tr2.getNameOfTranscriptome(), params.getTB(), 
				params.getBB(), params.getClassifier(), 
				params.getParamsForClassifier(), 0);
		
		System.out.println("Size of final transcriptome is " + transcriptome1.getAllSeq().size());
		return new ReturnOfWorkMode(transcriptome1);
	}


}
