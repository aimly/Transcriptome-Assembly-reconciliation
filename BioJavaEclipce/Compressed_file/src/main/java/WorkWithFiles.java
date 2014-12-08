import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import weka.classifiers.Classifier;



public class WorkWithFiles {
	private String pathToWorkFiles;
	private final String trPath = "Data"
			.concat(System.getProperty("file.separator"))
			.concat("Assemblies")
			.concat(System.getProperty("file.separator"));
	private final String refPath = "Data"
			.concat(System.getProperty("file.separator"))
			.concat("References")
			.concat(System.getProperty("file.separator"));
	private final String readsPath = "Data"
			.concat(System.getProperty("file.separator"))
			.concat("Reads")
			.concat(System.getProperty("file.separator"));
	private final String depPath = "Data"
			.concat(System.getProperty("file.separator"))
			.concat("Comparison")
			.concat(System.getProperty("file.separator"));
	
	private final String goodReadsID = "GoodReads";
	private final String goodReadsPath = "Good reads"
			.concat(System.getProperty("file.separator"));
	
	private final String classifierID = "Classifier";
	private final String classifierPath = "Classifiers"
			.concat(System.getProperty("file.separator"));
	
	private final String refinedTrID = "RefinedTranscript";
	private final String refinedTrPath = "Refined"
			.concat(System.getProperty("file.separator"));

	private final String simMatrixID = "simMatrix";
	private final String simMatrixPath = "Assignments"
			.concat(System.getProperty("file.separator"));

	
	
	public WorkWithFiles (){
		String[] curDir = new File("").getAbsolutePath().split(System.getProperty("file.separator"));
		String path = "";
		int i = 0;
		while (i < curDir.length - 3){
			path = path.concat(curDir[i]);
			path = path.concat(System.getProperty("file.separator"));
			i++;
		}
		this.pathToWorkFiles = path;
	}
	
	public String getPathForFile(String path, String typeOfFile){
		if (path == null){
			return null;
		}
		if (path.contains(System.getProperty("file.separator"))){
			return path;
		}
		else {
			if (typeOfFile.compareTo("rd") == 0)
				return pathToWorkFiles.concat(readsPath).concat(path);
			if (typeOfFile.compareTo("tr") == 0)
				return pathToWorkFiles.concat(trPath).concat(path);
			if (typeOfFile.compareTo("dep") == 0)
				return pathToWorkFiles.concat(depPath).concat(path);
			if (typeOfFile.compareTo("ref") == 0)
				return pathToWorkFiles.concat(refPath).concat(path);
			System.out.println("Bad type of file");
			return null;
		}
	}
	
	public String getNameOfFile (String fileName){
		return fileName.split("\\/")[fileName.split(System.getProperty("file.separator")).length - 1].split("\\.")[0];
	}
	
	public void writeToFile(String typeOfObj, 
			Object obj, String tr1Name, 
			String tr2Name, double topBound, 
			double bottomBound,
			String typeOfClassifier, 
			String paramsForClassifier) throws Exception{
		ObjectOutputStream out = null;
		String nameOfFile = this.getNameOfFile(typeOfObj, 
				tr1Name, tr2Name, topBound, 
				bottomBound, typeOfClassifier,
				paramsForClassifier);
		File f = new File(nameOfFile);
		if (f.exists())
			return;
		if (typeOfObj.compareTo(this.goodReadsID) == 0){
			out = WorkWithFiles.getOutputStream(pathToWorkFiles, 
							pathToWorkFiles + goodReadsPath,
							nameOfFile);
		}
		if (typeOfObj.compareTo(this.classifierID) == 0){
			out = WorkWithFiles.getOutputStream(pathToWorkFiles, 
							pathToWorkFiles + classifierPath,
							nameOfFile);
		}
		if (typeOfObj.compareTo(this.refinedTrID) == 0){
			out = WorkWithFiles.getOutputStream(pathToWorkFiles, 
							pathToWorkFiles + refinedTrPath,
							nameOfFile);
		}
		if (typeOfObj.compareTo(this.simMatrixID) == 0){
			out = WorkWithFiles.getOutputStream(pathToWorkFiles, 
							pathToWorkFiles + simMatrixPath,
							nameOfFile);
		}
		if (out != null){
			out.writeObject(obj);
			out.close();
		}
		else
			System.out.println("Cannot write object " + 
					typeOfObj + " to file!");
	}

