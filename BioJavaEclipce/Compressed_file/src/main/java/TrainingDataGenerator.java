import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class TrainingDataGenerator {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Gaga");
		
		Fasta gg = new Fasta();
		
		HashSet <NucleicSequence> gg2 = new HashSet<NucleicSequence>();
        try {/*
            Transcriptome tr1 = 
            		new Transcriptome(gg.read("/home/volta/another/diploma/bowtie/Trinity.fa", "fasta"));
            Transcriptome tr2 = new Transcriptome(gg.read("/home/volta/another/diploma/bowtie/Oases1.fa", "fasta"));
            
//            Reads rd = new Reads (gg.read("/home/volta/another/diploma/bowtie/test/rd.fq", "fastq"));
            //rd.print();
//            TranscriptomeAssembly links = ReadsMapper.map(rd, tr, "/home/volta/another/diploma/bowtie/results_trinity.sam");
            NWSimilarity sim = new NWSimilarity();
            SimilarityMatrix simMat = new SimilarityMatrix(tr1, tr2, "NW");
            Assignment as = new Assignment(simMat);
            as.print();*/
          
        	
        	String[] dep = new Scanner(new File("/home/volta/another/diploma/bowtie/results_trinity.sam")).useDelimiter("\\Z").next().split(System.lineSeparator());
        	dep = Arrays.sort(dep.);
        	String[] reads = new Scanner(new File("/home/volta/another/diploma/ag_1_GGCTAC_filtered.fq")).useDelimiter("\\Z").next().split(System.lineSeparator());
        	System.out.println(dep[28]);
        	System.out.println(reads[28]);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        } catch (ClassCastException e2) {
        	e2.printStackTrace();
            System.out.println(e2.toString());
        }
	}

}
