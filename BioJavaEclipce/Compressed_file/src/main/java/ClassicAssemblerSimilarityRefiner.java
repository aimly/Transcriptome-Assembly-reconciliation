import java.io.IOException;


public class ClassicAssemblerSimilarityRefiner implements
		AssembliesSimilarityRefiner {

	
	public Transcriptome getSimilarTranscripts(Assignment asgn, Params params) throws IOException {
		double level = params.getLevel();
		Transcriptome trSet = new Transcriptome ("ResultsOfAssemblyAnalysis");
		trSet.addAllTranscript(asgn.getUpper(level));
		System.out.println("AssemblerSimilarityRefiner"
				+ "returned " + trSet.getAllSeq().size() + " transcripts");
		return trSet;
	}

}
