import java.util.HashMap;

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
	
	public Vectorizer (){
		
	}
	
	public HashMap<String, Integer> vectorize (String str){
		HashMap<String, Integer> vect = this.copyOfVector();
		return vect;
	}
}
