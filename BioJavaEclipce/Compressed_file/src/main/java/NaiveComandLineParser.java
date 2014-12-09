
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;


public class NaiveComandLineParser {
	
	
	public static Params parse (String[] args) throws ParseException{
		Params params = new Params();
		
		Options opts = new Options();
    	
    	Option md = new Option("md", true, "mode: \"CV\", "
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
    	opt.setArgs(1);
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
    	
    	Option tr1 = new Option("tr1", true, "first transcript"); 
    	tr1.setArgs(1);
    	opts.addOption(tr1);
    	
    	Option tr2 = new Option("tr2", true, "second transcript"); 
    	tr2.setArgs(1);
    	opts.addOption(tr2);
    	
    	Option rd = new Option("rd", true, "file of reads"); 
    	rd.setArgs(1);
    	opts.addOption(rd);
    	
    	Option dep1 = new Option("dep1", true, "file of dependencies between reads and first transcriptome"); 
    	dep1.setArgs(1);
    	opts.addOption(dep1);
    	
    	Option dep2 = new Option("dep2", true, "file of dependencies between reads and second transcriptome"); 
    	dep2.setArgs(1);
    	opts.addOption(dep2);
    	
    	Option ref = new Option("ref", true, "file of reference"); 
    	ref.setArgs(1);
    	ref.setOptionalArg(true);
    	opts.addOption(ref);
    	
    	
    	CommandLineParser cmdLinePosixParser = new PosixParser();
    	CommandLine commandLine = cmdLinePosixParser.parse(opts, args);
    	
    	params.setTr1FileName(getOption("tr1", commandLine));
    	params.setTr2FileName(getOption("tr2", commandLine));
    	params.setReadsFileName(getOption("rd", commandLine));
    	params.setDep1FileName(getOption("dep1", commandLine));
    	params.setDep2FileName(getOption("dep2", commandLine));
    	params.setMode(getOption("md", commandLine));
    	params.setTB(Double.parseDouble(getOption("tb", commandLine)));
    	params.setBB(Double.parseDouble(getOption("bb", commandLine)));
    	params.setMaxCountOfSet1(Integer.parseInt(getOption("maxCount", commandLine)));
    	params.setClassifier(getOption("cl", commandLine));
    	params.setParamsForClassifier(getOption("opt", commandLine));
    	if (params.getMode().compareTo("bild") == 0){
    		params.setLevel(Double.parseDouble(getOption("lvl", commandLine)));
    		params.setPercentage(Double.parseDouble(getOption("ps", commandLine)));
    	}
    	if (params.getMode().compareTo("CV") == 0)
    		params.setFolds(Integer.parseInt(getOption("f", commandLine)));
    	params.setRefFileName(getOption("ref", commandLine));
    	
    	
		
		return params;
	}
	
	private static String getOption (String opt, CommandLine commandLine){
		String[] arguments;
		if (commandLine.hasOption(opt)) {
    		arguments = commandLine.getOptionValues(opt);
    		return arguments[0];
    	} else {
    		return null;
    	}
	}
	
}
