import java.io.IOException;
import java.util.HashSet;

public class TrainingDataGenerator {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Gaga");
		
		Fasta gg = new Fasta();
		
		HashSet <NucleicSequence> gg2 = new HashSet<NucleicSequence>();
        try {
            Transcriptome tr = new Transcriptome(gg.read("/home/volta/another/diploma/bowtie/Oases.fa", "fasta"));
            Transcriptome tr2 = new Transcriptome(gg.read("/home/volta/another/diploma/bowtie/Trinity.fa", "fasta"));
            
            Reads rd = new Reads (gg.read("/home/volta/another/diploma/ag_1_GGCTAC_filtered.fq", "fastq"));
            //rd.print();
//            TranscriptomeAssembly links = ReadsMapper.map(rd, tr, "/home/volta/another/diploma/bowtie/results_trinity.sam");
            NWSimilarity sim = new NWSimilarity();
            double[][] s1 = sim.computeSimilarity(tr, tr2);
            
            
            
            
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        } catch (ClassCastException e2) {
        	e2.printStackTrace();
            System.out.println(e2.toString());
        }
	}

}
