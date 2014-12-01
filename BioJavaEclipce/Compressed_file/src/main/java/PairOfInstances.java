import weka.core.Instances;


public class PairOfInstances {
	private Instances class1;
	private Instances class2;
	
	public PairOfInstances(Instances inst1, Instances inst2){
		class1 = inst1;
		class2 = inst2;
	}
	
	public Instances getInstsForClass1 (){
		return class1;
	}
	
	public Instances getInstsForClass2 (){
		return class2;
	}
}
