import java.io.IOException;


public interface AssembliesSimilarityRefiner {
	public Transcriptome getSimilarTranscripts (Assignment asgn, Params params)throws IOException;
}
