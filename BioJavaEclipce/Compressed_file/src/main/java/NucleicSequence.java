/**
 * Created by volta on 09.09.14.
 */

import java.io.IOException;



public class NucleicSequence {

    protected byte[] data;

    protected byte[] name;

    public NucleicSequence (String var1, String var2) {
        this.name = var1.getBytes();
        this.data = var2.getBytes();
    }

    public NucleicSequence() {
		// TODO Auto-generated constructor stub
	}
 
    // Copy constructor
    
    public NucleicSequence (NucleicSequence obj) throws IOException{
    	name = obj.getName().getBytes();
        data = obj.getData().getBytes();
    }

	public String getName () throws IOException {
        String Name1 =  new String(name); 
        return Name1;
    }

    public String getData () throws IOException {
        String Data1 = new String (data);
        return Data1;
    }
    
    public void print () {
		String s1 = new String(this.name);
		String s2 = new String(this.data);
		System.out.println("Name : " + s1);
		System.out.println(s2 + "\n");
	}

    @Override
    public int hashCode() {
        int result = 0;
        for (int i = 0; i < data.length; i++) {
            result += data[i]*31^(data.length - 1 - i);
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NucleicSequence other = (NucleicSequence) obj;
        String thisStr = new String(this.data);
        String otherStr = new String(other.data);
        if (thisStr.equalsIgnoreCase(otherStr)){
        	return true;
        }	
        return false;
    }


}
