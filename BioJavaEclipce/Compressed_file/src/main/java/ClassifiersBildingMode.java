import weka.classifiers.Classifier;
import weka.core.Instances;


public class ClassifiersBildingMode implements WorkMode {

	@Override
	public ReturnOfWorkMode work(AssembliesSimilarityRefiner simref, 
			Classifiers classifiers, 
			Params params,
			TranscriptomeAssembly tr1, 
			WorkWithData dataForClassifier1, 
			TranscriptomeAssembly tr2,
			WorkWithData dataForClassifier2, 
			Assignment asgn,
			WorkWithFiles workWithFiles) throws Exception {
		
		ClassifiersBildingMode.
			bildClassifier(classifiers.getClassifier1(), 
					dataForClassifier1.getData(), workWithFiles, 
					tr1.getNameOfTranscriptome(), 
					tr2.getNameOfTranscriptome(), 
					params);
		
		ClassifiersBildingMode.
		bildClassifier(classifiers.getClassifier2(), 
				dataForClassifier2.getData(), workWithFiles, 
				tr2.getNameOfTranscriptome(), 
				tr1.getNameOfTranscriptome(), 
				params);
		
		return new ReturnOfWorkMode();
		
	}
	
	public static void bildClassifier (Classifier classifier,
			Instances data,
			WorkWithFiles workWithFiles,
			String tr1Name,
			String tr2Name,
			Params params) throws Exception{
		
		classifier.buildClassifier(data);
		workWithFiles.writeToFile(workWithFiles.getClassifierID(), 
				classifier, tr1Name, tr2Name, 
				params.getTB(), 
				params.getBB(), 
				params.getClassifier(), 
				params.getParamsForClassifier(), 1);
	}

}
