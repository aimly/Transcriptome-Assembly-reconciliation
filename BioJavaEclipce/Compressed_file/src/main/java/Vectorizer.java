import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class Vectorizer {
	private HashMap<String, Integer> vector;
	
	private HashMap<String, Integer> copyOfVector () {
		if (vector.isEmpty()){
			System.out.println("Cannot copy vector : vector is empty");
			return null;
		}
		
		HashMap<String, Integer> newVector = new HashMap<String, Integer>();
		
		for (String index : this.vector.keySet()){
			newVector.put(index, 0);
		}
			
		return newVector;
	}
	

	
	public Vectorizer (ArrayList<Read> bad) throws IOException{
		for (Read index : bad){
			
			Reader reader = new StringReader(index.getData());
			NGramTokenizer gramTokenizer = new NGramTokenizer(reader, 1, 3);
			CharTermAttribute charTermAttribute = gramTokenizer.addAttribute(CharTermAttribute.class);
			
			gramTokenizer.reset();
			
			while (gramTokenizer.incrementToken()) {
			    String token = charTermAttribute.toString();
			    vector.put(token, 0);
			}
			gramTokenizer.close();
		}
	}
	
	public HashMap<String, Integer> vectorize (String str) throws IOException{
		HashMap<String, Integer> vect = this.copyOfVector();
		
		Reader reader = new StringReader(str);
		NGramTokenizer gramTokenizer = new NGramTokenizer(reader, 1, 3);
		CharTermAttribute charTermAttribute = gramTokenizer.addAttribute(CharTermAttribute.class);
		
		gramTokenizer.reset();
		
		while (gramTokenizer.incrementToken()) {
		    String token = charTermAttribute.toString();
		    vect.put(token, vect.get(token) + 1);
		}
		gramTokenizer.close();
		return vect;
	}
	
	public HashMap<String, Integer> getVector () {
		return vector;
	}
}
