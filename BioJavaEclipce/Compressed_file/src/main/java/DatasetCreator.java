import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;


public class DatasetCreator {
	
	// from vector of features in HashMap format create class Instance (Weka)
	private Vectorizer vectorizer;
	

	public static PairOfDataSets createInstances (Assignment trAssignment1,
			Assignment trAssignment2,
			TranscriptomeAssembly tr1,
			TranscriptomeAssembly tr2,
			double topBound,
			double bottomBound, 
			Vectorizer vectorize,
			int maxCountOfSet1) throws Exception{
		
		System.out.println("________________________");
		System.out.println("Dataset creating");
		/* 
		 * set2 - reads of class2
		 * 
		 * you can set the count of reads in set2 (int maxCountOfClass2),
		 * but this number is bounded above by set2.retainAll(set1)
		 * 
		 * I know, really this number is bounded above by larger number:)
		 * But in practice maxCountOfClass2 << set2.size()
		 * 
		 */
		
		
		
		ReadsForTraining training = 
				GoodReadsCreator.createSet(trAssignment1, 
						trAssignment2, 
						tr1, 
						tr2, 
						topBound, 
						bottomBound,
						maxCountOfSet1);
		
		ArrayList<Read> set1 = training.getReadsForClass1();
		ArrayList<Read> set2 = training.getReadsForClass2();
		
		ArrayList<Read> setSum = new ArrayList<Read>();
		
		setSum.addAll(set1);
		setSum.addAll(set2);
		
		ArrayList<Attribute> atts = new ArrayList<Attribute>();
		for (String index : vectorize.getVector().keySet()){
			atts.add(new Attribute(index));
		}
		
		ArrayList<String> attVals = new ArrayList<String>();
		for (int j = 0; j < 2; j++)
		  attVals.add("class" + (j+1));
		atts.add(new Attribute("class", attVals));
		
		System.out.println("Creating instances");
		
		Instances data1 = new Instances("MyRelation", atts, 0);
		Instances data2 = new Instances("MyRelation", atts, 0);
		
		System.out.println("Instances for set1");
		for (Read index : set1){
			HashMap<String, Integer> strToHashMap = vectorize.vectorize(index.getData());
			data1.add(createInstance(strToHashMap, attVals.indexOf("class1")));
		}
		System.out.println("Instances for set2");
		
		for (Read index : set2){
			HashMap<String, Integer> strToHashMap = vectorize.vectorize(index.getData());
			data2.add(createInstance(strToHashMap, attVals.indexOf("class2")));
		}
		data1.setClassIndex(data1.numAttributes() - 1);
		data2.setClassIndex(data2.numAttributes() - 1);
		int size = data1.size()+data2.size();
		System.out.println("Instances created");
		System.out.println("Size of data" + size );
		System.out.println("Size of set1: " + set1.size());
		System.out.println("Size of set2 : " + set2.size());
		System.out.println("________________________");
		PairOfDataSets pair = new PairOfDataSets(data1, data2);
		return pair;
	}
	
	
	
	/*
	 * Create dataset for two transcripts
	 * for classificators of Weka:
	 * 
	 * class1 - reads, which good for transcriptome1
	 * and bad for transcriptome2
	 * 
	 * class2 - another reads
	 * 
	 */
	
	public static Instances createData (Assignment trAssignment1,
			Assignment trAssignment2,
			TranscriptomeAssembly tr1,
			TranscriptomeAssembly tr2,
			double topBound,
			double bottomBound, 
			Vectorizer vectorize,
			int maxCountOfSet1) throws Exception{
		
		System.out.println("________________________");
		System.out.println("Dataset creating");
		PairOfDataSets pair = DatasetCreator.createInstances (trAssignment1,
				trAssignment2,
				tr1,
				tr2,
				topBound,
				bottomBound, 
				vectorize,
				maxCountOfSet1);
		
		Instances data = pair.getInstsForClass1();
		data.addAll(pair.getInstsForClass2());
		
		return data;
	}
	
	public static Instances createDataWithoutVectorizer (Assignment trAssignment1,
			Assignment trAssignment2,
			TranscriptomeAssembly tr1,
			TranscriptomeAssembly tr2,
			int topBound,
			int bottomBound,
			int maxCountOfSet1) throws Exception{
		
		ReadsForTraining training = 
				GoodReadsCreator.createSet(trAssignment1, 
						trAssignment2, 
						tr1, 
						tr2, 
						topBound, 
						bottomBound,
						maxCountOfSet1);
		
		ArrayList<Read> set1 = training.getReadsForClass1();
		ArrayList<Read> set2 = training.getReadsForClass2();
		
		System.out.println("Count of class1 is" + set1.size());
		System.out.println("Count of class2 is" + set2.size());
		
		ArrayList<Read> setSum = new ArrayList<Read>();
		
		setSum.addAll(set1);
		setSum.addAll(set2);
		
		
		
		Vectorizer vectorize = new Vectorizer(setSum);
		
		return createData(trAssignment1, 
				trAssignment2, 
				tr1, 
				tr2, 
				topBound, 
				bottomBound, 
				vectorize,
				maxCountOfSet1);
	}
	
	public static Instances createInstanceFromArray (ArrayList<Read> set, 
			Vectorizer vectorize) throws IOException{
		
		ArrayList<Attribute> atts = new ArrayList<Attribute>();
		
		for (String index : vectorize.getVector().keySet()){
			atts.add(new Attribute(index));
		}
		
		ArrayList<String> attVals = new ArrayList<String>();
		for (int j = 0; j < 2; j++)
		  attVals.add("class" + (j+1));
		atts.add(new Attribute("class", attVals));
		
		
		Instances data = new Instances("MyRelation", atts, 0);
		
		for (Read index : set){
			HashMap<String, Integer> strToHashMap = vectorize.vectorize(index.getData());
			data.add(createInstance(strToHashMap, 666));
		}
		data.setClassIndex(data.numAttributes() - 1);
		return data;
	}
///////////////////////////////////////////////////////////////////////////////////
	public DatasetCreator(ReadsForTraining sets) throws IOException{
		
		ArrayList<Read> set = sets.getReadsForClass1();
		set.addAll(sets.getReadsForClass2());
		vectorizer = new Vectorizer (set);
	
	}
	
	public PairOfDataSets create(ReadsForTraining sets) throws Exception {
		
		if (vectorizer == null){
			System.out.println("Fail! DatasetCreator isn't initialised");
			return null;
		}
		
		
		Instances data1 = this.createInstance(sets.getReadsForClass1(), 0);
		Instances data2 = this.createInstance(sets.getReadsForClass2(), 1);
		
		PairOfDataSets pair = new PairOfDataSets(data1, data2);
		return pair;
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
