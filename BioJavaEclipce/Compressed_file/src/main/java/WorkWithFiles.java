import java.io.File;


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
	
	public Object writeToFile(String typeOfClass, Object obj){
		System.out.println(obj.getClass().toString());
		return obj;
	}
}
