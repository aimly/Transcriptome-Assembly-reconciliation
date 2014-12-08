import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;


public class DatasetCreator {
	
	// from vector of features in HashMap format create class Instance (Weka)
	private Vectorizer vectorizer;
	

	public DatasetCreator(ReadsForTraining sets) throws IOException{
		
		ArrayList<Read> set = sets.getReadsForClass1();
		set.addAll(sets.getReadsForClass2());
		vectorizer = new Vectorizer (set);
	
	}
	
	public Instances create(ReadsForTraining sets) throws Exception {
		
		if (vectorizer == null){
			System.out.println("Fail! DatasetCreator isn't initialised");
			return null;
		}
		
		
		Instances data1 = this.createInstance(sets.getReadsForClass1(), 0);
		Instances data2 = this.createInstance(sets.getReadsForClass2(), 1);
		
		System.out.println("data1: " + data1.size());
		System.out.println("data2: " + data2.size());
		
		data1.addAll(data2);
		System.out.println("DataSum: " + data1.size());
		return data1;
	}
	
	public Instances createInstance(ArrayList<Read> set, int typeOfClass) throws IOException{
		
		if (vectorizer == null){
			System.out.println("Fail! DatasetCreator isn't initialised");
			return null;
		}
		
		ArrayList<Attribute> atts = new ArrayList<Attribute>();
		for (String index : vectorizer.getVector().keySet()){
			atts.add(new Attribute(index));
		}
		
		ArrayList<String> attVals = new ArrayList<String>();
		for (int j = 0; j < 2; j++)
		  attVals.add("class" + (j+1));
		atts.add(new Attribute("class", attVals));
		
		
		Instances data = new Instances("MyRelation", atts, 0);
		
		for (Read index : set){
			HashMap<String, Integer> strToHashMap = vectorizer.vectorize(index.getData());
			data.add(createInstance(strToHashMap, typeOfClass));
		}
		data.setClassIndex(data.numAttributes() - 1);
		return data;
		
	}
	
	private static DenseInstance createInstance (HashMap<String, Integer> instanceInMap, double classOfStr) {
		
		double[] vals = new double[instanceInMap.size() + 1];
		int i = 0;
		
		for (String index : instanceInMap.keySet()){
			vals[i] = instanceInMap.get(index);
			i++;
		}
		
		vals[i] = classOfStr;
		
		DenseInstance data = new DenseInstance(1.0, vals);
		return data;
	}
	

	
}
