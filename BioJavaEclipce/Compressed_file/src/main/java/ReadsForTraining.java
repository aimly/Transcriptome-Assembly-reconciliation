import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;


public class ReadsForTraining implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Read> readsForClass1;
	private ArrayList<Read> readsForClass2;
	
	public ReadsForTraining(ArrayList<Read> set1,
			ArrayList<Read> set2){
		
		this.readsForClass1 = set1;
		this.readsForClass2 = set2;
		
	}
	
	public ReadsForTraining() {
		this.readsForClass1 = new ArrayList<Read>();
		this.readsForClass2 = new ArrayList<Read>();
	}

	public ArrayList<Read> getReadsForClass1() {
		return this.readsForClass1;
	}
	
	public ArrayList<Read> getReadsForClass2() {
		return this.readsForClass2;
	}

	public void merge(ReadsForTraining reads) {
		if (reads != null && reads.getReadsForClass1().size() != 0){
			this.readsForClass1 = ReadsForTraining.mergeSets(readsForClass1, 
					reads.readsForClass1);
			
		}
		if (reads != null && reads.getReadsForClass2().size() != 0)
			this.readsForClass2 = ReadsForTraining.mergeSets(readsForClass2, 
					reads.readsForClass2);
			
	}
	
	private static ArrayList<Read> mergeSets (ArrayList<Read> set1,
			ArrayList<Read> set2){
		ArrayList<Read> res = new ArrayList<Read>();
		Collections.sort(set1);
		Collections.sort(set2);
		while (!set1.isEmpty() && !set2.isEmpty()) {
			Read rd1 = set1.get(set1.size()-1);
			Read rd2 = set2.get(set2.size()-1);
			if (rd1.compareTo(rd2) > 0){
				res.add(set1.get(set1.size()-1));
				set1.remove(set1.size()-1);
			}
			else if (rd1.compareTo(rd2) < 0){
				res.add(set2.get(set2.size()-1));
				set2.remove(set2.size()-1);
			}
			else {
				res.add(set1.get(set1.size()-1));
				set1.remove(set1.size()-1);
				set2.remove(set2.size()-1);
			}
		}
		if (!set1.isEmpty())
			res.addAll(set1);
		if (!set2.isEmpty())
			res.addAll(set2);
		return res;
	}

	public void cut(int maxCountOfSet1) {
		
		while (this.readsForClass1.size() > maxCountOfSet1)
			this.readsForClass1.remove(this.readsForClass1.size() - 1);
		
		while (this.readsForClass2.size() > maxCountOfSet1)
			this.readsForClass2.remove(this.readsForClass2.size() - 1);
		
	}
	

}
