import weka.classifiers.Classifier;


public class Classifiers {
	private Classifier classifierForGoodReads1;
	private Classifier classifierForGoodReads2;
	
	public Classifiers (Classifier cl1, Classifier cl2){
		this.classifierForGoodReads1 = cl1;
		this.classifierForGoodReads2 = cl2;
		System.out.println("Classifiers: " + this.classifierForGoodReads1.getClass());
		System.out.println("Classifiers: " + this.classifierForGoodReads2.getClass());
	}
	
	public Classifier getClassifier1 (){
		return this.classifierForGoodReads1;
	}
	
	public Classifier getClassifier2 (){
		return this.classifierForGoodReads2;
	}
}
