import weka.classifiers.Classifier;



public class MLRefiner {

	public static Transcriptome createTranscript(Classifier classifier,
			Params params,
			WorkWithData dataForClassifier,
			TranscriptomeAssembly tr1,
			WorkWithFiles workWithFiles) throws Exception {
		
		Classifier cl = (Classifier) workWithFiles.read(workWithFiles.getClassifierID(), 
				tr1.getNameOfTranscriptome(), null, params.getTB(), 
				params.getBB(), params.getClassifier(), params.getParamsForClassifier());
		if (cl == null)
			cl = classifier;
		classifier.buildClassifier(dataForClassifier.getData());
		System.out.println("Classifier bilded");
		
		Transcriptome tr = 
				GoodTranscriptsCreator.getTranscripts(classifier, 
						dataForClassifier.getDatasetCreator(), 
						params,
						tr1);
		return tr;
	}

}
