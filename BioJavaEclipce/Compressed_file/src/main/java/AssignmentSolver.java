
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;



public class AssignmentSolver {
	
	public static HashMap<TranscriptPair, Double> solve (SimilarityMatrix matrix) 
			throws IOException{
		
		double[][] sim = matrix.getMatrix();
		Transcriptome tr1 = matrix.getFirstTranscriptome();
		Transcriptome tr2 = matrix.getSecondTranscriptome();
		String path = "/home/volta/another/test/All calculated  shit/";
		
		File file = new File(path + tr1.getNameOfSet() + 
				"+" + tr2.getNameOfSet() + " " + "Assignment problem");
		
		double[][] reverseMatrix = new double[sim.length]
				[sim[0].length];
		
		for (int t = 0; t < reverseMatrix.length; t++)
			for (int l = 0; l < reverseMatrix[0].length; l++){
				reverseMatrix[t][l] = -sim[t][l];
			}
		
		
		
		int[] solution = new int[sim.length];
		
		if (file.exists()){
			
			FileInputStream fis = new FileInputStream(path + tr1.getNameOfSet() + 
					"+" + tr2.getNameOfSet() + " " + "Assignment problem");
			ObjectInputStream in = new ObjectInputStream(fis);
			int[] readObject;
			try {
				readObject = (int[]) in.readObject();
				solution = readObject;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			in.close();
			
			
			
			System.out.println("Assignment for transcripts"+ 
					tr1.getNameOfSet()+ "and"+
					tr2.getNameOfSet()+"Redddddd!");
			
			in.close();
		} else {
			HungarianAlgorithm solver = new HungarianAlgorithm(reverseMatrix);
			solution = solver.execute();
		}
//		for (int k = 0; k<solution.length; k++)
//			System.out.println(solution[k]);
		
		HashMap<TranscriptPair, Double> compliances = 
				new HashMap<TranscriptPair, Double>();
		
		
				
		String[] arr1 = new String[tr1.getAllSeq().size()];
		String[] arr2 = new String[tr2.getAllSeq().size()];
		
		Iterator<String> i1 = tr1.getAllSeq().iterator();
		Iterator<String> i2 = tr2.getAllSeq().iterator();
		int i = 0;
		while (i1.hasNext()){
			arr1[i] = new String(i1.next());
			i++;
		}
		i = 0;
		while (i2.hasNext()){
			arr2[i] = i2.next();
			i++;
			
		}
		
		
		/*
		 * 
		 *  нужно немного переписать эту хрень
		 * 	и написать комментарии
		 */
		
		for (i = 0; i < solution.length; i++){
			
			Transcript t1 = new Transcript(tr1.getName(arr1[i]), 
					arr1[i]);
			
			if(solution[i] == -1){
				Transcript t2 = null;
				Double similarity = 0.0;
				TranscriptPair pair = new TranscriptPair(t1, t2);
				compliances.put(pair, similarity);
			}
			else {
				Transcript t2 = new Transcript(tr2.getName(arr2[solution[i]]), 
						arr2[solution[i]]);
				Double similarity = new Double(sim[i][solution[i]]);
				TranscriptPair pair = new TranscriptPair(t1, t2);
				compliances.put(pair, similarity);
			}
			
		}
		//  Writing solution of assignment problem to file
		if ( !file.exists() ){
			

			
			FileOutputStream fos = new FileOutputStream(path + tr1.getNameOfSet() + 
					"+" + tr2.getNameOfSet() + " " + "Assignment problem");
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(solution);
			out.flush();
			out.close();
		}
		return compliances;
		
	}
	
}
