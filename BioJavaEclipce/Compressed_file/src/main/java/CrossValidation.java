import java.io.IOException;


public interface CrossValidation {
	public void crossValidate (Transcriptome transcriptome1,
			Transcriptome transcriptome2, 
			Assignment trAssignment1,
			Assignment trAssignment2, 
			TranscriptomeAssembly tr1,
			TranscriptomeAssembly tr2, 
			double topBound, 
			double bottomBound,
			Assignment asgn, 
			double level,
			int maxCountOfSet1,
			double percentage,
			String params,
			int numOfFolds) throws ClassNotFoundException, IOException, Exception;
}
