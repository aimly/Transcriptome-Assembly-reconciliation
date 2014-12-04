
public class AllData {
	private PairOfDataSets datasetForFirstTranscriptome;
	private PairOfDataSets datasetForSecondTranscriptome;
	
	public AllData (PairOfDataSets data1, PairOfDataSets data2){
		this.datasetForFirstTranscriptome = data1;
		this.datasetForSecondTranscriptome = data2;
	}
	
	public PairOfDataSets getDatasetForFirstTranscriptome(){
		return this.datasetForFirstTranscriptome;
	}
	
	public PairOfDataSets getDatasetForSecondTranscriptome(){
		return this.datasetForSecondTranscriptome;
	}
}
