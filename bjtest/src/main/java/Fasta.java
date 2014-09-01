/**
 * Created by volta on 29.08.14.
 */

import java.io.File;
import java.io.FileInputStream;
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



    public void read (String filename) {
        LinkedHashMap<String, ProteinSequence> a = FastaReaderHelper.readFastaProteinSequence(new File(filename));
        //FastaReaderHelper.readFastaDNASequence for DNA sequences

        for (  Entry<String, ProteinSequence> entry : a.entrySet() ) {
            System.out.println( entry.getValue().getOriginalHeader() + "=" + entry.getValue().getSequenceAsString() );
        }
    }

    public void write () {

    }

}
