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

	public Double[][] computeSimilarity(Transcriptome firstSet, 
			Transcriptome secondSet) {
		
		SubstitutionMatrix<NucleotideCompound> matrix = SubstitutionMatrixHelper.getNuc4_4();
		SimpleGapPenalty gap = new SimpleGapPenalty();
		
		System.out.println(matrix);
		System.out.println(gap);
		
		Double[][] SimilarityMatrix = new Double[firstSet.getAllSeq().size()]
				[secondSet.getAllSeq().size()];
		Iterator<String> i1 = firstSet.getAllSeq().iterator();
		Iterator<String> i2 = secondSet.getAllSeq().iterator();
		int i = 0, j = 0;
		
		
		while (i1.hasNext()){
			j = 0;
			String str1 = i1.next();
			System.out.println(str1);
			DNASequence target = new DNASequence(str1,
					AmbiguityDNACompoundSet.getDNACompoundSet());
			ProteinSequence seq1 = new ProteinSequence(str1);
			System.out.println(target);
			System.out.println(seq1);
			while (i2.hasNext()){
				String str2 = i2.next();
				System.out.println(str2);
				DNASequence query = new DNASequence(str2,
						AmbiguityDNACompoundSet.getDNACompoundSet());
				ProteinSequence seq2 = new ProteinSequence(str2);
				
				System.out.println(query);
				System.out.println(seq2);
				
/*				SequencePair<DNASequence, NucleotideCompound> psa =
						Alignments.getPairwiseAlignment(query, target,
								PairwiseSequenceAlignerType.GLOBAL, gap, matrix);*/
				NeedlemanWunsch<DNASequence, NucleotideCompound> aligner = new NeedlemanWunsch<DNASequence, NucleotideCompound>(query, target, gap, matrix);
				System.out.println(aligner.getMaxScore());
				SimilarityMatrix[i][j] = aligner.getDistance();
				j++;
			}
			i++;
		}
		
		return SimilarityMatrix;
	}

}