	public Object read(String typeOfObj, String tr1Name, 
			String tr2Name, double topBound, 
			double bottomBound, String typeOfClassifier,
			String paramsOfClassifier) throws Exception {
		
		String filename = this.getNameOfFile(typeOfObj, 
				tr1Name, tr2Name, topBound, bottomBound, 
				typeOfClassifier, paramsOfClassifier);
		ObjectInputStream in;
		Object obj = new Object();
		
		if (typeOfObj.compareTo(this.goodReadsID) == 0){
			in = WorkWithFiles.getInputStream(pathToWorkFiles + 
							goodReadsPath + filename);
			if (in == null){
				System.out.println("in is null");
				return new ReadsForTraining();
			}
			else {
				System.out.println("in is not null");
				obj = (ReadsForTraining)in.readObject();
				in.close();
			}
		}
		if (typeOfObj.compareTo(this.classifierID) == 0){
			in = WorkWithFiles.getInputStream(pathToWorkFiles + 
					classifierPath + filename);
			if (in == null)
				return null;
			else {
				obj = (Classifier)in.readObject();
				in.close();
			}
		}
		if (typeOfObj.compareTo(this.refinedTrID) == 0){
			in = WorkWithFiles.getInputStream(pathToWorkFiles + 
					refinedTrPath + filename);
			if (in == null)
				return new Transcriptome(this.refinedTrID);
			else {
				obj = (Transcriptome)in.readObject();
				in.close();
			}
		}
		if (typeOfObj.compareTo(this.simMatrixID) == 0){
			in = WorkWithFiles.getInputStream(pathToWorkFiles + 
					simMatrixPath + filename);
			if (in == null)
				return null;
			else{
				obj = (double[][])in.readObject();
				in.close();
			}
		}
		return obj;
	}
	
	private static ObjectInputStream getInputStream (String fileName) throws Exception{
		File file = new File(fileName);
		if (!file.exists())
			return null;
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream in = new ObjectInputStream(fis);
		return in;
	}
	
	private String getNameOfFile(String type, String tr1Name, 
			String tr2Name, double topBound, 
			double bottomBound,
			String typeOfClassifier, String paramsForClassifier){
		String name = new String();
		String str = WorkWithFiles.addSuffix(tr1Name, 
				tr2Name, topBound, 
				bottomBound);
		if (type.compareTo(this.goodReadsID) == 0){
			name = "Good reads ";
			name = name.concat(str);
		}
		if (type.compareTo(this.classifierID) == 0){
			name = "Classifier ";
			name = name.concat(str + " " + typeOfClassifier + 
					" params: " + paramsForClassifier);
		}
		if (type.compareTo(this.refinedTrID) == 0){
			name = "Refined transcriptome ";
			name = name.concat(str + " " + typeOfClassifier + 
					" params: " + paramsForClassifier);
		}
		if (type.compareTo(simMatrixID) == 0){
			name = "Similarity matrix ";
			name = name.concat(tr1Name + " and " + tr2Name);
		}
		
		return name;
	}
	
	private static String addSuffix(String tr1Name, String tr2Name,
			double topBound, double bottomBound) {
		String str = "";
		str = str.concat(tr1Name + " and " + tr2Name + 
				" tb " + topBound +
				" bb " + bottomBound);
		return str;
	}

	private static ObjectOutputStream getOutputStream (String basePath,
			String path, String fileName) throws IOException {
		File file = new File(path + fileName);
		FileOutputStream fis;
		try {
			fis = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fis);
			return out;
		} catch (FileNotFoundException e) {
			
			File file2 = new File(basePath + fileName);
			fis = new FileOutputStream(file2);
			ObjectOutputStream out = new ObjectOutputStream(fis);
			return out;
		}
	}
	
	public String getGoodReadsID(){
		return this.goodReadsID;
	}
	
	public String getClassifierID(){
		return this.classifierID;
	}
	
	public String getRefinedTrID(){
		return this.refinedTrID;
	}
	
	public String getSimMatrixID(){
		return this.simMatrixID;
	}
}
