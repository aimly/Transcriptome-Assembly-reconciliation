
public class RefinerSimilarityComputer {
	public static void computeSimilarity(TranscriptomeSimilarityComputer transcriptomeSimComp, 
			Transcriptome resTranscriptome, 
			Transcriptome ref, 
			AssembliesSimilarityRefiner asmSimRef,
			TranscriptSimilarityComputer simComp,
			Assignment asForTr1andRef, 
			Assignment asForTr2andRef,
			WorkWithFiles workWithFiles) throws Exception{
		
		TranscriptomeSimilarity simTr1 = new TranscriptomeSimilarity (asForTr1andRef, 
				transcriptomeSimComp);
		TranscriptomeSimilarity simTr2 = new TranscriptomeSimilarity(asForTr2andRef, 
				transcriptomeSimComp);
		TranscriptomeSimilarity simRef = new TranscriptomeSimilarity(resTranscriptome, 
				ref, asmSimRef, simComp, 
				transcriptomeSimComp, workWithFiles);
		
		if (ref != null){
		
	        System.out.println("RESULTS");
	        System.out.println("Oases:");
	        System.out.println("Accuracy: " 
	      		  + simTr1.getAccuracy());
	        System.out.println("Count proportion: " 
	      		  + simTr1.getCountProportion());
	        System.out.println("");
	        System.out.println("Trinity");
	        System.out.println("Accuracy: " 
	      		  + simTr2.getAccuracy());
	        System.out.println("Count Proportion: " 
	      		  + simTr2.getCountProportion());
	        System.out.println("");
	        System.out.println("Our refiner: ");
	        System.out.println("Accuracy: " 
	      		  + simRef.getAccuracy());
	        System.out.println("Count Proportion: " 
	      		  + simRef.getCountProportion());
	        System.out.println(""); 
		}
		else {
			System.out.println("No referense transcriptome");
		}
		
	}
}
