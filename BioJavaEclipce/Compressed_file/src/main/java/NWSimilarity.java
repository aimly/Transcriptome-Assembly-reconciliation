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
import org.biojava3.core.sequence.compound.NucleotideCompound;




class NWSimilarity implements TranscriptSimilarityComputer {

	public double[][] computeSimilarity(Transcriptome firstSet, 
			Transcriptome secondSet) {
		
		SubstitutionMatrix<NucleotideCompound> matrix = SubstitutionMatrixHelper.getNuc4_4();
		SimpleGapPenalty gap = new SimpleGapPenalty();
		
		System.out.println(matrix);
		System.out.println("ExtPen: " + gap.getExtensionPenalty());
		System.out.println("OpenPen: " + gap. getOpenPenalty());
		
		double[][] SimilarityMatrix = new double[firstSet.getAllSeq().size()]
				[secondSet.getAllSeq().size()];
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
				System.out.println("getScore: " + aligner.getScore());
				System.out.println("getMaxScore: " + aligner.getMaxScore());
				System.out.println("getMinScore: " + aligner.getMinScore());
				System.out.println("getDistance: " + aligner.getDistance());
				System.out.println("getSimilarity: " + aligner.getSimilarity());
				System.out.println("");

	*/			
				SimilarityMatrix[i][j] = aligner.getSimilarity();
				j++;
			}
			i++;
		}
		
		return SimilarityMatrix;
	}

}
