import java.util.HashMap;


public class DefaultMethod implements TranscriptomeSimilarityComputer {

	
	public double compute(Assignment asm) {
		
		if (asm == null){
			return 0;
		}
		HashMap<TranscriptPair, Double> pairs = asm.getMap();
		
		double sumAcc = 0;
		int count = 0;
		for (TranscriptPair index : pairs.keySet()){
			sumAcc += pairs.get(index);
			count ++;
		}
		System.out.println(sumAcc/count);
		return sumAcc/count;
	}

}
