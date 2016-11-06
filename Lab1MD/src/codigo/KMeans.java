package codigo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import weka.clusterers.SimpleKMeans;
import weka.core.DistanceFunction;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class KMeans {
	
	public static void main(String[] args) throws Exception {
		
		//Formato--> Ruta de arffTFIDF + K 
		Instances data = DataSource.read(args[0]);
		int k = Integer.parseInt(args[1]);
		ArrayList<Instance> centroides = new ArrayList<Instance>();
		//Hay que recordar no repetir ningun centroide
		double listaRandom[] = new double[2*k];
		for (int i = 0; i < k; i++) {
			listaRandom[i] = 0;
		}
		
		
		
		for (int i = 0; i < 2*k; i++) {
			boolean con = false;
			double numRandom = 0;
			while (!con) {
				con = true;
				numRandom = Math.floor(Math.random()*(0-data.numInstances()+1)+data.numInstances());
				for (int j = 0; j < 2*k; j++) {
					if(numRandom == listaRandom[j]){
						con = false;
					}
				}
			}
			
			  // Valor entre M y N, ambos incluidos.
			System.out.println("Valor del Random" + numRandom);
			Instance centroide = data.instance((int) numRandom);
			System.out.println("La instancia elegida es" + centroide.toString());
			centroides.add(centroide);
			listaRandom[i] = numRandom;
		}
		double matrizDistancias[][] = new double[centroides.size()][centroides.size()];
		double disT = 0;
		double disAux = 0;
		//Hallamos las distancias entre centroides
		for (int i = 0; i < centroides.size(); i++) {
			disT = 0;
			for (int j2 = i+1; j2 < centroides.size(); j2++) {
				for (int j = 1; j < 100; j++) {
					
					//System.out.println("Valor del atributo a " + centroides.get(j2).toString(centroides.get(j2).attribute(j)));
					//System.out.println(centroides.get(j2).attribute(j).toString());
				
					double a = Double.parseDouble(centroides.get(i).toString(centroides.get(i).attribute(j)));
					double b = Double.parseDouble(centroides.get(j2).toString(centroides.get(j2).attribute(j)));
					
					
					//disAux = sqrt(a^2 - b^2);
					double res = (a * a) - (b * b);
					res = Math.abs(res);
					disAux = Math.sqrt(res);
					disT = disT + disAux;
					
					//System.out.println("La distancia a guardar es: " + disT);
					matrizDistancias[i][j2] = disT;
				}
			}
		}
		//Cogemos las k distancias mayores
		double mayores[] = new double[k];
		//Inicializamos el array a 0
		for (int i = 0; i < k; i++) {
			mayores[i] = 0;
		}
		
		//Comprobar que lo de debajo de la diagonal no tiene valores extraños
		for (int i = 0; i <centroides.size(); i++) {
			for (int j = 0; j < centroides.size(); j++) {
				int j2 = 0;
				boolean control = false;
				while(j2 < k && !control){
					//System.out.println("Entra en el shile");
					//System.out.println("Valor a comparar:" + matrizDistancias[i][j]);
					if(mayores[j2] < matrizDistancias[i][j]){
						mayores[j2] = matrizDistancias[i][j];
						control = true;
						//System.out.println("No sale");
					}					
					j2++;
				}				
			}
		}
		System.out.println("La distancia X es: " + mayores[0]);
		System.out.println("La distancia X es: " + mayores[8]);
		System.out.println("La distancia X es: " + mayores[6]);
		
	}
	

	
	//
}
//http://weka.sourceforge.net/doc.dev/weka/clusterers/SimpleKMeans.html