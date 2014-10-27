import java.io.FileNotFoundException;
import java.io.IOException;


public interface TranscriptSimilarityComputer {
	
	public double[][] computeSimilarity(Transcriptome firstSet, Transcriptome secondSet) throws FileNotFoundException, IOException;
	
}
