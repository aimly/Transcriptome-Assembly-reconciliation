/**
 * Created by volta on 09.09.14.
 */

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.lang.String;


public class NucleicSequence {

    public byte[] data;

    public byte[] name;

    public NucleicSequence (String var1, String var2) {
        data = var1.getBytes();
        name = var2.getBytes();
    }

    public String getName () throws IOException {
        String Name1 =  new String(name);
        return Name1;
    }

    public String getData () throws IOException {
        String Data1 = new String (data);
        return Data1;
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
        if (data != other.data)
            return false;
        return true;
    }


}
