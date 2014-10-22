import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by volta on 09.09.14.
 */


public class Reads  {
	protected HashMap<String, String> reads;
	
	public Reads (HashMap<String, String> setOfTr) throws IOException {
    	
		this.reads = setOfTr;
    	
    }
	
	public Reads() {
			// TODO Auto-generated constructor stub
	}

	public void print () {
		System.out.println("Set of reads \n");
		int i = 1;
		
		for (Map.Entry<String,String> item : reads.entrySet()){
			System.out.println("Read №" + i);
			Read rd = new Read (item.getKey(), item.getValue());
			rd.print();
			i++;
		}
	}
	
	public String getSeq (String name){
		return reads.get(name);
	}

}
