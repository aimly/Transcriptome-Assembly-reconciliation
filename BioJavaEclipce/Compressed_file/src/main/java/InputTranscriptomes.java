
public abstract class InputTranscriptomes {
	private Transcriptome assembly1;
	private Transcriptome assembly2;
	private Assignment asgnOfTr1andTr2;
	
	public InputTranscriptomes (String fileOfTranscriptome1,
			String fileOfTranscriptome2,
			TranscriptSimilarityComputer trSim,
			WorkWithFiles workWithFiles) throws Exception{
		this.assembly1 = 
				new Transcriptome(Fasta.read(fileOfTranscriptome1, 
						"sequences"), 
						WorkWithFiles.getNameOfFile(fileOfTranscriptome1));
		this.assembly2 = 
        		new Transcriptome(Fasta.read(fileOfTranscriptome2, 
        				"sequences"), 
        				WorkWithFiles.getNameOfFile(fileOfTranscriptome2));
		
		this.asgnOfTr1andTr2 = 
        		new Assignment(this.assembly1, this.assembly2, 
        				trSim, workWithFiles);
	}
	
	public Transcriptome getAssembly1(){
		return this.assembly1;
	}
	
	public Transcriptome getAssembly2(){
		return this.assembly2;
	}
	
	public Transcriptome getRef(){
		return null;
	}
	
	public Assignment getAsgnOfAssemblies (){
		return this.asgnOfTr1andTr2;
	}

	public Assignment getAsgnOfAssembly1AndRef (){
		return null;
	}
	
	public Assignment getAsgnOfAssembly2AndRef (){
		return null;
	}
	
}
