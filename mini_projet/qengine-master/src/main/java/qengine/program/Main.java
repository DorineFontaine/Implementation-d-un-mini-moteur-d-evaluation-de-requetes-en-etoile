package qengine.program;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

	// ========================================================================

	/**
	 * Méthode utilisée ici lors du parsing de requête sparql pour agir sur l'objet obtenu.
	 */
	public static void processAQuery(ParsedQuery query,Dictionnaire dico) {
		List<StatementPattern> patterns = StatementPatternCollector.process(query.getTupleExpr());
	//	System.out.println("variables to project : ");
		
		List<Triplet<Integer, Integer, Integer>> liste_requete = new ArrayList<>();
		//	List<Integer> liste_requete = new ArrayList<>();
			
			
			for(int i =0; i<patterns.size(); i++) {
				
				
			//	System.out.println("REQUETES" + i);
				String objet = patterns.get(i).getObjectVar().getValue().toString();
				String predicat = patterns.get(i).getPredicateVar().getValue().toString();
			
		/*		System.out.println("REQUETES" + i);
				
				System.out.println("first pattern : " + patterns.get(i));
				System.out.println("object of the first pattern : " + dico.getKey(objet));
				System.out.println("predit of the first pattern : " + dico.getKey(predicat)); 
				*/
				Triplet<Integer,Integer,Integer> tuple = Triplet.of(null, dico.getKey(predicat), dico.getKey(objet)); 
				liste_requete.add(tuple);
				
			//	liste_requete.add(dico.getKey(predicat));
			
				
				
			}
			
			
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
		
	
		MainRDFHandler rdf  = parseData();
		
		
		
		
		//*******************************************AFFICHAGE DICTIONNAIRE******************************
		Dictionnaire dico; 
		dico = rdf.getDictionnaire();
		System.out.println("\nAFFICHAGE DICTIONNAIRE\n");
		dico.affichage();
		
		//*******************************************AFFICHAGE D'INDEX***********************************
		Index index = new Index();
		index = rdf.getIndex();
		System.out.println("\nAFFICHAGE INDEX\n");
		System.out.println(  index + " \n");
		
		
		//*******************************************AFFICHAGE DES REQUETES ENCODER**********************
		parseQueries(dico);
		for (int i =0; i< liste.size(); i++) {
			
			System.out.println("Voici la " + i + " requete encodée : " + liste.get(i) );
		}
		
	}

	// ========================================================================

	/**
	 * Traite chaque requête lue dans {@link #queryFile} avec {@link #processAQuery(ParsedQuery)}.
	 */
	private static void  parseQueries(Dictionnaire dico) throws FileNotFoundException, IOException {
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

					 processAQuery(query,dico);
					
					
					
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
