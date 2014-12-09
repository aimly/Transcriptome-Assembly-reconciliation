import java.io.IOException;


public class InputTranscriptomesWithoutRef extends InputTranscriptomes {

	public InputTranscriptomesWithoutRef(String fileOfTranscriptome1,
			String fileOfTranscriptome2, 
			TranscriptSimilarityComputer trSim,
			WorkWithFiles workWithFiles) throws IOException, Exception {
		
		super(fileOfTranscriptome1, fileOfTranscriptome2, 
				trSim, workWithFiles);
		
	}
	

	
}
