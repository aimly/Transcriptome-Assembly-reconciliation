import org.biojava3.alignment.NeedlemanWunsch;
import org.biojava3.alignment.SimpleGapPenalty;
import org.biojava3.alignment.SubstitutionMatrixHelper;
import org.biojava3.alignment.template.SubstitutionMatrix;
import org.biojava3.core.sequence.DNASequence;
import org.biojava3.core.sequence.compound.AmbiguityDNACompoundSet;
import org.biojava3.core.sequence.compound.NucleotideCompound;


public class NWSim implements TranscriptSimilarityComputer {

	@Override
	public double[][] computeSimilarity(Transcriptome firstSet, 
			Transcriptome secondSet,
			WorkWithFiles workWithFiles) throws Exception {
		System.out.println("Start computing similarity");
		double[][] SimilarityMatrix = (double[][]) workWithFiles.read(workWithFiles.getSimMatrixID(), 
				firstSet.getNameOfSet(), secondSet.getNameOfSet(), 0, 0, null, null);
		if (SimilarityMatrix != null){
			System.out.println("similarity readed");
			return SimilarityMatrix;
		}
		
		SubstitutionMatrix<NucleotideCompound> matrix = 
				SubstitutionMatrixHelper.getNuc4_4();
		SimpleGapPenalty gap = new SimpleGapPenalty();
		
		SimilarityMatrix = 
				new double[firstSet.getAllSeq().size()]
						[secondSet.getAllSeq().size()];
		int i = 0, j = 0;
		for (String index1 : firstSet.getAllSeq()){
			j = 0;
			DNASequence target = new DNASequence(index1,
					AmbiguityDNACompoundSet.getDNACompoundSet());
			for (String index2 : secondSet.getAllSeq()){
				DNASequence query = new DNASequence(index2,
						AmbiguityDNACompoundSet.getDNACompoundSet());
				
				NeedlemanWunsch aligner = new NeedlemanWunsch(query, target, gap, matrix);
				
				double test_similarity = aligner.getSimilarity();
				SimilarityMatrix[i][j] = test_similarity;
				j++;
			}
			i++;
		}
		workWithFiles.writeToFile(workWithFiles.getSimMatrixID(), 
				SimilarityMatrix, firstSet.getNameOfSet(), 
				secondSet.getNameOfSet(), 0, 0, null, null);
		System.out.println("Finish computing similarity");
		return SimilarityMatrix;
	}

}
