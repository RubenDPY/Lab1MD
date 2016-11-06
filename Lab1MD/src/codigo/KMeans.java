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
		
		//Formato--> Ruta de arffTFIDF + K + Tipo de inicializacion
		
		if(args[2].equals("a")){
			Instances data = DataSource.read(args[0]);
			int k = Integer.parseInt(args[1]);
			ArrayList<Instance> centroides = new ArrayList<Instance>();
			//Hay que recordar no repetir ningun centroide
			double listaRandom[] = new double[2*k];
			for (int i = 0; i < k; i++) {
				listaRandom[i] = 0;
			}
			
			
			
			for (int i = 0; i < k; i++) {
				boolean con = false;
				double numRandom = 0;
				while (!con) {
					con = true;
					numRandom = Math.floor(Math.random()*(0-data.numInstances()+1)+data.numInstances());
					for (int j = 0; j < k; j++) {
						if(numRandom == listaRandom[j]){
							con = false;
						}
					}
				}
				
				  // Valor entre M y N, ambos incluidos.
				//System.out.println("Valor del Random" + numRandom);
				Instance centroide = data.instance((int) numRandom);
				//System.out.println("La instancia elegida es" + centroide.toString());
				centroides.add(centroide);
				listaRandom[i] = numRandom;
			}
			
		}
		else if(args[2].equals("c")){
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
				//System.out.println("Valor del Random" + numRandom);
				Instance centroide = data.instance((int) numRandom);
				//System.out.println("La instancia elegida es" + centroide.toString());
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
						//double res = (a * a) - (b * b);
						double res = a - b;
						res = res * res;
						//res = Math.abs(res);
						
						disT = disT + res;
						
						//System.out.println("La distancia a guardar es: " + disT);
						}
					disT = Math.sqrt(disT);
					matrizDistancias[i][j2] = disT;
				}
			}
			//Cogemos las k distancias mayores
			double mayores[] = new double[k];
			//Inicializamos el array a 0
			for (int i = 0; i < k; i++) {
				mayores[i] = 0;
			}
			
			//Comprobar que lo de debajo de la diagonal no tiene valores extraños
			
			double distanciaA[] = new double[k];
			double distanciaB[] = new double[k];
			
			for (int i = 0; i <centroides.size(); i++) {
				for (int j = 0; j < centroides.size(); j++) {
					int j2 = 0;
					boolean control = false;
					while(j2 < k && !control){
						//System.out.println("Entra en el shile");
						//System.out.println("Valor a comparar:" + matrizDistancias[i][j]);
						if(mayores[j2] < matrizDistancias[i][j]){
							mayores[j2] = matrizDistancias[i][j];
							
							
							
							distanciaA[j2] = i;
							distanciaB[j2] = j;
							control = true;
							//System.out.println("No sale");
						}					
						j2++;
					}				
				}
			}
			
			//Array con los centroides elegidos
			Instance centroidesFin[] = new Instance[k];
			int indiceC = 0;
			for (int i = 0; i < k; i++) {
				
				boolean conA = true;
				boolean conB = true;
				
					for (int j = 0; j < indiceC; j++) {
						if(centroides.get((int) distanciaA[i]).toString().equals(centroidesFin[j].toString())){
							conA = false;
						}
						if(centroides.get((int) distanciaB[i]).toString().equals(centroidesFin[j].toString())){
							conB = false;
						}
										
				}if(indiceC < k){
					
					if(conA){
					centroidesFin[indiceC] = centroides.get((int) distanciaA[i]);
					indiceC++;
					}
				}
				if(indiceC < k){
					if(conB){
					centroidesFin[indiceC] = centroides.get((int) distanciaB[i]);
					indiceC++;
					}	
			}
			}
			
			//Inicializacion a False de la matriz
			boolean clusters[][] = new boolean[data.numInstances()][k];			
			
			for (int i = 0; i < k; i++) {
				for (int j = 0; j < data.numInstances(); j++) {
					clusters[j][i] = false;
				}
			}		
			
			double distF = 100000000000000.0;
			int donde = 0;
			
			for (int i = 0; i < data.numInstances(); i++) {			
				
				for (int j = 0; j < k; j++) {
					
					double distC = 0;
					
					
					for (int j2 = 1; j2 < 100; j2++) {
											
						//double a = Double.parseDouble(centroides.get(i).toString(centroides.get(i).attribute(j)));
						//double b = Double.parseDouble(centroides.get(j2).toString(centroides.get(j2).attribute(j)));
						
						double a = Double.parseDouble(data.instance(i).toString(data.instance(i).attribute(j2)));
						double b = Double.parseDouble(centroidesFin[j].toString(centroidesFin[j].attribute(j2)));
						
						double res2 = a - b;
						res2 = res2 * res2;						
						distC = distC + res2;						
						}
					//System.out.println("La distancia C es: ");
					if(distC < distF){
						distF = distC;
						donde = j;
						}					
				}
				
				clusters[i][donde] = true;
				
			}
			
			Instance nuevosCentroides[] = new Instance[k];
			double aux = 0.0;
			//Creamos k listas donde separar por cluster
			for (int j = 0; j < k; j++) {
				aux = 0;
				Instance centroide = null;
				for (int i = 0; i < data.numInstances(); i++) {
					if(clusters[i][j]){
						if(aux == 0){
							centroide = data.instance(i);
							aux++;
						}else{
							System.out.println("Hola");
							for (int l = 0; l < 100; l++) {
								System.out.println("Nombre del atributo: " + centroide.attribute(l));
								System.out.println("Valor antiguo del atributo : " + Double.parseDouble(centroide.toString(centroide.attribute(l))));
								System.out.println("Nuevo valor: " + Double.parseDouble(data.instance(i).toString(data.instance(i).attribute(l))));
								centroide.setValue(centroide.attribute(l), Double.parseDouble(centroide.toString(centroide.attribute(l))) + Double.parseDouble(data.instance(i).toString(data.instance(i).attribute(l))));
								System.out.println("Media: " + aux);								
							}
							aux++;
						}
					}					
				}if(aux != 0){
					for (int l2 = 0; l2 < 100; l2++) {
						centroide.setValue(centroide.attribute(l2), Double.parseDouble(centroide.toString(centroide.attribute(l2))) / aux );
					}	
				}
				
			}
			/*
			for (int i = 0; i < k; i++) {
				
				System.out.println(clusters[2900][i]);
			}

			
			
			
			System.out.println("La distancia X es: " + mayores[0]);
			System.out.println("Corresponde a: " + distanciaA[0] + " " + distanciaB[0]);
			
			for (int j = 1; j < 100; j++) {
				
				//System.out.println("Valor del atributo a " + centroides.get(j2).toString(centroides.get(j2).attribute(j)));
				//System.out.println(centroides.get(j2).attribute(j).toString());
			
				double a = Double.parseDouble(centroides.get((int) distanciaA[0]).toString(centroides.get((int) distanciaA[0]).attribute(j)));
				double b = Double.parseDouble(centroides.get((int) distanciaB[0]).toString(centroides.get((int) distanciaB[0]).attribute(j)));
				
				//disAux = sqrt(a^2 - b^2);
				//double res = (a * a) - (b * b);
				double res = a - b;
				res = res * res;
				//res = Math.abs(res);
				
				disT = disT + res;	
			}
			disT = Math.sqrt(disT);
			System.out.println("La supuesta distancia que deberia ser igual es: " + disT);

			
			System.out.println("La distancia X es: " + mayores[8]);
			System.out.println("La distancia X es: " + mayores[6]);
			*/
		}
	}
		
	

	
	//
}
//http://weka.sourceforge.net/doc.dev/weka/clusterers/SimpleKMeans.html