
public class Params {
	/*
	 * Mode: "CV", "refine" or "getClassifiers"
	 */
	private String mode;
	/* 
	 * topBound
	 */
	private double topBound;
	/*
	 *  bottomBound
	 */
	private double bottomBound;
	/*
	 * <maxCountOfSet1> - max number of good transcripts 
	 * Need to control size of dataset for learning
	 * classifiers
	 */
	private int maxCountOfSet1;
	/*
	 * Classifier and params for it
	 * 
	 * Examples:
	 * for SVM:
	 * "-C 1.0 -L 0.0010 -P 1.0E-12 -N 0 -V -1 -W 1 -K \"weka.classifiers.functions.supportVector.PolyKernel -C 250007 -E 1.0" + "\""
	 * for RF:
	 * "-I 100 -K 100 -S 1 -depth 0 -num-slots 20"
	 */
	private String classifier;
	private String params;
	/*
	 * <level> - For part of final transcriptome without using ML.
	 * When we search similar transcripts in two assemblies,
	 * we choose transcripts, which similar with more than <level>
	 * value in our measure
	 */
	private double level;
    /*
	 * <percentage> - in ML part we add transcripts, which have 
	 * part more than <percentage> of good reads
	 */
	private double percentage;
	/*
	 * For CV num of folds for k-fold cv
	 */
	private int numOfFolds;
	/*
	 * Files of transcriptomes, reads, dependencies and referense
	 */
	private String tr1;
	private String tr2;
	private String rd;
	private String dep1;
	private String dep2;
	private String ref;
	
	// Getters and setters for all fields
	// Не знаю - может, и не нужно оно и пабликом на все поля можно было бы обойтись
	
	public void setMode (String md){
		this.mode = md;
	}
	
	public String getMode (){
		return this.mode;
	}
	
	public void setTB (double tb){
		this.topBound = tb;
	}
	
	public double getTB (){
		return this.topBound;
	}
	
	public void setBB (double bb){
		this.bottomBound = bb;
	}
	
	public double getBB (){
		return this.bottomBound;
	}
	
	public void setMaxCountOfSet1 (int maxCount){
		this.maxCountOfSet1 = maxCount;
	}
	
	public int getMaxCountOfSet1 (){
		return this.maxCountOfSet1;
	}
	
	public void setClassifier (String cl){
		this.classifier = cl;
	}
	
	public String getClassifier (){
		return this.classifier;
	}
	
	public void setParamsForClassifier (String params){
		this.params = params;
	}
	
	public String getParamsForClassifier (){
		return this.params;
	}
	
	public void setLevel (double lvl){
		this.level = lvl;
	}
	
	public double getLevel (){
		return this.level;
	}
	
	public void setPercentage (double ps){
		this.percentage = ps;
	}
	
	public double getPercentage (){
		return this.percentage;
	}
	
	public void setFolds (int folds){
		this.numOfFolds = folds;
	}
	
	public int getFolds (){
		return this.numOfFolds;
	}
	
	public void setTr1FileName (String tr1){
		this.tr1 = tr1;
	}
	
	public String getTr1FileName (){
		return this.tr1;
	}
	
	public void setTr2FileName (String tr2){
		this.tr2 = tr2;
	}
	
	public String getTr2FileName (){
		return this.tr2;
	}
	
	public void setReadsFileName (String rd){
		this.rd = rd;
	}
	
	public String getReadsFileName (){
		return this.rd;
	}
	
	public void setDep1FileName (String dep1){
		this.dep1 = dep1;
	}
	
	public String getDep1FileName (){
		return this.dep1;
	}
	
	public void setDep2FileName (String dep2){
		this.dep2 = dep2;
	}
	
	public String getDep2FileName (){
		return this.dep2;
	}
	
	public void setRefFileName (String ref){
		this.ref = ref;
	}
	
	public String getRefFileName (){
		return this.ref;
	}
}
