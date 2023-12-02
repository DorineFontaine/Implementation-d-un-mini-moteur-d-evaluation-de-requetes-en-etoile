package qengine.program;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WriteCSV<U > {
	
/*
	
	  public static void main(String args[]) {
	    
	    WriteCSV testCSV = new WriteCSV();
	    testCSV.writeCSV("c", "n", 3,3,1.2,1.3,3.3,3,1.2,2.2,3.1);
	  }
	  
	  
	*/  
	  /*nom du fichier de données | nom du dossier des requêtes | nombre de triplets
	  RDF | nombre de requêtes | temps de lecture des données (ms) | temps
	  de lecture des requêtes (ms) | temps création dico (ms) | nombre d’index |
	  temps de création des index (ms) | temps total d’évaluation du workload (ms)
	  | temps total (du début à la fin du programme) 
	  */
	  
	  
	  public void writeCSV(List<String> tableauNom, List<U> tableau, String nomFichier ) {
		
		  try (PrintWriter writer = new PrintWriter(new File(nomFichier+".csv"))) {
			  
		
			  StringBuilder sb = new StringBuilder();
			  
			  /*
			  for (int i = 0; i< tableauNom.size(); i++) {
				  sb.append(tableauNom.get(i));
			      sb.append(',');
				 
			  }
			  sb.append('\n');
			  for (int i = 0; i< tableau.size(); i++) {
				
				  sb.append("\" "+ tableau.get(i) + "\" ");
			      sb.append(',');
				 
			  }
			  sb.append('\n');
		     
		      */
			  
			  for (int i = 0; i< tableauNom.size(); i++) {
				  sb.append(tableauNom.get(i));
			      sb.append(',');
			      sb.append("\" "+ tableau.get(i) + "\" ");
			      sb.append('\n');
				 
			  }
			  
		      
		      
		     

		      writer.write(sb.toString());
		      writer.close();
		      System.out.println("done!");

		    } catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		    }
		  
	  }
	  
	  
	  

	  public void readCSVFile(String nomFichier) {
	    List<List<String>> records = new ArrayList<>();
	    try (Scanner scanner = new Scanner(new File(nomFichier + ".csv"));) {
	      while (scanner.hasNextLine()) {
	        records.add(getRecordFromLine(scanner.nextLine()));
	      }
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    }
	    System.out.println(records.toString());
	  }
	  private List<String> getRecordFromLine(String line) {
	    List<String> values = new ArrayList<String>();
	    try (Scanner rowScanner = new Scanner(line)) {
	      rowScanner.useDelimiter(",");
	      while (rowScanner.hasNext()) {
	        values.add(rowScanner.next());
	      }
	    }
	    return values;
	  }
	}



