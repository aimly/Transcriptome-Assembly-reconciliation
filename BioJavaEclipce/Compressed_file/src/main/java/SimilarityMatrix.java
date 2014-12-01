import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;



public class SimilarityMatrix {
	private double[][] sim;
	private Transcriptome tr1;
	private Transcriptome tr2;
	
	public SimilarityMatrix(Transcriptome tr1, 
			Transcriptome tr2, 
			String method) throws IOException{
		
		this.tr1 = tr1;
		this.tr2 = tr2;
		
		if (method == "NW"){
			NWSimilarity nw = new NWSimilarity();
			sim = nw.computeSimilarity(tr1, tr2);
		}
		else {
			System.out.println("Unknown method");
		}
	}
	
	public double[][] getMatrix (){
		return this.sim;
	}
	
	public Transcriptome getFirstTranscriptome (){
		return this.tr1;
	}
	
	public Transcriptome getSecondTranscriptome (){
		return this.tr2;
	}
	
	/*
	 * 
	 *  Some shitcode for debugging
	 * 
	 *
	 */
	
	
	public void searchErrors () throws IOException{
		HashMap<Integer, Integer> errorsForTr1 = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> errorsForTr2 = new HashMap<Integer, Integer>();
		
		System.out.println("Similarity matrix of transcriptomes:");
		System.out.println(tr1.getNameOfSet());
		System.out.println(tr1.getAllSeq().size());
		System.out.println(sim.length);
		System.out.println(tr2.getNameOfSet());
		System.out.println(tr2.getAllSeq().size());
		System.out.println(sim[0].length);
		
		for (int i = 0; i < tr1.getAllSeq().size(); i++){
			for (int j = 0; j < tr2.getAllSeq().size(); j++){
				if (sim[i][j] < 0 || sim[i][j] > 1){
					
					if (errorsForTr1.containsKey(i)){
						Integer k = errorsForTr1.get(i);
						errorsForTr1.put(i, k + 1);
					} else
						errorsForTr1.put(i, 1);
					
					if (errorsForTr2.containsKey(j)){
						Integer k = errorsForTr2.get(j);
						errorsForTr2.put(j, k + 1);
					} else
						errorsForTr2.put(j, 1);
					
				}
			}
		}
		
		File errTr1 = new File(tr1.getNameOfSet() + " errored sequences.fa" );
		File errTr2 = new File(tr2.getNameOfSet()+ " errored sequences.fa");
		FileWriter writeFile1 = new FileWriter(errTr1);;
		FileWriter writeFile2 = new FileWriter(errTr2);
		
		HashSet<String> set1 = new HashSet<String>();
		HashSet<String> set2 = new HashSet<String>();
		
		System.out.println("Errors found:");
		for (Integer index : errorsForTr1.keySet()){
			if (errorsForTr1.get(index) > 0){
				System.out.println("i = " + index);
				System.out.println(tr1.getAllSeq().toArray()[index]);
				writeFile1.write(">Sequence " + index
						+ "\n");
				writeFile1.write(tr1.getAllSeq().toArray()[index] + "\n");
				set1.add(tr1.getAllSeq().toArray()[index].toString());
			}
		}
		
		for (Integer index : errorsForTr2.keySet()){
			if (errorsForTr2.get(index) > 0){
				System.out.println("j = " + index);
				System.out.println(tr2.getAllSeq().toArray()[index]);
				writeFile2.write(">Sequence " + index
						+ "\n");
				writeFile2.write(tr2.getAllSeq().toArray()[index] + "\n");
				set2.add(tr2.getAllSeq().toArray()[index].toString());
			}
		}
		
		writeFile1.close();
		writeFile2.close();
		
		System.out.println("Shars for strings in set 1");
		Iterator<String> i1 = set1.iterator();
		while (i1.hasNext()){
			String index = i1.next();
			HashMap<String, Integer> chars = new HashMap<String, Integer>();
			for(int i = 0; i < index.length(); i++){
				if (chars.containsKey(index.substring(i, i+1))){
					Integer count = chars.get(index.substring(i, i+1));
					chars.put(index.substring(i, i+1), count + 1);
				} else
					chars.put(index.substring(i, i+1), 1);
			}
			System.out.println("string:");
			for(Map.Entry<String, Integer> e : chars.entrySet()) {
	            System.out.println("Char: " + e.getKey() + "\t num: " + e.getValue());
	        }
			
		}
		

		System.out.println("Shars for strings in set 2");
		Iterator<String> i2 = set2.iterator();
		while (i2.hasNext()){
			String index = i2.next();
			HashMap<String, Integer> chars = new HashMap<String, Integer>();
			for(int i = 0; i < index.length(); i++){
				if (chars.containsKey(index.substring(i, i+1))){
					Integer count = chars.get(index.substring(i, i+1));
					chars.put(index.substring(i, i+1), count + 1);
				} else
					chars.put(index.substring(i, i+1), 1);
			}
			System.out.println("string:");
			for(Map.Entry<String, Integer> e : chars.entrySet()) {
	            System.out.println("Char: " + e.getKey() + "\t num: " + e.getValue());
	        }
			
		}
		
		
		System.out.println("set1.size()");
		System.out.println(set1.size());
		System.out.println(set1.toString());
		System.out.println("set2.size()");
		System.out.println(set2.size());
		System.out.println(set2.toString());
		
		System.out.println("Similarity matrix of transcriptomes:");
		System.out.println(tr1.getNameOfSet());
		System.out.println(tr1.getAllSeq().size());
		System.out.println(sim.length);
		System.out.println(tr2.getNameOfSet());
		System.out.println(tr2.getAllSeq().size());
		System.out.println(sim[0].length);
	}
}
