import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class TranscriptomeRefinerWithRF implements TranscriptomeRefiner {

	@Override
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
			String params) throws Exception {
		
		String path = "/home/volta/another/test/Results/";
		
		String transcriptomeName = "results of merging " 
				+ transcriptome1.getNameOfSet() 
				+ " and " + transcriptome2.getNameOfSet()
				+ " with RF tb " + topBound 
				+ " bb " + bottomBound
				+ " set1size " + maxCountOfSet1;
    	
		
		
		File file = new File(path + 
				transcriptomeName);
		
		if (file.exists()){
			FileInputStream fis = new FileInputStream(path + transcriptomeName);
			ObjectInputStream in = new ObjectInputStream(fis);
			
			Transcriptome tr = (Transcriptome) in.readObject();

			in.close();
			System.out.println("Size of refined transcriptome: " + tr.getAllSeq().size());
			return tr;
		}
		
		
		RFrefiner rf = new RFrefiner();
		int maxSize;
		
		if (transcriptome1.getAllSeq().size() > transcriptome2.getAllSeq().size())
			maxSize = transcriptome1.getAllSeq().size();
		else
			maxSize = transcriptome2.getAllSeq().size();
		
		Transcriptome MLtr = rf.getGoodTranscripts (trAssignment1,
				trAssignment2,
				tr1,
				tr2,
				topBound,
				bottomBound,
				maxCountOfSet1,
				percentage,
				params);
		
		ClassicAssemblerSimilarityRefiner asssim = 
				new ClassicAssemblerSimilarityRefiner();
		
		Transcriptome AStr = asssim.getSimilarTranscripts(asgn, level);
		
		if (AStr.getAllSeq().size() >= maxSize)
			return AStr;
		else {
			int size = AStr.getAllSeq().size();
			for (String index : MLtr.getAllSeq()){
				Transcript tr = new Transcript (MLtr.getName(index), index );
				AStr.addTranscript(tr);
				size++;
				if (size >= maxSize){
					return AStr;
				}
			}
			
		}
		
		FileOutputStream fos = new FileOutputStream(path 
				+ transcriptomeName);
		
		ObjectOutputStream out = new ObjectOutputStream(fos);
		out.writeObject(AStr);
		out.flush();
		out.close();
		
		AStr.setTranscriptomeName(transcriptomeName);
		
		return AStr;
	}

}
