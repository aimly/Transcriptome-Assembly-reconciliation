import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class TrainingDataGenerator {

	public static void main(String[] args) throws Exception {
		
		
        try {
        	// topBound
        	double topBound = 0.85;
        	// bottomBound
        	double bottomBound = 0.35;
        	/*
        	 * <level> - For part of final transcriptome without using ML.
        	 * When we search similar transcripts in two assemblies,
        	 * we choose transcripts, which similar with more than <level>
        	 * value in our measure
        	 */
        	double level = 0.6;
        	/*
        	 * <maxCountOfSet1> - max number of good transcripts 
        	 * Need to control size of dataset for learning
        	 * classifiers
        	 */
			int maxCountOfSet1 = 100000;
			/*
			 * <percentage> - in ML part we add transcripts, which have 
			 * part more than <percentage> of good reads
			 */
			double percentage = 0.03;
			/*
			 * Classifier and params for it
			 */
			String classifier = "RF";
			String params = "-I 100 -K 100 -S 1 -depth 0 -num-slots 20";
			/*
			 * Mode: "CV", "refine" or "getClassifiers"
			 */
        	String mode = "refine";
        	/*
			 * For CV num of folds for k-fold cv
			 */
        	int numOfFolds = 10;
			
        	String fileOfTranscript1 = "/home/volta/another/test/" +
        			"Data/Assemblies/Oases.fa";
        	String fileOfTranscript2 = "/home/volta/another/test/" +
        			"Data/Assemblies/Trinity.fa";
        	String fileOfRef = "/home/volta/another/test/" +
        			"Data/References/ref_for_reads.fa";
        	String fileOfReads = "/home/volta/another/test/"
        			+ "Data/Reads/ag_1_GGCTAC_filtered.fq";
        	String fileOfDependenciesTr1 = "/home/volta/another/test/Data/Сomparison/results_oases.sam";
        	String fileOfDependenciesTr2 = "/home/volta/another/test/Data/Сomparison/results_trinity.sam";
        	
        	
        	String nameOfTranscriptome1 = 
        			fileOfTranscript1.split("\\/")[fileOfTranscript1.split("\\/").length - 1].split("\\.")[0];
        	String nameOfTranscriptome2 = 
        			fileOfTranscript2.split("\\/")[fileOfTranscript2.split("\\/").length - 1].split("\\.")[0];
        	String nameOfReference = 
        			fileOfRef.split("\\/")[fileOfRef.split("\\/").length - 1].split("\\.")[0];
        	String nameOfReads = 
        			fileOfReads.split("\\/")[fileOfReads.split("\\/").length - 1].split("\\.")[0];
        	
        	
        	Transcriptome tr1 = 
            		new Transcriptome(Fasta.read(fileOfTranscript1, 
            				"fasta", "sequences"), 
            				nameOfTranscriptome1);
            Transcriptome tr2 = 
            		new Transcriptome(Fasta.read(fileOfTranscript2, 
            				"fasta", "sequences"), 
            				nameOfTranscriptome2);
            Transcriptome ref = 
            		new Transcriptome(Fasta.read(fileOfRef, 
            				"fasta", "sequences"), 
            				nameOfReference);
            
            System.out.println("Succsessful reading "
            		+ "of transcriptomes");
            Reads rd = 
            		new Reads (Fasta.read(fileOfReads, 
            				"fastq", "sequences"));
            //rd.print();
            TranscriptomeAssembly linksTr1 = ReadsMapper.map(rd, tr1, 
            		fileOfDependenciesTr1, 
            		fileOfReads,
            		fileOfTranscript1);
            
            
            TranscriptomeAssembly linksTr2 = ReadsMapper.map(rd, tr2, 
            		fileOfDependenciesTr2, 
            		fileOfReads,
            		fileOfTranscript2);
            
          
            
            
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
		          System.out.println("That's it:)");
		          
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        } catch (ClassCastException e2) {
        	e2.printStackTrace();
            System.out.println(e2.toString());
        }
	}

}
