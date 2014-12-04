import java.util.ArrayList;


public class ReadsForTraining {
	private ArrayList<Read> readsForClass1;
	private ArrayList<Read> readsForClass2;
	
	public ReadsForTraining(ArrayList<Read> set1,
			ArrayList<Read> set2){
		
		this.readsForClass1 = set1;
		this.readsForClass2 = set2;
		
	}
	
	public ReadsForTraining() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Read> getReadsForClass1() {
		return this.readsForClass1;
	}
	
	public ArrayList<Read> getReadsForClass2() {
		return this.readsForClass2;
	}
}
