package codigo;

import weka.clusterers.SimpleKMeans;
import weka.core.DistanceFunction;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class KMeans {
	
	public static void main(String[] args) throws Exception {
		
		
		Instances data = DataSource.read(args[0]);
		
		//Se genera el modelo
		
		SimpleKMeans kMeans = new SimpleKMeans();
		
		//Modificamos las inicializaciones
		String[] options = weka.core.Utils.splitOptions("-init 0");
		String[] options2 = weka.core.Utils.splitOptions("-A ");
				
		kMeans.setOptions(options);
	}
	

}
//http://weka.sourceforge.net/doc.dev/weka/clusterers/SimpleKMeans.html