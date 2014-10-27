import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.HashBiMap;

/**
 * Created by volta on 09.09.14.
 */


public class Reads  {
	private HashMap<String, String> reads;
	
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
			Read rd = new Read (item.getValue(), item.getKey());
			rd.print();
			i++;
		}
	}
	
	public String getName (String seq){
		return reads.get(seq);
	}

}
