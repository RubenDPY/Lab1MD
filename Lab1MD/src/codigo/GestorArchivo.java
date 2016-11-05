package codigo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.Randomize;

public class GestorArchivo {
		private Instances file;
	public GestorArchivo(String pFile) throws Exception{
		file = cargarARFF(pFile);
	}
	public void setInstances(String pFile) throws Exception{
		file = cargarARFF(pFile);
	}
	public Instances cargarARFF(String file) throws Exception{
	    FileReader fi=null;
	    Instances data = null;
		try {
			fi= new FileReader(file); //(args[0]) <-> ("~/software/weka-3-6-9/data/breast-cancer.arff" )
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: El path del fichero de datos es erroneo:"+file);
		}
		// 1.3. Load the instances
		try {
			data = new Instances(fi);
		} catch (IOException e) {
			System.out.println("ERROR: El contenido del fichero de datos no cumple las precondiciones: "+file);
		}
		// 1.4. Close the file
		try {
			fi.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
//		Randomize random= new Randomize();
//		random.setInputFormat(data);
//		data = Filter.useFilter(data, random);
		///data.setClassIndex(data.numAttributes()-1);
		data.setClass(data.attribute("clase"));
		return data;
	}
	public Instances getFile(){
		return file;
	}
	public void guardarARFF(Instances i, String Ruta){
		Instances dataSet= i;
		ArffSaver saver = new ArffSaver();
		saver.setInstances(dataSet);
		try{
			File archivo = new File(Ruta);
			saver.setFile(archivo);
			saver.setDestination(new FileOutputStream(archivo));
			saver.writeBatch();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
