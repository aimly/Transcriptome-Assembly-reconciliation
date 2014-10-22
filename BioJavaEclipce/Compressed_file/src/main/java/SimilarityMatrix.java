
public class SimilarityMatrix {
	protected double[][] sim;
	protected Transcriptome tr1;
	protected Transcriptome tr2;
	
	public SimilarityMatrix(Transcriptome tr1, Transcriptome tr2, String method){
		
		this.tr1 = tr1;
		this.tr2 = tr2;
		
		if (method == "NW"){
			NWSimilarity nw = new NWSimilarity();
			sim = nw.computeSimilarity(tr1, tr2);
		}
	}
}
