import weka.classifiers.Classifier;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.RandomForest;


public class ClassCreator {
	
	public static WorkMode getWorkMode (String mode){
		WorkMode workMode = null;
		if (mode.compareTo("bild") == 0){
			workMode = new TranscriptomesRefiner();
		}
		return workMode;
	}
	
	public static Classifier getClassifier (String typeOfClassifier, String options) throws Exception{
		Classifier classifier = null;
		if (typeOfClassifier.compareTo("RF") == 0){
			classifier = new RandomForest();
			((RandomForest) classifier).setOptions(weka.core.Utils.splitOptions(options));
		}
		if (typeOfClassifier.compareTo("SVM") == 0){
			classifier = new SMO();
			((SMO) classifier).setOptions(weka.core.Utils.splitOptions(options));
		}
		if (typeOfClassifier.compareTo("AD") == 0){
			classifier = new LibSVM();
			((LibSVM) classifier).setOptions(weka.core.Utils.splitOptions(options));
		}
		return classifier;
	}
	
	public static AssembliesSimilarityRefiner getSimilarityRefiner (){
		return new ClassicAssemblerSimilarityRefiner();
	}
}
