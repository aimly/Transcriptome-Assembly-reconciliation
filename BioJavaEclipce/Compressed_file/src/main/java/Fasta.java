
import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.biojava3.core.sequence.compound.AminoAcidCompoundSet;
import org.biojava3.core.sequence.io.FastaReader;
import org.biojava3.sequencing.io.fastq.Fastq;
import org.biojava3.sequencing.io.fastq.FastqReader;
import org.biojava3.sequencing.io.fastq.SangerFastqReader;
import org.biojava3.core.sequence.io.GenericFastaHeaderParser;
import org.biojava3.core.sequence.io.ProteinSequenceCreator;

public class Fasta {

    public static HashMap<String, String> read (String filePath, 
    		String fileformat, 
    		String typeOfKeys) throws Exception {
        
    	// awry
    	
    	if (filePath == null){
    		return null;
    	}
    	
    	HashMap<String, String> set = new HashMap<String, String>();
    	if (fileformat.equals("fasta") || fileformat.equals("fa")) {
	        FileInputStream inStream = new FileInputStream( filePath );
	        FastaReader<ProteinSequence,AminoAcidCompound> fastaReader =
	                new FastaReader<ProteinSequence,AminoAcidCompound>(
	                        inStream,
	                        new GenericFastaHeaderParser<ProteinSequence,AminoAcidCompound>(),
	                        new ProteinSequenceCreator(AminoAcidCompoundSet.getAminoAcidCompoundSet()));
	        LinkedHashMap<String, ProteinSequence> b = fastaReader.process();
	    	
	        
	        // Biojava парсит FASTA файл и выдает в качестве имени 
	        // последовательности все, что стоит в строке после символа ">"
	        // Однако программы вроде TopHat, сопоставляющие ридам
	        // транскрипты, считают, что имя последовательности - 
	        // это набор символов после ">", оканчивающийся пробелом,
	        // (все остальное в строке - это просто информация, и на деле 
	        // так и есть). Посему использована конструкция 
	        // entry.getValue().getOriginalHeader().split(" ")[0]
	        if (typeOfKeys == "sequences"){
		        for (  Entry<String, ProteinSequence> entry : b.entrySet() ) {
		        	set.put(entry.getValue().getSequenceAsString(), 
		        			entry.getValue().getOriginalHeader().split(" ")[0]);
		        }

		        System.out.println("File " + filePath + "parsed successfully");
	        }
	        else if (typeOfKeys == "names")
	        	for (  Entry<String, ProteinSequence> entry : b.entrySet() ) {
		        	set.put(entry.getValue().getOriginalHeader().split(" ")[0], 
		        			entry.getValue().getSequenceAsString());
		        }
	        	
    	}
    	else if (fileformat.equals("fastq") || fileformat.equals("fq")) {
    		FastqReader fastqReader = new SangerFastqReader();
    		if (typeOfKeys == "sequences"){
		        for ( Fastq fastq : fastqReader.read(new File(filePath)) ) {
		        	set.put(fastq.getSequence(), 
		        			fastq.getDescription().split(" ")[0]);
		        	
		        }
		        System.out.println("File " + filePath + "parsed successfully");
    		}
    		else if (typeOfKeys == "names")
    			for ( Fastq fastq : fastqReader.read(new File(filePath)) ) {
		        	set.put(fastq.getDescription().split(" ")[0], 
		        			fastq.getSequence());
		        }
    		
    	}
    	else {
    		System.out.println("Unknown file format. Please, use FASTA or FASTQ files");
    	}
		return set;
    	
    }
    
    
    // Overwriten function "read" (need to fix reading fasta files!)
    /*
    protected HashMap<String, String> fastRead (String filename, String fileformat) throws IOException{
    	
    	HashMap<String, String> set = new HashMap<String, String>();
    	BufferedReader reader = new BufferedReader(new FileReader(filename));
    	String lnName, lnSeq, ln, lnQual;

    	
    	// System.getProperty("line.separator") выдает код символа переноса строки
    	
    	if (fileformat == "fasta") {
    		while ((lnName = reader.readLine()) != null && (lnName.length() > 1)) {
    			if ((lnSeq = reader.readLine()) == null){
    				System.out.println("Incorrect FASTA file");
    				reader.close();
    				return null;
    			}
    			if (lnName.endsWith(System.getProperty("line.separator"))){
    				lnName = lnName.substring(1, lnName.length()-2);
    			} else {
    				lnName = lnName.substring(1, lnName.length()-1);
    			}
    			
    			if (lnSeq.endsWith(System.getProperty("line.separator"))){
    				lnSeq = lnSeq.substring(0, lnSeq.length()-2);
    			} else {
    				lnSeq = lnSeq.substring(0, lnSeq.length()-1);
    			}
    			
	        	set.put(lnSeq, lnName);
    		}
            
    	} else if (fileformat == "fastq") {
    		
    		while ((lnName = reader.readLine()) != null) {
    			if ((lnSeq = reader.readLine()) == null  ||
    					(ln = reader.readLine()) == null ||
    					(lnQual = reader.readLine()) == null){
    				System.out.println("Incorrect or empty FASTQ file");
    				reader.close();
    				return null;
    			}
    			
    			if (lnName.endsWith(System.getProperty("line.separator"))){
    				lnName = lnName.substring(1, lnName.length()-2);
    			} else {
    				lnName = lnName.substring(1, lnName.length()-1);
    			}
    			
    			if (lnSeq.endsWith(System.getProperty("line.separator"))){
    				lnSeq = lnSeq.substring(0, lnSeq.length()-2);
    			} else {
    				lnSeq = lnSeq.substring(0, lnSeq.length()-1);
    			}
	        	set.put(lnSeq, lnName);
    		}
    		
    	} else {
    		System.out.println("Unknown file format");
    	}
    	
    	reader.close();
    	return set;
    }
    */
}
