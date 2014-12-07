import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/*
 * 
 * Class for create vector of features for string
 * features is n-gramms (substrings with length n)
 * Vector is in HashMap format
 * 
 */

public class Vectorizer {
	
	// Model of vector to vectorise string
	
	private HashMap<String, Integer> vector = 
			new HashMap<String, Integer>();
	private String status;
	// To vectorize string we need a copy of model vector
	
	private HashMap<String, Integer> copyOfVector () {
		if (vector.isEmpty()){
			System.out.println("Cannot copy vector : vector is empty");
			return null;
		}
		
		HashMap<String, Integer> newVector = 
				new HashMap<String, Integer>();
		
		for (String index : this.vector.keySet()){
			newVector.put(index, 0);
		}
			
		return newVector;
	}
	
	// Constructor with data to create model vector
	

	// Тот самый конструктор, написанный через жопу
	// Обязательно переписать!
	
	public Vectorizer (Assignment trAssignment1,
			Assignment trAssignment2,
			TranscriptomeAssembly tr1,
			TranscriptomeAssembly tr2,
			double topBound,
			double bottomBound,
			int maxCountOfSet1) throws Exception{
		System.out.println("________________________");
		System.out.println("Initialization of Vectorizer with manz params");
		
		
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
		
		System.out.println("Final sizes: ");
		System.out.println("set1 " + set1.size());
		System.out.println("set2 "+ set2.size());
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
		
		/*
		int maxCountOfClass2 = set1.size();
		
		System.out.println("Maxcount of class2: " + maxCountOfClass2);
		
		int i = 0;
		
		int removals = 0;
		if (maxCountOfClass2 < set2.size()){
			System.out.println("Removals is need");
			int set2Size = set2.size();
			for (i = set2Size-1; i >= maxCountOfClass2; i--){
				set2.remove(i);
				removals++;
			}
		}
		System.out.println("set2 had " + removals + " removals");
		*/
		System.out.println("Count of class1 is " + set1.size());
		System.out.println("Count of class2 is " + set2.size());

		ArrayList<Read> setSum = new ArrayList<Read>();
		
		setSum.addAll(set1);
		setSum.addAll(set2);
		
		
		
		Vectorizer vectorize = new Vectorizer(setSum);
		this.status = vectorize.getStatus();
		this.vector = vectorize.getVector();
		System.out.println("Vectorizer initialized successfull");
		System.out.println("________________________");
	}
	
	
	public Vectorizer (HashMap<String, Integer> vect){
		this.vector = vect;
		this.status = "full";
	}
	
	public Vectorizer (){
		this.status = "empty";
	}
	
	// Method - vectorise String
	


	

	public String getStatus () {
		
		if (this.status == null){
			System.out.println("Vectorizer is NULL!");
			return "null";
		}
		
		return this.status;
	}
	
	////////////////////////////////////////////////////////////////
	public Vectorizer (ArrayList<Read> reads) throws IOException{

		for (Read index : reads){
			
			NGramTokenizer gramTokenizer = 
					new NGramTokenizer(new StringReader(index.getData()), 
							1, 4);
			CharTermAttribute charTermAttribute = 
					gramTokenizer.addAttribute(CharTermAttribute.class);
			
			gramTokenizer.reset();
			
			while (gramTokenizer.incrementToken()) {
			    String token = charTermAttribute.toString();
			    if (token != null){
			    	vector.put(token, 0);
			    }
			}
			
			gramTokenizer.close();
		}
		
	}
	

	public HashMap<String, Integer> vectorize (String str) 
			throws IOException{
		
		HashMap<String, Integer> vect = this.copyOfVector();
		
		NGramTokenizer gramTokenizer = 
				new NGramTokenizer(new StringReader(str), 1, 4);
		CharTermAttribute charTermAttribute = 
				gramTokenizer.addAttribute(CharTermAttribute.class);
		
		gramTokenizer.reset();
		
		while (gramTokenizer.incrementToken()) {
		    String token = charTermAttribute.toString();
		    if (!vect.containsKey(token))
		    	continue;
		    vect.put(token, vect.get(token) + 1);
		}
		gramTokenizer.close();
		return vect;
	}
	
	// This function is to create attributes in DataCreator
	
	public HashMap<String, Integer> getVector () {
		return vector;
	}
	
}
