package qengine.program;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/********************************ECRITURE ET LECTURE DANS UN FICHIER CSV********************************/ 

public class WriteCSV<U > {
	

	 
	  
	  public void writeCSV(List<String> tableauNom, List<U> tableau, String nomFichier ) {
		
		  try (PrintWriter writer = new PrintWriter(new File(nomFichier+".csv"))) {
			  
			  StringBuilder sb = new StringBuilder();
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



