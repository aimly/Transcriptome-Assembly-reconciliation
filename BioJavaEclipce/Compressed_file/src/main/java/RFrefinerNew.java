
public class RFrefinerNew implements MllLibRefiner {

	@Override
	public Transcriptome getGoodTranscripts (Assignment trAssignment1,
			Assignment trAssignment2, TranscriptomeAssembly tr1,
			TranscriptomeAssembly tr2, double topBound, double bottomBound,
			int maxCountOfSet, double percentage, String params)
			throws Exception {
		
		Instances inst = DatasetCreator.createInstances();
		RandomForest classifier = new RandomForest();
		
		return null;
	}

}
