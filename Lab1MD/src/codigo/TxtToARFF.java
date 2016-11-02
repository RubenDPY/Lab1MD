package codigo;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;

import java.io.FileWriter;
import java.util.ArrayList;



public class TxtToARFF {

	public static void main(String[] args) {

		try{
			
		String path = null;	
			
			
		File f = new File(args[0]);
		//while(f.exists()){
			
		if(f.exists()){
			File[] fLista = f.listFiles();
			int id=0;
			BufferedWriter out = new BufferedWriter(new FileWriter(args[1]));
			out.write("@relation TwentyNewsgroups\n\n");
			out.write("@attribute id NUMERIC\n");
			out.write("@attribute text string\n\n");
			out.write("@data\n");
			
			
			for(int x=0;x<fLista.length;x++){
				
				if(!fLista[x].getName().equals("TxToARFF.jar") && !fLista[x].getName().equals("prueba2.arff") ){
					path = "C:/MD2016/";
					path = path + fLista[x].getName();
					System.out.println(path);
				}
				
				
				if(args.length==2){
					
					BufferedReader in = new BufferedReader(new FileReader(path));
					
					String line;
					String total = "";
					
					
				
					line = in.readLine();
					//total = id + ",'";
					while(!line.isEmpty()){
						line = in.readLine();
					}
					while((line = in.readLine()) != null){
						//formato: id,'text',?
						//System.out.println(line);
						//line.replace("'", "");
						//System.out.println(line);
					    total = total + line;
					    
					}
					//total = total + "'\n";
					System.out.println(total);
					total = filtroCaracteres(total);
					out.write(id+","+"'"+total.replaceAll("'", "")+"',"+"\n");
					//out.write(total);
					
					in.close();
					
					
				}else{
						System.out.println("Se necesitan dos parametros, primero el txt y segundo el archivo arff donde guardar la informacion.");
						System.out.println("Comando esperado: java -jar getARFF.jar file.txt file.arff");
						
						System.out.println(args[0]);
						System.out.println(args[1]);
					}
				System.out.println(id);
				id++;
			}
			
			out.close();
		}
		
				
			
			
			
		//	}

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static String filtroCaracteres(String total) {
		// TODO Auto-generated method stub
		String c[] = {};
		String r[] = {};
		for (int i = 0; i < c.length; i++) {
			total.replaceAll(c[i], r[i]);
		} 
		return total;
	}

}
