import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;


public class GoodTranscriptsCreator {

	public static Transcriptome getTranscripts(Classifier classifier,
			DatasetCreator datasetCreator, 
			Params params,
			TranscriptomeAssembly tr1) throws Exception {
		
		int i = 0;
		
		double percentage = params.getPercentage();
		
		Transcriptome results = new Transcriptome("Good");
		for (Transcript indexOfTr : tr1.getAllTranscripts()){
			if (GoodTranscriptsCreator.isGood(indexOfTr, tr1, 
					datasetCreator, classifier, percentage)){
				results.addTranscript(indexOfTr);
				i++;
			}
		}
		System.out.println(i + " transcripts detected");
		return results;
	}
	
	private static boolean isGood(Transcript tr,
			TranscriptomeAssembly trAssem,
			DatasetCreator datasetCreator,
			Classifier classifier,
			double percentage) throws Exception{
		
		ArrayList<Read> set = trAssem.getReadsOfTr(tr);
		Instances dataOfReads = 
				datasetCreator.createInstance(set, 0);
		double count = 0;
		double goodCount = 0;
		
		for (Instance ind : dataOfReads){
			goodCount += classifier.classifyInstance(ind);
			count++;
		}
		
		return ((count - goodCount)/count > percentage);
	}
}
