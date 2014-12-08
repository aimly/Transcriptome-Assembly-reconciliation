import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;


public class CrossValidationMode implements WorkMode {

	@Override
	public Transcriptome work(AssembliesSimilarityRefiner simref, 
			Classifiers classifiers, 
			Params params,
			TranscriptomeAssembly tr1, 
			WorkWithData dataForClassifier1, 
			TranscriptomeAssembly tr2,
			WorkWithData dataForClassifier2, 
			Assignment asgn,
			WorkWithFiles workWithFiles) throws Exception {
		
		CrossValidationMode.analyse(classifiers.getClassifier1(), 
				dataForClassifier1.getData(), 
				params.getFolds());
		
		CrossValidationMode.analyse(classifiers.getClassifier2(), 
				dataForClassifier2.getData(), 
				params.getFolds());
		
		return null;

	}
	
	private static void analyse (Classifier classifier,
			Instances data,
			int numOfFolds) throws Exception{
		
		Evaluation eval1 = new Evaluation(data);
		eval1.crossValidateModel(classifier, 
				data, numOfFolds, new Random(1));
		System.out.println("Estimated Accuracy: "+Double.toString(eval1.pctCorrect()));
		System.out.println("Precision: "+eval1.precision(0));
		System.out.println("Recall: "+eval1.recall(0));
		System.out.println("F-measure: "+eval1.fMeasure(0));
		
		
	}

}
