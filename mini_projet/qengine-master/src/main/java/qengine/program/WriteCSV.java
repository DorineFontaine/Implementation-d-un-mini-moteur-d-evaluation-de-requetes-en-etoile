package qengine.program;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WriteCSV {
	
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
	  
	  
	  public void writeCSV(String nomFichier,
			  String nomDossierRQ, int nbTriplet, int nbRQ, double lectureDonnee,
			  double tempsRQ,double tempsDico, int nbIndex, 
			  double tempsIndex, double evalWorkload, double tempTotal) {
		
		  try (PrintWriter writer = new PrintWriter(new File(nomFichier+".csv"))) {
		      StringBuilder sb = new StringBuilder();
		      sb.append("nom du fichier de données");
		      sb.append(',');
		      sb.append("nom du dossier des requêtes");
		      sb.append(',');
		      sb.append("nombre de triplets RDF");
		      sb.append(',');
		      sb.append("nombre de requêtes");
		      sb.append(',');
		      sb.append("temps lecture des données (ms)");
		      sb.append(',');
		      sb.append("temps lecture des requêtes (ms)");
		      sb.append(',');
		      sb.append("temps création dico (ms)");
		      sb.append(',');
		      sb.append("nombre d’index");
		      sb.append(',');
		      sb.append("temps de création des index (ms)");
		      sb.append(',');
		      sb.append("temps total d’évaluation du workload (ms)");
		      sb.append(',');
		      sb.append("temp Total");
		      sb.append('\n');

		      sb.append(nomFichier);
		      sb.append(',');
		      sb.append(nomDossierRQ);
		      sb.append(',');
		      sb.append(nbTriplet);
		      sb.append(',');
		      sb.append(nbRQ);
		      sb.append(',');
		      sb.append(lectureDonnee);
		      sb.append(',');
		      sb.append(tempsRQ);
		      sb.append(',');
		      sb.append(tempsDico);
		      sb.append(',');
		      sb.append(nbIndex);
		      sb.append(',');
		      sb.append(tempsIndex);
		      sb.append(',');
		      sb.append(evalWorkload);
		      sb.append(',');
		      sb.append(tempTotal);
		      sb.append('\n');
		      
		      
		      
		      
		      
		     

		      writer.write(sb.toString());
		      writer.close();
		      System.out.println("done!");

		    } catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		    }
		  
	  }

	  public void readCSVFile() {
	    List<List<String>> records = new ArrayList<>();
	    try (Scanner scanner = new Scanner(new File("test.csv"));) {
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



