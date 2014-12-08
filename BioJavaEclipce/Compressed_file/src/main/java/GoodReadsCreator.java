import java.util.ArrayList;
import java.util.Collections;


public class GoodReadsCreator {
	
	public static ReadsForTraining createGoodReadsSet(Assignment trAssignment1,
			Assignment trAssignment2,
			TranscriptomeAssembly tr1,
			TranscriptomeAssembly tr2,
			Params params,
			WorkWithFiles workWithFiles) throws Exception, ClassNotFoundException{
		System.out.println("Start creating good reads for " + trAssignment1.getNameOfSet1()
				+ " and " + trAssignment2.getNameOfSet2());
		ReadsForTraining reads = (ReadsForTraining)workWithFiles.read("goodReads",
				trAssignment1.getNameOfSet1(), trAssignment2.getNameOfSet1(),
				params.getTB(), params.getBB(), null, null);
		
		ReadsForTraining reads2 = GoodReadsCreator.getSets(trAssignment1, 
				trAssignment2, tr1, tr2, params);
		System.out.println("Start merging");
		reads.merge(reads2);
		System.out.println("Write to file");
		workWithFiles.writeToFile("goodReads", reads, 
				trAssignment1.getNameOfSet1(), 
				trAssignment2.getNameOfSet1(), 
				params.getTB(), 
				params.getBB(), null, null);
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
			
			ArrayList<Read> set1 = 
					tr1.getReads(trAssignment1.getUpper(params.getTB()));
			Collections.sort(set1);
			
			ArrayList<Read> set2 = 
					tr2.getReads(trAssignment2.getLower(params.getBB()));
			Collections.sort(set2);
			
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

	
}
