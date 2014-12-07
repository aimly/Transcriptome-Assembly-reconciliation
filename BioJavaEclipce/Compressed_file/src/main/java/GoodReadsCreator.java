import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import com.google.common.collect.HashMultimap;


public class GoodReadsCreator {
	public static ReadsForTraining createSet(Assignment trAssignment1,
			Assignment trAssignment2,
			TranscriptomeAssembly tr1,
			TranscriptomeAssembly tr2,
			double topBound,
			double bottomBound,
			int maxCountOfSet1) throws Exception, ClassNotFoundException{

		System.out.println("______________________________");
		System.out.println("Start creating good reads ");
		
		
		String path = "/home/volta/another/test/Good reads/";
		
		String goodReadsForTr1 = trAssignment1.getNameOfSet1();
    	
		String badReadsForTr2 = trAssignment2.getNameOfSet1();
		
		File file = new File(path + 
				goodReadsForTr1 + 
				" against " + badReadsForTr2 + 
				" good reads");

		ArrayList<Read> setClass1Calculated = new ArrayList<Read>();
		
		if (file.exists()){
			
			FileInputStream fis = new FileInputStream(path + 
					goodReadsForTr1 + 
					" against " + badReadsForTr2 + 
					" good reads tp" + topBound + 
					" bb" + bottomBound);
			
			ObjectInputStream in = new ObjectInputStream(fis);
			
			setClass1Calculated = (ArrayList<Read>) in.readObject();
			
			System.out.println("Good reads for " + goodReadsForTr1
					+ " against " + badReadsForTr2 + " red");
			System.out.println("Count: " + setClass1Calculated.size());
			
			in.close();
		}
		
		
		ArrayList<Read> setClass1 = new ArrayList<Read>();
		ArrayList<Read> setClass2 = new ArrayList<Read>();
		
		ArrayList<Read> set1 = 
				tr1.getReads(trAssignment1.getUpper(topBound));
		Collections.sort(set1);
		
		ArrayList<Read> set2 = 
				tr2.getReads(trAssignment2.getLower(bottomBound));
		Collections.sort(set2);
		System.out.println("Pre-sets created");
		System.out.println("set1: " + set1.size());
		System.out.println("set2: " + set2.size());
		int iter = 0;
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
			if (iter%50000 == 1){
				System.out.println(iter + " reads analysed");
			}
			iter++;
		}
		
		for (Read index : setClass1Calculated)
			if (!setClass1.contains(index))
				setClass1.addAll(setClass1Calculated);
		
		System.out.println("Another pre-sets");
		System.out.println("set1: " + setClass1.size());
		System.out.println("set2: " + setClass2.size());
		
		if (file.exists()){
			FileWriter fstream1 = new FileWriter(path + 
					goodReadsForTr1 + 
					" against " + badReadsForTr2 + 
					" good reads tp" + topBound + 
					" bb" + bottomBound);
			BufferedWriter out1 = new BufferedWriter(fstream1); 
			out1.write("");
			out1.close(); 
		}
		
		FileOutputStream fos = new FileOutputStream(path + 
				goodReadsForTr1 + 
				" against " + badReadsForTr2 + 
				" good reads tp" + topBound + 
				" bb" + bottomBound);
		ObjectOutputStream out = new ObjectOutputStream(fos);
		out.writeObject(setClass1);
		out.flush();
		out.close();
		
		System.out.println("max count of set1 " + maxCountOfSet1);
		while (setClass1.size() > maxCountOfSet1)
			setClass1.remove(setClass1.size() - 1);
		
		int maxCountOfSet2 = setClass1.size();
		
		while (setClass2.size() > maxCountOfSet2)
			setClass2.remove(setClass2.size() - 1);
		
		System.out.println("Sets created");
		System.out.println("setClass1: " + setClass1.size());
		System.out.println("setClass2: " + setClass2.size());
		
		ReadsForTraining finalSets = new ReadsForTraining(setClass1, setClass2);

		System.out.println("Finisch creating good reads ");

		System.out.println("______________________________");
		return finalSets;
	}
	
	
	/////////////////////////////////////////////////////////////
	
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
				params.getTB(), params.getBB());
		
		ReadsForTraining reads2 = GoodReadsCreator.getSets(trAssignment1, 
				trAssignment2, tr1, tr2, params);
		System.out.println("Start merging");
		reads.merge(reads2);
		System.out.println("Write to file");
		workWithFiles.writeToFile("goodReads", reads, 
				trAssignment1.getNameOfSet1(), 
				trAssignment2.getNameOfSet1(), 
				params.getTB(), 
				params.getBB());
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
