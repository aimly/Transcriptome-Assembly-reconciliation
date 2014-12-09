
public class PairOfReadsForTraining {
	private ReadsForTraining readsForTr1;
	private ReadsForTraining readsForTr2;
	
	public PairOfReadsForTraining (ReadsForTraining rd1, 
			ReadsForTraining rd2){
		this.readsForTr1 = rd1;
		this.readsForTr2 = rd2;
	}
	
	public PairOfReadsForTraining(InputTranscriptomes inpTr,
			TranscriptomeAssembly linksTr1, 
			TranscriptomeAssembly linksTr2,
			Params prms, 
			WorkWithFiles workWithFile) throws ClassNotFoundException, Exception {
		
		ReadsForTraining reads1 = GoodReadsCreator.createGoodReadsSet(inpTr, linksTr1, linksTr2, prms, workWithFile);
		this.readsForTr1 = reads1;
		ReadsForTraining reads2 = GoodReadsCreator.createGoodReadsSet(inpTr, linksTr2, linksTr1, prms, workWithFile);
		this.readsForTr2 = reads2;
	}

	public ReadsForTraining getReadsForTr1 (){
		return this.readsForTr1;
	}
	
	public ReadsForTraining getReadsForTr2 (){
		return this.readsForTr2;
	}
	
	
}
