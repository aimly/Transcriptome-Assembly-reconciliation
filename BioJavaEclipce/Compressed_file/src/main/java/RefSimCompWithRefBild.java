
public class RefSimCompWithRefBild implements RefSimComp {

	@Override
	public void computeSimilarity(
			TranscriptomeSimilarityComputer transcriptomeSimComp,
			ReturnOfWorkMode resTranscriptome, WorkWithFiles workWithFiles,
			InputTranscriptomes inpTr,
			TranscriptSimilarityComputer trSim,
			AssembliesSimilarityRefiner asmSimRef) throws Exception {
		
		TranscriptomeSimilarity simTr1 = new TranscriptomeSimilarity (inpTr.getAsgnOfAssembly1AndRef(), 
				transcriptomeSimComp);
		TranscriptomeSimilarity simTr2 = new TranscriptomeSimilarity(inpTr.getAsgnOfAssembly2AndRef(), 
				transcriptomeSimComp);
		TranscriptomeSimilarity simRef = 
				new TranscriptomeSimilarity(resTranscriptome.getRefinedTranscript(), 
						inpTr.getRef(), 
						 asmSimRef, trSim, transcriptomeSimComp, workWithFiles);
        /*
         * Портянка с выводом;
         * можно сделать и поумнее, 
         * но этого хватает, чтобы результаты увидеть
         */
		
		
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

}
