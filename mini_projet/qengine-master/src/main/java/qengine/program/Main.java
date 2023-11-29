package qengine.program;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.rdf4j.query.algebra.Projection;
import org.eclipse.rdf4j.query.algebra.StatementPattern;
import org.eclipse.rdf4j.query.algebra.helpers.AbstractQueryModelVisitor;
import org.eclipse.rdf4j.query.algebra.helpers.StatementPatternCollector;
import org.eclipse.rdf4j.query.parser.ParsedQuery;
import org.eclipse.rdf4j.query.parser.sparql.SPARQLParser;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;

/**
 * Programme simple lisant un fichier de requête et un fichier de données.
 * 
 * <p>
 * Les entrées sont données ici de manière statique,
 * à vous de programmer les entrées par passage d'arguments en ligne de commande comme demandé dans l'énoncé.
 * </p>
 * 
 * <p>
 * Le présent programme se contente de vous montrer la voie pour lire les triples et requêtes
 * depuis les fichiers ; ce sera à vous d'adapter/réécrire le code pour finalement utiliser les requêtes et interroger les données.
 * On ne s'attend pas forcémment à ce que vous gardiez la même structure de code, vous pouvez tout réécrire.
 * </p>
 * 
 * @author Olivier Rodriguez <olivier.rodriguez1@umontpellier.fr>
 */
final class Main {
	static final String baseURI = null;

	/**
	 * Votre répertoire de travail où vont se trouver les fichiers à lire
	 */
	static final String workingDir = "data/";

	/**
	 * Fichier contenant les requêtes sparql
	 */
	static final String queryFile = workingDir + "sample_query.queryset";

	/**
	 * Fichier contenant des données rdf
	 */
	static final String dataFile = workingDir + "sample_data.nt";
	
	static List<List< Triplet<Integer,Integer,Integer>>> liste = new ArrayList<>();
	
	static List<Set<String>> liste_reponse = new ArrayList<>();

	// ========================================================================

	/**
	 * Méthode utilisée ici lors du parsing de requête sparql pour agir sur l'objet obtenu.
	 */
	public static void processAQuery(ParsedQuery query,Dictionnaire<String,Integer > dico,Dictionnaire<Integer, String> dico1,Index index) {
		List<StatementPattern> patterns = StatementPatternCollector.process(query.getTupleExpr());
	//	System.out.println("variables to project : ");
		
		List<Triplet<Integer, Integer, Integer>> liste_requete = new ArrayList<>();
		Set<Integer> setReponseEncoder =  new HashSet<>();
		Set<String> setReponseDecoder =  new HashSet<>();	
			
		
		
			for(int i =0; i<patterns.size(); i++) {
				
				
			
				String objet = patterns.get(i).getObjectVar().getValue().toString();
				String predicat = patterns.get(i).getPredicateVar().getValue().toString();
			
				int v1 = dico.encode(predicat);
				int v2 = dico.encode(objet);
				
				
				Triplet<Integer,Integer,Integer> tuple = Triplet.of(null, v1,v2 ); 
				liste_requete.add(tuple);
				
				setReponseEncoder.addAll(index.get1(v1, v2));
				
		
				
				
				
			}
			System.out.println(setReponseEncoder);
			for(int s : setReponseEncoder){
				
				
				
				//int --> string
				setReponseDecoder.add(dico1.decode(s));
				
			}
			
			//Décode des requetes 
			liste_reponse.add(setReponseDecoder);
		
			
			
			
			liste.add(liste_requete);

			
			
			
			
			// Utilisation d'une classe anonyme
			query.getTupleExpr().visit(new AbstractQueryModelVisitor<RuntimeException>() {

			public void meet(Projection projection) {
				//System.out.println(projection.getProjectionElemList().getElements());
			}
		});
	}
	
	
	

