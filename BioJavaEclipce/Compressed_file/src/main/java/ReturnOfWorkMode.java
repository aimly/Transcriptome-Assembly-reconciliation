
public class ReturnOfWorkMode {
	private final String workModeWithoutTranscriptome = "NO_TRANSCRIPT";
	private final String workModeWithTranscriptome = "HAVE_TRANSCRIPT";
	
	private String workModeName;
	private Transcriptome refinedTranscriptome;
	
	public ReturnOfWorkMode (){
		this.workModeName = this.workModeWithoutTranscriptome;
	}
	
	public ReturnOfWorkMode(Transcriptome tr){
		this.workModeName = this.workModeWithTranscriptome;
		this.refinedTranscriptome = tr;
	}
	
	public Transcriptome getRefinedTranscript(){
		return this.refinedTranscriptome;
	}
	
	public String getWorkModeName(){
		return this.workModeName;
	}

	public String getWorkModeWithTranscriptome() {
		return this.workModeWithTranscriptome;
	}
}
