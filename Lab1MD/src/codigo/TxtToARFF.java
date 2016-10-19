package codigo;

import java.io.BufferedReader;

import java.io.BufferedWriter;

import java.io.FileReader;

import java.io.FileWriter;



public class TxtToARFF {

	public static void main(String[] args) {

		try{
			if(args.length==2){
				BufferedReader in = new BufferedReader(new FileReader(args[0]));
				BufferedWriter out = new BufferedWriter(new FileWriter(args[1]));
				String line;
				int id=0;
				out.write("@relation sms_spam\n\n");
				out.write("@attribute id NUMERIC\n");
				out.write("@attribute text string\n");
				out.write("@attribute class {ham,spam}\n\n");
				out.write("@data\n\n");

				//archivo test_blind

				if(args[0].contains("test_blind")){
					while((line = in.readLine()) != null){
						//formato: id,'text',?
						//elimina los char ' de los SMS porque en weka da problemas
					    out.write(id+","+"'"+line.substring(4,line.length()).replace("'", "")+"',?\n");
					    id++;
					}
				}

				//archivos train y dev

				else{
					while((line = in.readLine()) != null){
						//formato: id,'text',class
						//elimina los char ' de los SMS porque en weka da problemas
					    out.write(id+","+"'"+line.substring(4,line.length()).replace("'", "")+"',"+line.substring(0,4)+"\n");
					    id++;
					}
				}	
				in.close();
				out.close();
			}else{
				System.out.println("Se necesitan dos parametros, primero el txt y segundo el archivo arff donde guardar la informacion.");
				System.out.println("Comando esperado: java -jar getARFF.jar file.txt file.arff");
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
