public interface MllLibRefiner {
	public Transcriptome MLLibRefine(Assignment trAssignment1,
			Assignment trAssignment2,
			TranscriptomeAssembly tr1,
			TranscriptomeAssembly tr2,
			double topBound,
			double bottomBound,
			int maxCountOfSet,
			double percentage,
			String params) throws Exception;
}
