import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;


public class TrainingDataGenerator {
    public static void main() {

        Fasta gg = new Fasta();
        try {
            gg.read("/home/volta/another/diploma/ag_1_GGCTAC_filtered.fq");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println ("ufuf");

    }
}
