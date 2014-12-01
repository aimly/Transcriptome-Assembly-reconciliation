import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.biojava3.alignment.Alignments;
import org.biojava3.alignment.Alignments.PairwiseSequenceAlignerType;
import org.biojava3.alignment.NeedlemanWunsch;
import org.biojava3.alignment.SimpleGapPenalty;
import org.biojava3.alignment.SimpleSubstitutionMatrix;
import org.biojava3.alignment.SubstitutionMatrixHelper;
import org.biojava3.alignment.template.SequencePair;
import org.biojava3.alignment.template.SubstitutionMatrix;
import org.biojava3.core.sequence.DNASequence;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AmbiguityDNACompoundSet;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.biojava3.core.sequence.compound.NucleotideCompound;




class NWSimilarity implements TranscriptSimilarityComputer {

	public double[][] computeSimilarity(Transcriptome firstSetOfTr, 
			Transcriptome secondSetOfTr) throws IOException  {
		
		Transcriptome firstSet, secondSet;
		firstSet = firstSetOfTr;
		secondSet = secondSetOfTr;
		String path = "/home/volta/another/test/All calculated  shit/";
	/*
		SubstitutionMatrix<NucleotideCompound> matrix = 
				new SimpleSubstitutionMatrix();
		SubstitutionMatrix<AminoAcidCompound> matrix = 
				SubstitutionMatrixHelper.getPAM250();
	*/
		
		SubstitutionMatrix<NucleotideCompound> matrix = 
				SubstitutionMatrixHelper.getNuc4_4();
		SimpleGapPenalty gap = new SimpleGapPenalty();
		
//		System.out.println(matrix);
//		System.out.println("ExtPen: " + gap.getExtensionPenalty());
//		System.out.println("OpenPen: " + gap. getOpenPenalty());
		
		double[][] SimilarityMatrix = new double[firstSet.getAllSeq().size()]
				[secondSet.getAllSeq().size()];
		
		File file = new File(path + 
				firstSet.getNameOfSet() + 
				"+" + secondSet.getNameOfSet() + 
				" Similarity");
		
		if (file.exists()){
			
			FileInputStream fis = new FileInputStream(path + firstSet.getNameOfSet() + 
					"+" + secondSet.getNameOfSet() + " " + "Similarity");
			ObjectInputStream in = new ObjectInputStream(fis);
			double[][] readObject;
			try {
				readObject = (double[][]) in.readObject();
				SimilarityMatrix = readObject;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			in.close();
			
			
			
			System.out.println("Red!");
			in.close();
			

	        System.out.println("Similarity matrix for "+ firstSetOfTr.getNameOfSet()
	        		+ " and " + secondSetOfTr.getNameOfSet() + "red successfully");
			
			
			return SimilarityMatrix;
		} 
		
		
		
		
		Iterator<String> i1 = firstSet.getAllSeq().iterator();
		int i = 0, j = 0;
		
		
		while (i1.hasNext()){
			j = 0;
			String str1 = i1.next();
			DNASequence target = new DNASequence(str1,
					AmbiguityDNACompoundSet.getDNACompoundSet());

			Iterator<String> i2 = secondSet.getAllSeq().iterator();
			while (i2.hasNext()){
				String str2 = i2.next();
				DNASequence query = new DNASequence(str2,
						AmbiguityDNACompoundSet.getDNACompoundSet());
				
				
/*				SequencePair<DNASequence, NucleotideCompound> psa =
						Alignments.getPairwiseAlignment(query, target,
								PairwiseSequenceAlignerType.GLOBAL, gap, matrix);*/
				NeedlemanWunsch aligner = new NeedlemanWunsch(query, target, gap, matrix);
/*
				System.out.println("aligner: ");
				System.out.println("getScore: " + aligner.getScore());
				System.out.println("getMaxScore: " + aligner.getMaxScore());
				System.out.println("getMinScore: " + aligner.getMinScore());
				System.out.println("getDistance: " + aligner.getDistance());
				System.out.println("getSimilarity: " + aligner.getSimilarity());
				System.out.println("Sequences is really equivalent: " + str2.equals(str1));
				System.out.println("");
*/
				double test_similarity = aligner.getSimilarity();
				
				if (test_similarity >= 1.0 || test_similarity <= 0.0) {
				//	System.out.println("ggg ");
				//	System.out.println(" ");
				}
				SimilarityMatrix[i][j] = test_similarity;
				if (j%50 == 0)
					System.out.println("i = " + i + "j = " + j);
				j++;
				
			}
			i++;
		}
		
		FileOutputStream fos = new FileOutputStream(path + 
				firstSet.getNameOfSet() + 
				"+" + secondSet.getNameOfSet() + 
				" " + "Similarity");
		ObjectOutputStream out = new ObjectOutputStream(fos);
		out.writeObject(SimilarityMatrix);
		out.flush();
		out.close();
		

        System.out.println("Similarity matrix for "+ firstSetOfTr.getNameOfSet()
        		+ " and " + secondSetOfTr.getNameOfSet() + "computed successfully");
		
		return SimilarityMatrix;
	}

}
