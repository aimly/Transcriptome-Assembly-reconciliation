import java.util.ArrayList;

import weka.classifiers.functions.LibSVM;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import weka.core.Instance;
import weka.core.Instances;


public class ADrefiner implements MllLibRefiner {

	@Override
	public Transcriptome MLLibRefine(Assignment trAssignment1,
			Assignment trAssignment2, 
			TranscriptomeAssembly tr1,
			TranscriptomeAssembly tr2, 
			double topBound, 
			double bottomBound,
			int maxCountOfSet1,
			double percentage,
			String params) throws Exception {
		
		
		int numOfTrees = 100;
		int numOfFeatures = 10;
		int numOfSlots = 20;
		
		
		System.out.println("MLLibRefiner start working");
		
		System.out.println("Vectorizer1");
		
		Vectorizer vectorizer1 = new Vectorizer(trAssignment1,
				trAssignment2,
				tr1,
				tr2,
				topBound,
				bottomBound,
				maxCountOfSet1);
		System.out.println("Vectorizer 1 created");
		System.out.println("Instance1");
		
		Instances data1 = DatasetCreator.createInstances ( trAssignment1,
				trAssignment2,
				tr1,
				tr2,
				topBound,
				bottomBound,
				vectorizer1,
				maxCountOfSet1).getInstsForClass1();
		
		System.out.println("Instance1 finished");
		
		LibSVM classifier1 = new LibSVM();
		System.out.println("Vectorizer2");
		Vectorizer vectorizer2 = new Vectorizer(trAssignment2,
				trAssignment1,
				tr2,
				tr1,
				topBound,
				bottomBound,
				maxCountOfSet1);
		
		System.out.println("Vectorizer2 finished");
		System.out.println("Instance2");
		
		Instances data2 = DatasetCreator.createInstances ( trAssignment2,
				trAssignment1,
				tr2,
				tr1,
				topBound,
				bottomBound,
				vectorizer2,
				maxCountOfSet1).getInstsForClass1();
		
		System.out.println("Instance2 finished");
		long timeout1, timeout2;
		
		LibSVM classifier2 = new LibSVM();
		

		System.out.println("MLLibRefiner made sets and another shit");
		
		
		/*
		 * Recomended params:
		 * "-I 100 -K 10 -S 1 -depth 0 -num-slots 20"
		 * 
		 */
		
		
		String path = "/home/volta/another/test/Classifiers/";
		
		String tr1Name = trAssignment1.getNameOfSet1();
    	
		String tr2Name = trAssignment2.getNameOfSet1();
		
		File file1 = new File(path + 
				tr1Name + 
				" against " + tr2Name + 
				" good reads classifier AD tb" + topBound
				+ " bb" + bottomBound);

		File file2 = new File(path + 
				tr2Name + 
				" against " + tr1Name + 
				" good reads classifier AD tb" + topBound
				+ " bb" + bottomBound);
		
		if (file1.exists() && file2.exists()){
			
			FileInputStream fis1 = new FileInputStream(path + 
					tr1Name + 
					" against " + tr2Name + 
					" good reads classifier AD tb" + topBound
					+ " bb" + bottomBound);
			
			ObjectInputStream in1 = new ObjectInputStream(fis1);
			classifier1 = (LibSVM) in1.readObject();
			System.out.println("Classifier 1 red");
			in1.close();
			
			FileInputStream fis2 = new FileInputStream(path + 
					tr2Name + 
					" against " + tr1Name + 
					" good reads classifier AD tb" + topBound
					+ " bb" + bottomBound);
			
			ObjectInputStream in2 = new ObjectInputStream(fis2);
			classifier2 = (LibSVM) in2.readObject();
			System.out.println("Classifier 1 red");
			in2.close();
		}
		else {
		
			timeout1= System.currentTimeMillis();
			classifier1.setOptions(weka.core.Utils.splitOptions(params));
			
			classifier1.buildClassifier(data1);
			
			timeout1= System.currentTimeMillis() - timeout1;
			System.out.println("Classifier 1 bilded");
			System.out.println("Timeout: " + timeout1);
			
			timeout2 = System.currentTimeMillis();
			classifier2.setOptions(weka.core.Utils.splitOptions(params));
			classifier2.buildClassifier(data2);
			timeout2 = System.currentTimeMillis() - timeout2;
			System.out.println("Classifier 2 bilded");
			System.out.println("Timeout: " + timeout2);
			
			
			if (file1.exists()){
				FileWriter fstream1 = new FileWriter(path + 
						tr1Name + 
						" against " + tr2Name + 
						" good reads classifier AD tb" + topBound
						+ " bb" + bottomBound);
				BufferedWriter out1 = new BufferedWriter(fstream1); 
				out1.write("");
				out1.close(); 
			}
			if (file2.exists()){
				FileWriter fstream1 = new FileWriter(path + 
						tr2Name + 
						" against " + tr1Name + 
						" good reads classifier AD tb" + topBound
						+ " bb" + bottomBound);
				BufferedWriter out1 = new BufferedWriter(fstream1); 
				out1.write("");
				out1.close(); 
			}
			
			FileOutputStream fos1 = new FileOutputStream(path + 
					tr1Name + 
					" against " + tr2Name + 
					" good reads classifier AD tb" + topBound
					+ " bb" + bottomBound);
			ObjectOutputStream out1 = new ObjectOutputStream(fos1);
			out1.writeObject(classifier1);
			out1.flush();
			out1.close();
			
			FileOutputStream fos2 = new FileOutputStream(path + 
					tr2Name + 
					" against " + tr1Name + 
					" good reads classifier AD tb" + topBound
					+ " bb" + bottomBound);
			ObjectOutputStream out2 = new ObjectOutputStream(fos2);
			out2.writeObject(classifier2);
			out2.flush();
			out2.close();
			
			System.out.println("MLLibRefiner bilded classifiers");
		
		}
		Transcriptome results = new Transcriptome("ResultsOfMLAnalyze");
		/*
		 * Добавление в итоговый транскриптом транскрипты из первого набора
		 * (первый цикл) и из второго набора (второй цикл)
		 * blabla - количество отобранных транскриптов
		 */
		System.out.println("MLLibRefiner start learning ......");
		System.out.println("First transcript getting");
		
		int inst = 0;
		int goodReads = 0;
		
		int blabla = 0;   
		for (Transcript indexOfTr : tr1.getAllTranscripts()){
			ArrayList<Read> set = tr1.getReadsOfTr(indexOfTr);
			Instances dataOfReads = 
					DatasetCreator.createInstanceFromArray(set, 
							vectorizer1);
			double count = 0;
			double goodCount = 0;
			
			/*
			 * так как класс хороших ридов классификатор выведет 0, 
			 * число хороших ридов - это (count - goodCount)
			 */
			
			for (Instance ind : dataOfReads){
				goodCount += classifier1.classifyInstance(ind);
				count++;
				goodReads += classifier1.classifyInstance(ind) - 1;
			}
			
			if ((count - goodCount)/count > percentage){
				results.addTranscript(indexOfTr);
				System.out.println("Transcript added!");
				blabla++;
			}
			inst++;
			if ( inst%60 == 1){
				System.out.println(inst + " transcripts analysed");
			}
		}
		
		System.out.println("Good reads detecte "
				+ "in first transcriptome: " + goodReads);
		System.out.println("Second transcript getting");
		
		inst = 0;
		goodReads = 0;
		
		for (Transcript indexOfTr : tr2.getAllTranscripts()){
			ArrayList<Read> set = tr2.getReadsOfTr(indexOfTr);
			Instances dataOfReads = 
					DatasetCreator.createInstanceFromArray(set, 
							vectorizer2);
			double count = 0;
			double goodCount = 0;
			
			/*
			 * так как класс хороших ридов классификатор выведет 0, 
			 * число хороших ридов - это (count - goodCount)
			 */
			
			for (Instance ind : dataOfReads){
				goodCount += classifier2.classifyInstance(ind);
				count++;
				goodReads += classifier2.classifyInstance(ind) - 1;
			}
			
			if ((count - goodCount)/count > percentage){
				results.addTranscript(indexOfTr);
				System.out.println("Transcript added!");
				blabla++;
			}
			inst++;
			if ( inst%60 == 1){
				System.out.println(inst + " transcripts analysed");
			}
		}
		System.out.println("Good reads detecte "
				+ "in second transcriptome: " + goodReads);
		System.out.println("MLLib finished work");
		System.out.println("Num of added transcripts is  " + blabla);
		
		return results;
		
		
	}

}
