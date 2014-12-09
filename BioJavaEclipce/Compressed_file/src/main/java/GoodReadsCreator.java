import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class GoodReadsCreator {
	
	public static ReadsForTraining createGoodReadsSet(InputTranscriptomes inpTr,
			TranscriptomeAssembly tr1,
			TranscriptomeAssembly tr2,
			Params params,
			WorkWithFiles workWithFiles) throws Exception, ClassNotFoundException{
		System.out.println("Start creating good reads for " + inpTr.getAssembly1().getNameOfSet()
				+ " and " + inpTr.getAssembly2().getNameOfSet());
		ReadsForTraining reads = (ReadsForTraining)workWithFiles.read(workWithFiles.getGoodReadsID(),
				inpTr.getAssembly1().getNameOfSet(), inpTr.getAssembly2().getNameOfSet(),
				params.getTB(), params.getBB(), null, null);
		ReadsForTraining reads2 = new ReadsForTraining();
		if (inpTr.getRef() != null)
			reads2 = GoodReadsCreator.getSets(inpTr.getAsgnOfAssembly1AndRef(), 
					inpTr.getAsgnOfAssembly2AndRef(), tr1, tr2, params);
		
		reads.merge(reads2);
		reads.cut(params.getMaxCountOfSet1());
		workWithFiles.writeToFile(workWithFiles.getGoodReadsID(), 
				reads, 
				inpTr.getAssembly1().getNameOfSet(), 
				inpTr.getAssembly2().getNameOfSet(), 
				params.getTB(), 
				params.getBB(), null, null, 1);
		System.out.println("Finish!");
		return reads;
	}
	
	private static ReadsForTraining getSets (Assignment trAssignment1,
			Assignment trAssignment2,
			TranscriptomeAssembly tr1,
			TranscriptomeAssembly tr2,
			Params params) throws Exception{
		ReadsForTraining reads = new ReadsForTraining();
		
		if (trAssignment1 != null && trAssignment2 != null){
			ArrayList<Read> setClass1 = new ArrayList<Read>();
			ArrayList<Read> setClass2 = new ArrayList<Read>();
			
			ArrayList<Read> set1 = GoodReadsCreator.getSuitableSet(tr1, 
					trAssignment1, trAssignment2, params);
			
			ArrayList<Read> set2 = GoodReadsCreator.getSuitableSet(tr2, 
					trAssignment1, trAssignment2, params);
			
			while (!set1.isEmpty() && !set2.isEmpty()) {
				Read rd1 = set1.get(set1.size()-1);
				Read rd2 = set2.get(set2.size()-1);
				if (rd1.compareTo(rd2) > 0){
					setClass2.add(set1.get(set1.size()-1));
					set1.remove(set1.size()-1);
				}
				else if (rd1.compareTo(rd2) < 0){
					setClass2.add(set2.get(set2.size()-1));
					set2.remove(set2.size()-1);
				}
				else {
					setClass1.add(set1.get(set1.size()-1));
					set1.remove(set1.size()-1);
					set2.remove(set2.size()-1);
				}
			}
			
			if (!set1.isEmpty())
				setClass2.addAll(set1);
			if (!set2.isEmpty())
				setClass2.addAll(set2);
			reads = new ReadsForTraining(setClass1, setClass2);
		}
		return reads;
	}

	private static ArrayList<Read> getSuitableSet(TranscriptomeAssembly tr1,
			Assignment trAssignment1, 
			Assignment trAssignment2,
			Params params) throws IOException {
		ArrayList<Read> set1 = new ArrayList<Read>();
		set1 = (tr1.getNameOfTranscriptome() == trAssignment1.getNameOfSet1() || tr1.getNameOfTranscriptome() == trAssignment1.getNameOfSet2()) ?
				tr1.getReads(trAssignment1.getUpper(params.getTB(), 
						tr1.getNameOfTranscriptome())) : 
				tr1.getReads(trAssignment2.getUpper(params.getTB(), 
						tr1.getNameOfTranscriptome()));
		Collections.sort(set1);
		return set1;
	}

	
}