	/**
	 * Entrée du programme
	 */
	public static void main(String[] args) throws Exception {
		
		
		/**************************************DEBUT PROGRAMME *****************************************/
		
		long startTimeProgramme = System.currentTimeMillis();
		
		
		//*******************************************AFFICHAGE DICTIONNAIRE******************************
        
        
		MainRDFHandler rdf  = parseData();
		Dictionnaire<Integer, String> dico; 
		dico = rdf.getDictionnaireEncode();
		Dictionnaire<String,Integer > dictionnaireDecode;
		dictionnaireDecode = rdf.getDictionnaireDecode();
		System.out.println("\nAFFICHAGE DICTIONNAIRE\n");
		dico.affichage();
		
		
		
	
		
		
		
		
		
		//*******************************************AFFICHAGE INDEX EXEMPLE***********************************
		
		
		
		Index index_ops = new Index();
		index_ops = rdf.getIndex("POS"); 	// vous pouvez aussi afficher d'autre index : POS, SOP etc ...
		System.out.println("\nAFFICHAGE INDEX OPS\n");
		System.out.println(  index_ops + " \n");
	
		
		
		
		
		
		
		
		//*******************************************AFFICHAGE DES REQUETES ENCODER ET REPONSE**********************
		
		//Les requetes sont encodée comme suit : SPO
		// on utilise l'indexe POS pour repondre a ces requetes
		parseQueries(dictionnaireDecode,dico,index_ops);
		
		for (int i =0; i< liste.size(); i++) {
			
			
			System.out.println("\nVoici la " + i + " requete encodée : " + liste.get(i) );
			System.out.println("\nVoici la réponse non encoder "  + liste_reponse.get(i) );
			
			
		}
		
		
		/**************************************FIN PROGRAMME *****************************************/
		long endTimeProgramme = System.currentTimeMillis();
		long timeElapsedProgramme = endTimeProgramme - startTimeProgramme;
		
		System.out.println("Execution time for the dico in milliseconds: " + timeElapsedProgramme);
		
		
		//*****************************************ECRITURE DANS UN FICHIER CSV***************************************
		
		/*nom du fichier de données | nom du dossier des requêtes | nombre de triplets
		  RDF | nombre de requêtes | temps de lecture des données (ms) | temps
		  de lecture des requêtes (ms) | temps création dico (ms) | nombre d’index |
		  temps de création des index (ms) | temps total d’évaluation du workload (ms)
		  | temps total (du début à la fin du programme) 
		  */
		
		WriteCSV testCSV = new WriteCSV();
	    testCSV.writeCSV(dataFile, queryFile, dico.size(),liste.size(),1.2,1.3,3.3,3,1.2,2.2,timeElapsedProgramme);
		
	}

	// ========================================================================

	/**
	 * Traite chaque requête lue dans {@link #queryFile} avec {@link #processAQuery(ParsedQuery)}.
	 */
	private static void  parseQueries(Dictionnaire<String,Integer > dico,Dictionnaire<Integer, String> dico1,Index index) throws FileNotFoundException, IOException {
		/**
		 * Try-with-resources
		 * 
		 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html">Try-with-resources</a>
		 */
		/*
		 * On utilise un stream pour lire les lignes une par une, sans avoir à toutes les stocker
		 * entièrement dans une collection.
		 */
		
	
		
		try (Stream<String> lineStream = Files.lines(Paths.get(queryFile))) {
			SPARQLParser sparqlParser = new SPARQLParser();
			Iterator<String> lineIterator = lineStream.iterator();
			StringBuilder queryString = new StringBuilder();

			while (lineIterator.hasNext())
			/*
			 * On stocke plusieurs lignes jusqu'à ce que l'une d'entre elles se termine par un '}'
			 * On considère alors que c'est la fin d'une requête
			 */
			{
				String line = lineIterator.next();
				queryString.append(line);

				if (line.trim().endsWith("}")) {
					ParsedQuery query = sparqlParser.parseQuery(queryString.toString(), baseURI);

				// Traitement de la requête, à adapter/réécrire pour votre programme

					 processAQuery(query,dico,dico1,index);
					
					
					
					queryString.setLength(0); // Reset le buffer de la requête en chaine vide
				}
			}
		}
	
	}

	/**
	 * Traite chaque triple lu dans {@link #dataFile} avec {@link MainRDFHandler}.
	 */
	private static MainRDFHandler parseData() throws FileNotFoundException, IOException {

		try (Reader dataReader = new FileReader(dataFile)) {
			// On va parser des données au format ntriples
			RDFParser rdfParser = Rio.createParser(RDFFormat.NTRIPLES);

			// On utilise notre implémentation de handler
			
			MainRDFHandler rdf = new MainRDFHandler();
			rdfParser.setRDFHandler(rdf);
		 
			// Parsing et traitement de chaque triple par le handler
			rdfParser.parse(dataReader, baseURI);
			
			
			
			return rdf; 
			
			
			
			
			
			
		}
		
		
	}
}
