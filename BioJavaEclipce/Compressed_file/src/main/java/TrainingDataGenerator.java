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

public class TrainingDataGenerator {

	public static void main(String[] args) throws Exception {
		
		
        try {
        	/*
        	double topBound = 0.85;
        	double bottomBound = 0.35;
        	double level = 0.6;
			int maxCountOfSet1 = 100000;
			double percentage = 0.03;
			String classifier = "RF";
			String params = "-I 100 -K 100 -S 1 -depth 0 -num-slots 20";
        	String mode = "refine";
        	int numOfFolds = 10;
        	*/

			//_________________COMAND_LINE_PARSER______________________
        	
        	String[] arguments;
			
        	/*
			 * Mode: "CV", "refine" or "getClassifiers"
			 */
        	String mode = new String();
        	/* 
        	 * topBound
        	 */
        	double topBound = 0;
        	/*
        	 *  bottomBound
        	 */
        	double bottomBound = 0;
        	/*
        	 * <maxCountOfSet1> - max number of good transcripts 
        	 * Need to control size of dataset for learning
        	 * classifiers
        	 */
        	int maxCountOfSet1 = 0;
        	/*
			 * Classifier and params for it
			 * 
			 * Examples:
			 * for SVM:
			 * "-C 1.0 -L 0.0010 -P 1.0E-12 -N 0 -V -1 -W 1 -K \"weka.classifiers.functions.supportVector.PolyKernel -C 250007 -E 1.0" + "\""
			 * for RF:
			 * "-I 100 -K 100 -S 1 -depth 0 -num-slots 20"
			 */
			String classifier = new String();
			String params = new String();
			/*
        	 * <level> - For part of final transcriptome without using ML.
        	 * When we search similar transcripts in two assemblies,
        	 * we choose transcripts, which similar with more than <level>
        	 * value in our measure
        	 */
	        double level = 0;
	        /*
			 * <percentage> - in ML part we add transcripts, which have 
			 * part more than <percentage> of good reads
			 */
			double percentage = 0;
			/*
			 * For CV num of folds for k-fold cv
			 */
			int numOfFolds = 0;
        	
			/*
			 * Command line params
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
			
        	Options opts = new Options();
        	
        	Option md = new Option("md", "mode", true, "mode: \"CV\", "
        			+ "\"refine\" or \"bild\""); 
        	md.setArgs(1);
        	opts.addOption(md);
        	
        	Option tb = new Option("tb", "topBound", true, "top bound "
        			+ "(for classification)"); 
        	tb.setArgs(1);
        	opts.addOption(tb);
        	
        	Option bb = new Option("bb", "bottomBound", true, "bottom "
        			+ "bound (for classification)"); 
        	bb.setArgs(1);
        	opts.addOption(bb);
        	
        	Option maxCount = new Option("maxCount", true, "max count "
        			+ "of reads for class of good reads "
        			+ "(for classification)"); 
        	maxCount.setArgs(1);
        	opts.addOption(maxCount);
        	
        	Option cl = new Option("cl", "classifier", true, "type of "
        			+ "classifier "
        			+ "(for classification)"); 
        	cl.setArgs(1);
        	opts.addOption(cl);
        	
        	Option opt = new Option("opt", "options", true, "options "
        			+ "for classifier"); 
        	opt.setArgs(30);
        	opts.addOption(opt);
        	
        	Option lvl = new Option("lvl", "level", true, "level for "
        			+ "add similar transcripts"); 
        	lvl.setArgs(1);
        	lvl.setOptionalArg(true);
        	opts.addOption(lvl);
        	
        	Option pr = new Option("ps", "percentage", true, "part of "
        			+ "good reads in transcript to add transcript"); 
        	pr.setArgs(1);
        	pr.setOptionalArg(true);
        	opts.addOption(pr);
        	
        	Option folds = new Option("f", "folds", true, "num of folds for CV"); 
        	folds.setArgs(1);
        	folds.setOptionalArg(true);
        	opts.addOption(folds);
        	
        	
        	
        	CommandLineParser cmdLinePosixParser = new PosixParser();
        	CommandLine commandLine = cmdLinePosixParser.parse(opts, args);
        	
        	if (commandLine.hasOption("md")) {
        		arguments = commandLine.getOptionValues("md");
        		mode = arguments[0];
        	} else {
        		System.out.println("Set option \"mode\"!");
        	}
        	if (commandLine.hasOption("tb")) {
        		arguments = commandLine.getOptionValues("tb");
            	topBound = Double.parseDouble(arguments[0]);
        		
        	} else {
        		System.out.println("Set option \"topBound\"!");
        	}
        	if (commandLine.hasOption("bb")) {
        		arguments = commandLine.getOptionValues("bb");
            	bottomBound = Double.parseDouble(arguments[0]);
        	} else {
        		System.out.println("Set option \"bottomBound\"!");
        	}
        	if (commandLine.hasOption("maxCount")) {
        		arguments = commandLine.getOptionValues("maxCount");
            	maxCountOfSet1 = Integer.parseInt(arguments[0]);
        	} else {
        		System.out.println("Set option \"maxCount\"!");
        	}
        	if (commandLine.hasOption("cl")) {
        		arguments = commandLine.getOptionValues("cl");
    			classifier = arguments[0];
        	} else {
        		System.out.println("Set option \"classifier\"!");
        	}
        	if (commandLine.hasOption("opt")) {
        		arguments = commandLine.getOptionValues("opt");
        		params = arguments[0];
        	} else {
        		System.out.println("Set option \"options\"!");
        	}
        	if (commandLine.hasOption("lvl")) {
        		arguments = commandLine.getOptionValues("lvl");
        		level = Double.parseDouble(arguments[0]);
        	} else if (mode.compareTo("bild") == 0){
        		System.out.println("Set option \"level\"!");
        	}
        	if (commandLine.hasOption("ps")) {
        		arguments = commandLine.getOptionValues("ps");
        		percentage = Double.parseDouble(arguments[0]);
        	} else if (mode.compareTo("bild") == 0){
        		System.out.println("Set option \"options\"!");
        	}
        	if (commandLine.hasOption("f")) {
        		arguments = commandLine.getOptionValues("f");
        		numOfFolds = Integer.parseInt(arguments[0]);
        	} else if (mode.compareTo("CV") == 0){
        		System.out.println("Set option \"options\"!");
        	}
        	
        	
			
			
			//_________________________________________________________
			
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
