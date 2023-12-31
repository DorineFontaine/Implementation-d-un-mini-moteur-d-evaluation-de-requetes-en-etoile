package qengine.program;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.eclipse.rdf4j.rio.ntriples.NTriplesParser;

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
	
	static long tempslectureRequete = 0;

	// ========================================================================

	/**
	 * Méthode utilisée ici lors du parsing de requête sparql pour agir sur l'objet obtenu.
	 */
	public static void processAQuery(ParsedQuery query,Dictionnaire<String,Integer > dico,Dictionnaire<Integer, String> dico1,Index index) {
		List<StatementPattern> patterns = StatementPatternCollector.process(query.getTupleExpr());
		
		List<Triplet<Integer, Integer, Integer>> liste_requete = new ArrayList<>();
		Set<Integer> setReponseEncoder =  new HashSet<>();
		Set<String> setReponseDecoder =  new HashSet<>();	
			
			//Les requetes sont encodée comme suit : SPO
			// on utilise l'index POS pour repondre a ces requetes
		
			for(int i =0; i<patterns.size(); i++) {
				
				//On récupere les termes de la requetes 
				String objet = patterns.get(i).getObjectVar().getValue().toString();
				String predicat = patterns.get(i).getPredicateVar().getValue().toString();
			
				//On encode la requetes a l'aide du dico
				int v1 = dico.encode(predicat);
				int v2 = dico.encode(objet);
				
				//On test les termes de la requetes dans l'index 
				Triplet<Integer,Integer,Integer> tuple = Triplet.of(null, v1,v2 ); 
				liste_requete.add(tuple);
				setReponseEncoder.addAll(index.get1(v1, v2));
			}
			
			for(int s : setReponseEncoder){
				//On décode 
				//int --> string
				setReponseDecoder.add(dico1.decode(s));
			}
			
				//On ajoute les requetes decoder a un tableau 
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
        
		//evaluation du temps de la fonction parseData
		long tempslectureDonnee; 
		MainRDFHandler rdf  = parseData();
		long endTimeDonnee = System.currentTimeMillis();
		tempslectureDonnee =  endTimeDonnee -startTimeProgramme; 
		
		//On récupére le dico 
		Dictionnaire<Integer, String> dico; 
		dico = rdf.getDictionnaireEncode();
		Dictionnaire<String,Integer > dictionnaireDecode;
		dictionnaireDecode = rdf.getDictionnaireDecode();
		//System.out.println("\nAFFICHAGE DICTIONNAIRE\n");
		//dico.affichage();
		
		//on récupére le nombre de triplet 
		int nbTriplet = rdf.getnbTriplet() ;
		
		//******************************VARIABLE POUR LE TEMPS D'EXECUTION*********************************
		long tempsdico = rdf.getTempsExecutionDico();
		long tempsIndex = rdf.getTempsExecutionIndex();
	  
		
		
		
		
		//*******************************************AFFICHAGE INDEX EXEMPLE***********************************
		
		//on récupére les index du fichier MainRDF
		Index index_ops = new Index();
		index_ops = rdf.getIndex("POS"); 	// vous pouvez aussi afficher d'autre index : POS, SOP etc ...
		//System.out.println("\nAFFICHAGE INDEX OPS\n");
		//System.out.println(  index_ops + " \n");
	
		//*******************************************AFFICHAGE DES REQUETES ENCODER ET REPONSE**********************
		
		
		//lecture, encodage, reponse et décodage des requetes 
		parseQueries(dictionnaireDecode,dico,index_ops);
		
		//affichage du resulats 
		for (int i =0; i< liste.size(); i++) {
			//System.out.println("\nVoici la " + i + " requete encodée : " + liste.get(i) );
			//System.out.println("\nVoici la réponse non encoder "  + liste_reponse.get(i) );
		}
	
		/**************************************FIN PROGRAMME *****************************************/
		long endTimeProgramme = System.currentTimeMillis();
		long timeElapsedProgramme = endTimeProgramme - startTimeProgramme;
		
		//*****************************************ECRITURE DANS UN FICHIER CSV***************************************
		
		/*écriture des informations suivantes dans un fichier csv 
			nom du fichier de données | nom du dossier des requêtes | nombre de triplets
		  	RDF | nombre de requêtes | temps de lecture des données (ms) | temps
		  	de lecture des requêtes (ms) | temps création dico (ms) | nombre d’index |
		  	temps de création des index (ms) | temps total d’évaluation du workload (ms) ??
		   	temps total (du début à la fin du programme) 
		*/
		List<String> listenomFichier = Arrays.asList("nom du fichier de données", "nom du dossier des requêtes", 
				"nombre de triplets RDF", "nombre de requêtes", "temps lecture des données (ms)", 
				"temps lecture des requêtes (ms)", "temps création dico (ms)", "nombre d’index",
				"temps de création des index (ms)", "temps total d’évaluation du workload (ms)", "temp Total");

		
		List<Object> tab = Arrays.asList(dataFile, queryFile, nbTriplet,liste.size(),tempslectureDonnee - tempsdico,tempslectureRequete,tempsdico,index_ops.size(),tempsIndex,2.2,timeElapsedProgramme);
		WriteCSV<Object> executionTimeCSV = new WriteCSV<Object>();
		executionTimeCSV.writeCSV(listenomFichier,tab,dataFile );
		
		//ecriture des reponses des requetes sur un fichier csv
		List<String> listenomRequete = Arrays.asList("Réponse requete 1 ", "Réponse requete 2  ","Réponse requete 3 ");
		WriteCSV<Set<String>> requeteCSV = new WriteCSV<Set<String>>();
		requeteCSV.writeCSV(listenomRequete,liste_reponse,"Réponse requêtes" );
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
		
		long startTimeQuery = System.currentTimeMillis();
		
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
		
		long endTimeRequete = System.currentTimeMillis();
		tempslectureRequete = endTimeRequete - startTimeQuery; 
	
	}

	/**
	 * Traite chaque triple lu dans {@link #dataFile} avec {@link MainRDFHandler}.
	 */
	private static MainRDFHandler parseData() throws FileNotFoundException, IOException {

		try (Reader dataReader = new FileReader(dataFile)) {
			
			// On va parser des données au format ntriples
			//RDFParser rdfParser = Rio.createParser(RDFFormat.NTRIPLES);
			NTriplesParser rdfParser = new NTriplesParser(); 
			// On utilise notre implémentation de handler
			
			MainRDFHandler rdf = new MainRDFHandler();
			rdfParser.setRDFHandler(rdf);
		 
			// Parsing et traitement de chaque triple par le handler
			rdfParser.parse(dataReader, baseURI);
			
			return rdf; 
		}
	}
}
