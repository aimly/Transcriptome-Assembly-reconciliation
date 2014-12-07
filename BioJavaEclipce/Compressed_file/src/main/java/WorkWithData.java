import java.io.IOException;

import weka.core.Instances;


public class WorkWithData {
	private DatasetCreator datasetCreator;
	private Instances data;
	
	public WorkWithData (DatasetCreator datasetCreator, Instances data){
		this.datasetCreator = datasetCreator;
		this.data = data;
	}
	
	public WorkWithData (ReadsForTraining reads) throws Exception{
		this.datasetCreator = new DatasetCreator(reads);
		this.data = this.datasetCreator.create(reads);
	}
	
	public DatasetCreator getDatasetCreator(){
		return this.datasetCreator;
	}
	
	public Instances getData (){
		return this.data;
	}
	
}
