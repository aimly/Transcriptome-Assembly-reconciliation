import weka.classifiers.Classifier;



public class MLRefiner {

	public static Transcriptome createTranscript(Classifier classifier,
			Params params,
			WorkWithData dataForClassifier,
			TranscriptomeAssembly tr1) throws Exception {
		
		System.out.println("Bilding classifier");
		System.out.println("DataSize: " + dataForClassifier.getData().size());
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
