import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class WorkWithFiles {
	private String pathToWorkFiles;
	private final String trPath = "Data".concat(System.getProperty("file.separator"))
			.concat("Assemblies").concat(System.getProperty("file.separator"));
	private final String refPath = "Data".concat(System.getProperty("file.separator"))
			.concat("References").concat(System.getProperty("file.separator"));
	private final String readsPath = "Data".concat(System.getProperty("file.separator"))
			.concat("Reads").concat(System.getProperty("file.separator"));
	private final String depPath = "Data".concat(System.getProperty("file.separator"))
			.concat("Comparison").concat(System.getProperty("file.separator"));
	private final String GoodReadsPath = "Data".concat(System.getProperty("file.separator"))
			.concat("Good reads").concat(System.getProperty("file.separator"));

	

	
	
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
			double bottomBound) throws Exception{
		ObjectOutputStream out = null;
		String nameOfFile = WorkWithFiles.getNameOfFile(typeOfObj, 
				tr1Name, tr2Name, topBound, bottomBound);
		if (typeOfObj.compareTo("goodReads") == 0){
			out = WorkWithFiles.getOutputStream(pathToWorkFiles, 
							pathToWorkFiles + GoodReadsPath,
							nameOfFile);
		}
		out.writeObject(obj);
	}

	public Object read(String typeOfObj, String tr1Name, 
			String tr2Name, double topBound, 
			double bottomBound) throws Exception {
		
		String filename = WorkWithFiles.getNameOfFile(typeOfObj, 
				tr1Name, tr2Name, topBound, bottomBound);
		
		Object obj = new Object();
		
		if (typeOfObj.compareTo("goodReads") == 0){
			ObjectInputStream in = WorkWithFiles.getInputStream(pathToWorkFiles + 
							GoodReadsPath + filename);
			if (in == null)
				return new ReadsForTraining();
			else
				obj = (ReadsForTraining)in.readObject();
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
	
	private static String getNameOfFile(String type, String tr1Name, 
			String tr2Name, double topBound, 
			double bottomBound){
		String name = new String();
		String str = WorkWithFiles.addSuffix(tr1Name, 
				tr2Name, topBound, 
				bottomBound);
		if (type.compareTo("goodReads") == 0){
			name = "Good reads ";
			name = name.concat(str);
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
}
