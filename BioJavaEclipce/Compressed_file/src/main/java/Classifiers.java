import weka.classifiers.Classifier;


public class Classifiers {
	private Classifier classifierForGoodReads1;
	private Classifier classifierForGoodReads2;
	
	public Classifiers (Classifier cl1, Classifier cl2){
		this.classifierForGoodReads1 = cl1;
		this.classifierForGoodReads2 = cl2;
	}
	
	public Classifier getClassifier1 (){
		return this.classifierForGoodReads1;
	}
	
	public Classifier getClassifier2 (){
		return this.classifierForGoodReads2;
	}
}
