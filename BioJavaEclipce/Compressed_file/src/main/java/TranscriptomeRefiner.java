
public interface TranscriptomeRefiner {
/*	public Transcriptome AssembliesSimilarityRefine (Transcript firstSet, Transcript secondSet);
	public Transcriptome MLLibRefiner ();
*/
	public Transcriptome refiner(Transcriptome transcriptome1,
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
			String params) throws Exception;
}
