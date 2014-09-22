/**
 * Created by volta on 09.09.14.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.biojava3.core.sequence.compound.AminoAcidCompoundSet;
import org.biojava3.core.sequence.io.FastaReader;
import org.biojava3.core.sequence.io.FastaReaderHelper;
import org.biojava3.core.sequence.io.GenericFastaHeaderParser;
import org.biojava3.core.sequence.io.ProteinSequenceCreator;

public class Fasta {

    public void read (String filename) throws Exception {
        /*
		 * Method 1: With the FastaReaderHelper
		 */
        //Try with the FastaReaderHelper
        LinkedHashMap<String, ProteinSequence> a = FastaReaderHelper.readFastaProteinSequence(new File(filename));
        //FastaReaderHelper.readFastaDNASequence for DNA sequences

        FileWriter writeFile = null;
        try {
            File logFile = new File("error.txt");
            writeFile = new FileWriter(logFile);
            for (  Entry<String, ProteinSequence> entry : a.entrySet() ) {
                writeFile.write( entry.getValue().getOriginalHeader() + "=" + entry.getValue().getSequenceAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(writeFile != null) {
                try {
                    writeFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
/*
        for (  Entry<String, ProteinSequence> entry : a.entrySet() ) {
            System.out.println( entry.getValue().getOriginalHeader() + "=" + entry.getValue().getSequenceAsString() );
        }
*/
		/*
		 * Method 2: With the FastaReader Object

        //Try reading with the FastaReader
        FileInputStream inStream = new FileInputStream( filename );
        FastaReader<ProteinSequence,AminoAcidCompound> fastaReader =
                new FastaReader<ProteinSequence,AminoAcidCompound>(
                        inStream,
                        new GenericFastaHeaderParser<ProteinSequence,AminoAcidCompound>(),
                        new ProteinSequenceCreator(AminoAcidCompoundSet.getAminoAcidCompoundSet()));
        LinkedHashMap<String, ProteinSequence> b = fastaReader.process();
        for (  Entry<String, ProteinSequence> entry : b.entrySet() ) {
            System.out.println( entry.getValue().getOriginalHeader() + "=" + entry.getValue().getSequenceAsString() );
        }
        */

    }

}
