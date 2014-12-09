
public class InputTranscriptomesWithRef extends InputTranscriptomes {
	private Transcriptome reference;
	private Assignment asgnOfTr1andRef;
	private Assignment asgnOfTr2andRef;
	
	public InputTranscriptomesWithRef(String fileOfTranscriptome1,
			String fileOfTranscriptome2,
			String fileOfRef,
			TranscriptSimilarityComputer trSim,
			WorkWithFiles workWithFiles) throws Exception{
		
		super(fileOfTranscriptome1, fileOfTranscriptome2, 
				trSim, workWithFiles);
		this.reference = 
        		new Transcriptome(Fasta.read(fileOfRef, 
        				"sequences"), 
        				WorkWithFiles.getNameOfFile(fileOfRef));
		
		this.asgnOfTr1andRef = 
        		new Assignment(this.getAssembly1(), this.reference, 
        				trSim, workWithFiles);
		
		this.asgnOfTr2andRef = 
        		new Assignment(this.getAssembly2(), this.reference, 
        				trSim, workWithFiles);
	}
	
	public Transcriptome getRef(){
		return this.reference;
	}
	
	public Assignment getAsgnOfAssembly1AndRef (){
		return this.asgnOfTr1andRef;
	}
	
	public Assignment getAsgnOfAssembly2AndRef (){
		return this.asgnOfTr2andRef;
	}
	
}
