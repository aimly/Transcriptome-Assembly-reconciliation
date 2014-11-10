import java.awt.List;
import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.tokenizers.NGramTokenizer;
import weka.filters.unsupervised.attribute.StringToWordVector;


public class DatasetCreator {
	
	public static Instance getInstance(Assignment trAssignment1,
			Assignment trAssignment2,
			TranscriptomeAssembly tr1,
			TranscriptomeAssembly tr2) throws Exception {
		
		ArrayList<Attribute> atts = new ArrayList<Attribute>();
		atts.add(new Attribute("att1",(ArrayList<String>) null));
		
		ArrayList<String> attVals = new ArrayList<String>();
		for (int i = 0; i < 5; i++)
		  attVals.add("class" + (i+1));
		atts.add(new Attribute("class", attVals));
		
		Instances data = new Instances("MyRelation", atts, 0);
		
		double[] vals = new double[data.numAttributes()];
		System.out.println(data.numAttributes());
		
		vals[0] = data.attribute(0).addStringValue("GTTGAAGGAGAAGTTGTTGTGGAAAAGATTATGTTCAGCGATTGGGAGAATGGGTACAACGCAATGACTGATACATATGAGAATCTGTTCGAAGCTGGAGTGATTGATC");
		vals[1] = attVals.indexOf("class1");
		data.add(new DenseInstance(1.0, vals));
		
		vals[0] = data.attribute(0).addStringValue("NTTGATTGATATCACTGAGGATGGCTTCGTGAGCCTTNTNNCTGACNNTGGTGGCACCAAGGATGATCTCAAGCTTCCCACCGNTGANGGTCTCACCGCCCAGATGAGG");
		vals[1] = attVals.indexOf("class1");
		data.add(new DenseInstance(1.0, vals));
		
		vals[0] = data.attribute(0).addStringValue("CGAGGAACGAATCTACCGCAACTCGCTTCATTGTAGTAAACGTTGATGCGCTCAAGTTGCAGATCTGAGTCTCCGGTGTACCTTCCGGTTGGATCGATGCCGTGCTCGG");
		vals[1] = attVals.indexOf("class1");
		data.add(new DenseInstance(1.0, vals));
		
		
		NGramTokenizer tokenizer = new NGramTokenizer();
		tokenizer.setNGramMinSize(1);
		tokenizer.setNGramMaxSize(1);
		tokenizer.setDelimiters("A");
		
		StringToWordVector filter = new StringToWordVector();
		filter.setTokenizer(tokenizer);
		filter.setInputFormat(data);
		filter.setDoNotOperateOnPerClassBasis(true);
		filter.setLowerCaseTokens(true);
		
		Instances newInst = filter.useFilter(data, filter);
		
		System.out.println(newInst);
		
		return null;
		
	}
	
}
