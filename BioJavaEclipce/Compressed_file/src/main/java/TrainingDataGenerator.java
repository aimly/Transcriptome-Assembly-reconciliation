
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
        Classifiers classifiers = 
        		ClassCreator.getClassifiers(prms.getClassifier(), 
        				prms.getParamsForClassifier());
        AssembliesSimilarityRefiner simref = ClassCreator.getSimilarityRefiner();
        TranscriptSimilarityComputer trSim = ClassCreator.getSimComp();
        TranscriptomeSimilarityComputer transcriptomeSimComp = ClassCreator.getTranscriptomeSim();
        
        Assignment asForTr1andTr2 = 
        		new Assignment(tr1, tr2, trSim, workWithFile);
        
        Assignment asForTr1andRef = 
        		new Assignment(tr1, ref, trSim, workWithFile);
        
        Assignment asForTr2andRef = new 
        		Assignment(tr2, ref, trSim, workWithFile);
        
        ReadsForTraining reads1 = GoodReadsCreator.createGoodReadsSet(asForTr1andRef, asForTr2andRef, linksTr1, linksTr2, prms, workWithFile);
        ReadsForTraining reads2 = GoodReadsCreator.createGoodReadsSet(asForTr2andRef, asForTr1andRef, linksTr2, linksTr1, prms, workWithFile);
        
        PairOfReadsForTraining pair = new PairOfReadsForTraining(reads1, reads2);
        
        Transcriptome resTranscriptome = WorkClass.work(simref, 
        		classifiers, 
        		prms, workMode, linksTr1, 
        		linksTr2, pair, asForTr1andTr2,
        		workWithFile);
        
        RefinerSimilarityComputer.
        	computeSimilarity(transcriptomeSimComp,
        			resTranscriptome, ref,
        			simref, trSim, asForTr1andRef,
        			asForTr2andRef, workWithFile);
        
        System.out.println("End of work");

	}

}
