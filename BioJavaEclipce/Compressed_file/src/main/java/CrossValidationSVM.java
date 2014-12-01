import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.core.Instances;


public class CrossValidationSVM implements CrossValidation {

	@Override
	public void crossValidate(Transcriptome transcriptome1,
			Transcriptome transcriptome2, 
			Assignment trAssignment1,
			Assignment trAssignment2, 
			TranscriptomeAssembly tr1,
			TranscriptomeAssembly tr2, 
			double topBound, 
			double bottomBound,
			Assignment asgn, 
			double level,
			int maxCountOfSet1,
			double percentage,
			String params,
			int numOfFolds) throws Exception {
		
		Vectorizer vectorizer1 = new Vectorizer(trAssignment1,
				trAssignment2,
				tr1,
				tr2,
				topBound,
				bottomBound,
				maxCountOfSet1);
		System.out.println("Vectorizer 1 created");
		System.out.println("Instance1");
		
		Instances data1 = DatasetCreator.createData ( trAssignment1,
				trAssignment2,
				tr1,
				tr2,
				topBound,
				bottomBound,
				vectorizer1,
				maxCountOfSet1);
		
		System.out.println("Instance1 finished");
		
		SMO classifier1 = new SMO();
		System.out.println("Vectorizer2");
		Vectorizer vectorizer2 = new Vectorizer(trAssignment2,
				trAssignment1,
				tr2,
				tr1,
				topBound,
				bottomBound,
				maxCountOfSet1);
		
		System.out.println("Vectorizer2 finished");
		System.out.println("Instance2");
		
		Instances data2 = DatasetCreator.createData ( trAssignment2,
				trAssignment1,
				tr2,
				tr1,
				topBound,
				bottomBound,
				vectorizer2,
				maxCountOfSet1);
		
		System.out.println("Instance2 finished");
		
		SMO classifier2 = new SMO();
		

		System.out.println("MLLibRefiner made sets and another shit");
		
		
		/*
		 * Recomended params:
		 * "-I 100 -K 10 -S 1 -depth 0 -num-slots 20"
		 * 
		 */
		
		

			
		System.out.println("_____________________________");
		System.out.println("Cross-validation started");
		
		System.out.println("Classifier1");
		System.out.println("Good reads for " + transcriptome1.getNameOfSet() +
				" and bad reads for " + transcriptome2.getNameOfSet());
		System.out.println("k-fold CV of SVM, k = " + numOfFolds);
		classifier1.setOptions(weka.core.Utils.splitOptions(params));

		Evaluation eval1 = new Evaluation(data1);
		eval1.crossValidateModel(classifier1, 
				data1, numOfFolds, new Random(1));
		System.out.println("Estimated Accuracy: "+Double.toString(eval1.pctCorrect()));
		System.out.println("Precision: "+eval1.precision(0));
		System.out.println("Recall: "+eval1.recall(0));
		System.out.println("F-measure: "+eval1.fMeasure(0));

		System.out.println("Classifier2");
		System.out.println("Good reads for " + transcriptome2.getNameOfSet() +
				" and bad reads for " + transcriptome1.getNameOfSet());
		System.out.println("k-fold CV of SVM, k = " + numOfFolds);
		classifier2.setOptions(weka.core.Utils.splitOptions(params));
			
		Evaluation eval2 = new Evaluation(data2);
		eval2.crossValidateModel(classifier2, 
				data2, numOfFolds, new Random(1));
		System.out.println("Estimated Accuracy: "+Double.toString(eval2.pctCorrect()));
		System.out.println("Precision: "+eval2.precision(0));
		System.out.println("Recall: "+eval2.recall(0));
		System.out.println("F-measure: "+eval2.fMeasure(0));			


		System.out.println("Cross-validation finished");
		System.out.println("_____________________________");
		
	

		
	}

}
