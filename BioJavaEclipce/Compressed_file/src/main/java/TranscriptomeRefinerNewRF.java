
public class TranscriptomeRefinerNewRF implements TranscriptomeRefiner {

	@Override
	public Transcriptome refiner(Transcriptome transcriptome1,
			Transcriptome transcriptome2, Assignment trAssignment1,
			Assignment trAssignment2, TranscriptomeAssembly tr1,
			TranscriptomeAssembly tr2, double topBound, double bottomBound,
			Assignment asgn, double level, int maxCountOfSet1,
			double percentage, String params) throws Exception {
		// TODO Auto-generated method stub
		ClassicAssemblerSimilarityRefiner refiner1 = 
				new ClassicAssemblerSimilarityRefiner();
		Transcriptome transcriptomeOfSimilarTranscripts = 
				refiner1.getSimilarTranscripts(asgn, level);
		
		
		Transcriptome transcriptomeOfMLLibWork1 = 
				getTranscriptsFromML();
		Transcriptome transcriptomeOfMLLibWork2 = 
				getTranscriptsFromML();
		transcriptomeOfMLLibWork1.merge(transcriptomeOfMLLibWork2);
		
		transcriptomeOfSimilarTranscripts.
					merge(transcriptomeOfMLLibWork);
		
		return transcriptomeOfSimilarTranscripts ;
	}

}
