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
		
        try {
        	
        	String fileOfTranscript1 = "/home/volta/another/test/" +
        			"Data/Assemblies/Oases.fa";
        	String fileOfTranscript2 = "/home/volta/another/test/" +
        			"Data/References/ref_for_reads.fa";
        	String nameOfTranscriptome1 = 
        			fileOfTranscript1.split("/")[fileOfTranscript1.split("\\/").length - 1].split("\\.")[0];
        	String nameOfTranscriptome2 = 
        			fileOfTranscript2.split("/")[fileOfTranscript2.split("\\/").length - 1].split("\\.")[0];
        	
        	Transcriptome tr1 = 
            		new Transcriptome(Fasta.read(fileOfTranscript1, 
            				"fasta", "sequences"), 
            				nameOfTranscriptome1);
            Transcriptome tr2 = 
            		new Transcriptome(Fasta.read(fileOfTranscript2, 
            				"fasta", "sequences"), 
            				nameOfTranscriptome2);
            
            System.out.println("Succsessful reading of transcriptomes");
//            Reads rd = new Reads (gg.read("/home/volta/another/diploma/bowtie/test/rd.fq", "fastq"));
            //rd.print();
//            TranscriptomeAssembly links = ReadsMapper.map(rd, tr, "/home/volta/another/diploma/bowtie/results_trinity.sam");
            NWSimilarity sim = new NWSimilarity();
            SimilarityMatrix simMat = new SimilarityMatrix(tr1, tr2, "NW");
  //          simMat.searchErrors();
            Assignment as = new Assignment(simMat);
            as.print();
          
        	
        	
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        } catch (ClassCastException e2) {
        	e2.printStackTrace();
            System.out.println(e2.toString());
        }
	}

}
