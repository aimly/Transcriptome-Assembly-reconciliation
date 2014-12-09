import weka.classifiers.Classifier;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.RandomForest;


public class ClassCreator {
	
	public static WorkMode getWorkMode (String mode){
		WorkMode workMode = null;
		if (mode.compareTo("bild") == 0){
			workMode = new TranscriptomeRefineMode();
		}
		if (mode.compareTo("CV") == 0){
			workMode = new CrossValidationMode();
		}
		if (mode.compareTo("getClassifiers") == 0){
			workMode = new ClassifiersBildingMode();
		}
		return workMode;
	}
	
	public static Classifiers getClassifiers (String typeOfClassifier, 
			String options) throws Exception{
		Classifiers classifiers = null;
		Classifier classifier1 = null;
		Classifier classifier2 = null;
		if (typeOfClassifier.compareTo("RF") == 0){
			classifier1 = new RandomForest();
			classifier2 = new RandomForest();
			((RandomForest) classifier1).setOptions(weka.core.Utils.splitOptions(options));
			((RandomForest) classifier2).setOptions(weka.core.Utils.splitOptions(options));
			System.out.println(classifier1.getClass());
		}
		if (typeOfClassifier.compareTo("SVM") == 0){
			classifier1 = new SMO();
			classifier2 = new SMO();
			((SMO) classifier1).setOptions(weka.core.Utils.splitOptions(options));
			((SMO) classifier2).setOptions(weka.core.Utils.splitOptions(options));
		}
		if (typeOfClassifier.compareTo("AD") == 0){
			classifier1 = new LibSVM();
			classifier2 = new LibSVM();
			((LibSVM) classifier1).setOptions(weka.core.Utils.splitOptions(options));
			((LibSVM) classifier2).setOptions(weka.core.Utils.splitOptions(options));
		}
		classifiers = new Classifiers (classifier1, classifier2);
		return classifiers;
	}
	
	public static AssembliesSimilarityRefiner getSimilarityRefiner (){
		return new ClassicAssemblerSimilarityRefiner();
	}

	public static TranscriptSimilarityComputer getSimComp() {
		return new NWSim();
	}

	public static TranscriptomeSimilarityComputer getTranscriptomeSim() {
		
		return new DefaultMethod();
	}

	public static InputTranscriptomes getSimilarityRefiner(Params prms,
			WorkWithFiles workWithFiles,
			TranscriptSimilarityComputer trSim) throws Exception {
		
		String fileOfTranscriptome1 = workWithFiles.getPathForFile(prms.getTr1FileName(), "tr");
    	String fileOfTranscriptome2 = workWithFiles.getPathForFile(prms.getTr2FileName(), "tr");
		
		if (prms.getRefFileName() != null){
			String fileOfRef = workWithFiles.getPathForFile(prms.getRefFileName(), "ref");
			return new InputTranscriptomesWithRef(fileOfTranscriptome1, 
					fileOfTranscriptome2, fileOfRef, 
					trSim, workWithFiles);
		} else {
			return new InputTranscriptomesWithoutRef(fileOfTranscriptome1,
					fileOfTranscriptome2, trSim, workWithFiles);
		}
	}

	public static RefSimComp getRefSimComp(Params params) {
		if (params.getMode().compareTo("bild") == 0 && 
				params.getRefFileName() != null){
			return new RefSimCompWithRefBild();
		}
		else
			return new RefSimCompNoneBild();
	}
}
