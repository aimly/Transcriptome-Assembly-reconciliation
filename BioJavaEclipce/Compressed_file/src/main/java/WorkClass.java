
public class WorkClass {
	public static Transcriptome work(AssembliesSimilarityRefiner simref,
			Classifiers classifiers,
			Params params,
			WorkMode workMode,
			TranscriptomeAssembly tr1,
			TranscriptomeAssembly tr2,
			PairOfReadsForTraining pair,
			Assignment asgn,
			WorkWithFiles workWithFiles) throws Exception{
		
		WorkWithData dataForClassifier1 = new WorkWithData(pair.getReadsForTr1());
		
		
		WorkWithData dataForClassifier2 = new WorkWithData(pair.getReadsForTr2());
		
		return workMode.work(simref, 
				classifiers, 
				params,
				tr1,
				dataForClassifier1,
				tr2,
				dataForClassifier2,
				asgn, 
				workWithFiles);
	};
}
