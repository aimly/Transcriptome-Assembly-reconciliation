import weka.classifiers.Classifier;
import weka.core.Instances;



public class MLRefiner {

	public static Transcriptome createTranscript(Classifier classifier,
			Instances dataset,
			DatasetCreator datasetCreator,
			Params params,
			TranscriptomeAssembly tr1,
			TranscriptomeAssembly tr2) throws Exception {
		classifier.buildClassifier(dataset);
		Transcriptome tr = 
				GoodTranscriptsCreator.getTranscripts(classifier, 
						datasetCreator, 
						params,
						tr1,
						tr2);
		return tr;
	}

}
