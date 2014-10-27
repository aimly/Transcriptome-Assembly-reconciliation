import java.io.IOException;


public class SimilarityMatrix {
	private double[][] sim;
	private Transcriptome tr1;
	private Transcriptome tr2;
	
	public SimilarityMatrix(Transcriptome tr1, Transcriptome tr2, String method) throws IOException{
		
		this.tr1 = tr1;
		this.tr2 = tr2;
		
		if (method == "NW"){
			NWSimilarity nw = new NWSimilarity();
			sim = nw.computeSimilarity(tr1, tr2);
		}
		else {
			System.out.println("Unknown method");
		}
	}
	
	public double[][] getMatrix (){
		return this.sim;
	}
	
	public Transcriptome getFirstTranscriptome (){
		return this.tr1;
	}
	
	public Transcriptome getSecondTranscriptome (){
		return this.tr2;
	}
}
