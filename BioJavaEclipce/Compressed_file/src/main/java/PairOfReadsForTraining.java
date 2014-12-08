
public class PairOfReadsForTraining {
	private ReadsForTraining readsForTr1;
	private ReadsForTraining readsForTr2;
	
	public PairOfReadsForTraining (ReadsForTraining rd1, 
			ReadsForTraining rd2){
		this.readsForTr1 = rd1;
		this.readsForTr2 = rd2;
	}
	
	public ReadsForTraining getReadsForTr1 (){
		return this.readsForTr1;
	}
	
	public ReadsForTraining getReadsForTr2 (){
		return this.readsForTr2;
	}
}
