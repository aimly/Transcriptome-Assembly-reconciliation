import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

import weka.classifiers.Classifier;


public class TrainingDataGenerator {

	public static void main(String[] args) throws Exception {
		
		//_________________COMAND_LINE_PARAMS______________________
    	

		/*
		 * 
		 * обязательные:
		 * --md <String> - mode ("CV", "bild" or "getClassifiers")
		 * --tb <double> - top bound
		 * --bb <double> - bottom bound
		 * --maxcount <int> - max cound of reads for good classes
		 * --cl <String> - type of classifier ("RF", "SVM" or "AD")
		 * --opt <String> - options for classifier (in weka)
		 * обязательные для режима "bild":
		 * --lvl <Double> - level for add similar transcripts
		 * --ps <Double> - part of good reads in transcript to add transcript
		 * обязательные для режима "CV":
		 * -f <int> - num of folds for CV
		 * 
		 * Params 
		 * --md bild --tb 0.85 --bb 0.35 --maxCount 100000 --cl RF --opt "-I 100 -K 100 -S 1 -depth 0 -num-slots 20" --lvl 0.6 --ps 0.03
		 * --md CV --tb 0.85 --bb 0.35 --maxCount 100000 --cl RF --opt "-I 100 -K 100 -S 1 -depth 0 -num-slots 20" -f 10
		 */
		
    	
  	
    	Params prms = NaiveComandLineParser.parse(args);
    	WorkWithFiles workWithFile = new WorkWithFiles();
		
		
    	String mode = prms.getMode();
    	double topBound = prms.getTB();
    	double bottomBound = prms.getBB();
    	int maxCountOfSet1 = prms.getMaxCountOfSet1();
		String classifier = prms.getClassifier();
		String params = prms.getParamsForClassifier();
        double level = prms.getLevel();
		double percentage = prms.getPercentage();
		int numOfFolds = prms.getFolds();
    	
		
		//_________________________________________________________
		
    	String fileOfTranscriptome1 = workWithFile.getPathForFile(prms.getTr1FileName(), "tr");
    	String fileOfTranscriptome2 = workWithFile.getPathForFile(prms.getTr2FileName(), "tr");
    	String fileOfRef = workWithFile.getPathForFile(prms.getRefFileName(), "ref");
    	String fileOfReads = workWithFile.getPathForFile(prms.getReadsFileName(), "rd");
    	String fileOfDependenciesTr1 = workWithFile.getPathForFile(prms.getDep1FileName(), "dep");
    	String fileOfDependenciesTr2 = workWithFile.getPathForFile(prms.getDep2FileName(), "dep");
    	
    	
    	Transcriptome tr1 = 
        		new Transcriptome(Fasta.read(fileOfTranscriptome1, 
        				"fasta", "sequences"), 
        				workWithFile.getNameOfFile(fileOfTranscriptome1));
        
    	Transcriptome tr2 = 
        		new Transcriptome(Fasta.read(fileOfTranscriptome2, 
        				"fasta", "sequences"), 
        				workWithFile.getNameOfFile(fileOfTranscriptome2));
        Transcriptome ref = 
        		new Transcriptome(Fasta.read(fileOfRef, 
        				"fasta", "sequences"), 
        				workWithFile.getNameOfFile(fileOfRef));
        
        System.out.println("Succsessful reading "
        		+ "of transcriptomes");
        
        
        Reads rd = 
        		new Reads (Fasta.read(fileOfReads, 
        				"fastq", "sequences"));
        
        TranscriptomeAssembly linksTr1 = ReadsMapper.map(rd, tr1, 
        		fileOfDependenciesTr1, 
        		fileOfReads,
        		fileOfTranscriptome1);
        
        
        TranscriptomeAssembly linksTr2 = ReadsMapper.map(rd, tr2, 
        		fileOfDependenciesTr2, 
        		fileOfReads,
        		fileOfTranscriptome2);
        
        
        WorkMode workMode = ClassCreator.getWorkMode(prms.getMode());
        Classifier classif = 
        		ClassCreator.getClassifier(prms.getClassifier(), 
        				prms.getParamsForClassifier());
        AssembliesSimilarityRefiner simref = ClassCreator.getSimilarityRefiner();
        
        
        
        
        SimilarityMatrix simMatForTr1andTr2 = 
        		new SimilarityMatrix(tr1, tr2, "NW");
        Assignment asForTr1andTr2 = 
        		new Assignment(simMatForTr1andTr2);
        
        SimilarityMatrix simMatForTr1andRef = 
        		new SimilarityMatrix(tr1, ref, "NW");
        Assignment asForTr1andRef = 
        		new Assignment(simMatForTr1andRef);
        
        SimilarityMatrix simMatForTr2andRef = 
        		new SimilarityMatrix(tr2, ref, "NW");
        Assignment asForTr2andRef = new 
        		Assignment(simMatForTr2andRef);
        
        ReadsForTraining reads = GoodReadsCreator.createSet(asForTr1andRef, asForTr2andRef, linksTr1, linksTr2, topBound, bottomBound, maxCountOfSet1);
        
        WorkClass.work(simref, classif, prms, workMode, linksTr1, linksTr2, reads, asForTr1andTr2);
        /*
        Transcriptome res = new Transcriptome("results");
        if(mode.compareTo("bild") == 0 || mode.compareTo("getClassifiers") == 0){
            if (classifier.compareTo("RF") == 0){
	            
            	TranscriptomeRefinerWithRF refiner = 
	            		new TranscriptomeRefinerWithRF();
	            
	            res = refiner.refiner(tr1, 
	            		tr2, 
	            		asForTr1andRef, 
	            		asForTr2andRef, 
	            		linksTr1, 
	            		linksTr2, 
	            		topBound, 
	            		bottomBound, 
	            		asForTr1andTr2, 
	            		level,
	            		maxCountOfSet1,
	            		percentage,
	            		params);
            } else
            if (classifier.compareTo("SVM") == 0){
	            
            	TranscriptomeRefinerWithSVM refiner = 
	            		new TranscriptomeRefinerWithSVM();
	            
	            res = refiner.refiner(tr1, 
	            		tr2, 
	            		asForTr1andRef, 
	            		asForTr2andRef, 
	            		linksTr1, 
	            		linksTr2, 
	            		topBound, 
	            		bottomBound, 
	            		asForTr1andTr2, 
	            		level,
	            		maxCountOfSet1,
	            		percentage,
	            		params);
            } else
            if (classifier.compareTo("AD") == 0){
            	
            	TranscriptomeRefinerWithSVM refiner = 
	            		new TranscriptomeRefinerWithSVM();
	            
	            res = refiner.refiner(tr1, 
	            		tr2, 
	            		asForTr1andRef, 
	            		asForTr2andRef, 
	            		linksTr1, 
	            		linksTr2, 
	            		topBound, 
	            		bottomBound, 
	            		asForTr1andTr2, 
	            		level,
	            		maxCountOfSet1,
	            		percentage,
	            		params);
            	
            } else {
            	System.out.println("Incorrect classifier");
            }
        } else 
        if (mode.compareTo("CV") == 0){
            if (classifier.compareTo("RF") == 0){
	            
            	CrossValidationRF cv = 
	            		new CrossValidationRF();
	            
	            cv.crossValidate(tr1, 
	            		tr2, 
	            		asForTr1andRef, 
	            		asForTr2andRef, 
	            		linksTr1, 
	            		linksTr2, 
	            		topBound, 
	            		bottomBound, 
	            		asForTr1andTr2, 
	            		level,
	            		maxCountOfSet1,
	            		percentage,
	            		params,
	            		numOfFolds);
            } else
            if (classifier.compareTo("SVM") == 0){
	            
            	CrossValidationSVM cv = 
	            		new CrossValidationSVM();
	            
	            cv.crossValidate(tr1, 
	            		tr2, 
	            		asForTr1andRef, 
	            		asForTr2andRef, 
	            		linksTr1, 
	            		linksTr2, 
	            		topBound, 
	            		bottomBound, 
	            		asForTr1andTr2, 
	            		level,
	            		maxCountOfSet1,
	            		percentage,
	            		params,
	            		numOfFolds);
            } else
            if (classifier.compareTo("AD") == 0){
            	
            	CrossValidationAD cv = 
	            		new CrossValidationAD();
	            
	            cv.crossValidate(tr1, 
	            		tr2, 
	            		asForTr1andRef, 
	            		asForTr2andRef, 
	            		linksTr1, 
	            		linksTr2, 
	            		topBound, 
	            		bottomBound, 
	            		asForTr1andTr2, 
	            		level,
	            		maxCountOfSet1,
	            		percentage,
	            		params,
	            		numOfFolds);
            	
            } else {
            	System.out.println("Incorrect classifier");
            }
        }
        if(mode.compareTo("bild") == 0){
        	
	          System.out.println("Count of result is" 
	        		  +res.getAllSeq().size());
	        
	          
	          SimilarityMatrix simMatForResandRef = 
	          		new SimilarityMatrix(res, ref, "NW");
	          Assignment asForResandRef = new 
	          		Assignment(simMatForResandRef);
	         
	          TranscriptomeSimilarity resSim = 
	        		  new TranscriptomeSimilarity(asForResandRef);
	          
	          TranscriptomeSimilarity oasesSim = 
	        		  new TranscriptomeSimilarity(asForTr1andRef);
	          TranscriptomeSimilarity trinitySim = 
	        		  new TranscriptomeSimilarity(asForTr2andRef);
	          
	          System.out.println("RESULTS");
	          System.out.println("Oases:");
	          System.out.println("Accuracy: " 
	        		  + oasesSim.getAccuracy());
	          System.out.println("Count proportion: " 
	        		  + oasesSim.getCountProportion());
	          System.out.println("");
	          System.out.println("Trinity");
	          System.out.println("Accuracy: " 
	        		  + trinitySim.getAccuracy());
	          System.out.println("Count Proportion: " 
	        		  + trinitySim.getCountProportion());
	          System.out.println("");
	          System.out.println("Our refiner: ");
	          System.out.println("Accuracy: " 
	        		  + resSim.getAccuracy());
	          System.out.println("Count Proportion: " 
	        		  + resSim.getCountProportion());
	          System.out.println(""); 
        }	          
	          */
	          System.out.println("That's it:)");

       
       
	}

}
