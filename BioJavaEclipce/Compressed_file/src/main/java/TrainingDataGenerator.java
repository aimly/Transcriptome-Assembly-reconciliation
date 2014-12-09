
public class TrainingDataGenerator {

	public static void main(String[] args) throws Exception {
		
		//_________________COMAND_LINE_PARAMS______________________
		/*
		 * files (full paths or just names; but if you use just name,
		 * file will be searched in specific basedir;
		 * default basedir (~)is folder, where you copy
		 * project from github):
		 * 
		 * --ref <> (basedir: ~/Data/References/) - reference 
		 * transcriptome (optional)
		 * --dep1 <>, --dep2 <> (basedir: ~/Data/Comparison/) - maps 
		 * of transcriptomes and reads
		 * -tr1 <>, -tr2 <> (basedir: ~/Data/Assemblies/) - assembled 
		 * from reads by different programs transcriptomes 
		 * --rd <> (basedir: ~/Data/Reads/) - reads
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

    	String fileOfTranscriptome1 = workWithFile.getPathForFile(prms.getTr1FileName(), "tr");
    	String fileOfTranscriptome2 = workWithFile.getPathForFile(prms.getTr2FileName(), "tr");
    	String fileOfReads = workWithFile.getPathForFile(prms.getReadsFileName(), "rd");
    	String fileOfDependenciesTr1 = workWithFile.getPathForFile(prms.getDep1FileName(), "dep");
    	String fileOfDependenciesTr2 = workWithFile.getPathForFile(prms.getDep2FileName(), "dep");
    	
        WorkMode workMode = ClassCreator.getWorkMode(prms.getMode());
        Classifiers classifiers = 
        		ClassCreator.getClassifiers(prms.getClassifier(), 
        				prms.getParamsForClassifier());
        AssembliesSimilarityRefiner simref = ClassCreator.getSimilarityRefiner();
        TranscriptSimilarityComputer trSim = ClassCreator.getSimComp();
        TranscriptomeSimilarityComputer transcriptomeSimComp = ClassCreator.getTranscriptomeSim();
    	InputTranscriptomes inpTr = 
    			ClassCreator.getSimilarityRefiner(prms, workWithFile, trSim);
    	RefSimComp refSimComp = ClassCreator.getRefSimComp(prms);
            
        Reads rd = 
        		new Reads (Fasta.read(fileOfReads, "sequences"));
        
        TranscriptomeAssembly linksTr1 = ReadsMapper.map(rd, 
        		inpTr.getAssembly1(), 
        		fileOfDependenciesTr1, 
        		fileOfReads,
        		fileOfTranscriptome1);
 
        TranscriptomeAssembly linksTr2 = ReadsMapper.map(rd, 
        		inpTr.getAssembly2(), 
        		fileOfDependenciesTr2, 
        		fileOfReads,
        		fileOfTranscriptome2);

        PairOfReadsForTraining pair = new PairOfReadsForTraining(inpTr, linksTr1, linksTr2, prms, workWithFile);
        
        ReturnOfWorkMode resTranscriptome = WorkClass.work(simref, 
        		classifiers, 
        		prms, workMode, linksTr1, 
        		linksTr2, pair, inpTr.getAsgnOfAssemblies(),
        		workWithFile);

        refSimComp.computeSimilarity(transcriptomeSimComp, 
        		resTranscriptome, workWithFile, inpTr, 
        		trSim, simref);
        
        System.out.println("End of work");
	}

}
