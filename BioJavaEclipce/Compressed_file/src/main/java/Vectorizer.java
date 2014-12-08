import java.io.IOException;
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
