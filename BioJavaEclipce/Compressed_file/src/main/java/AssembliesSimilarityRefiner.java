import java.io.IOException;


public interface AssembliesSimilarityRefiner {
	public Transcriptome getSimilarTranscripts (Assignment asgn, double level)throws IOException;
}
