
public class RefSimCompNoneBild implements RefSimComp {

	@Override
	public void computeSimilarity(
			TranscriptomeSimilarityComputer transcriptomeSimComp,
			ReturnOfWorkMode resTranscriptome, WorkWithFiles workWithFiles,
			InputTranscriptomes inpTr, TranscriptSimilarityComputer trSim,
			AssembliesSimilarityRefiner asmSimRef) throws Exception {
		/*
		 * Realization of interfase, when we don'd hawe reference
		 * or the work mode is CV or bildClassifiers
		 * We mustn't do anythisng in this situations
		 */
		
		System.out.println("");

	}

}
