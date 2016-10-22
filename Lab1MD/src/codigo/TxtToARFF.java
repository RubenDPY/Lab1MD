package codigo;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;

import java.io.FileWriter;



public class TxtToARFF {

	public static void main(String[] args) {

		try{
			
		File f = new File(args[0]);
		while(f.exists()){
				
			
			if(args.length==2){
				BufferedReader in = new BufferedReader(new FileReader(args[0]));
				BufferedWriter out = new BufferedWriter(new FileWriter(args[1]));
				String line;
				String total;
				int id=0;
				out.write("@relation Twenty Newsgroups\n\n");
				out.write("@attribute id NUMERIC\n");
				out.write("@attribute text string\n");
				out.write("@data\n\n");
			
				line = in.readLine();
				total = id + ",";
				while(!line.isEmpty()){
					line = in.readLine();
				}
				while((line = in.readLine()) != null){
					//formato: id,'text',?
					//elimina los char ' de los SMS porque en weka da problemas
				    total = total + line;
				    id++;
				}
				out.write(total);
				
				in.close();
				out.close();
				
			}else{
					System.out.println("Se necesitan dos parametros, primero el txt y segundo el archivo arff donde guardar la informacion.");
					System.out.println("Comando esperado: java -jar getARFF.jar file.txt file.arff");
				}
			
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
