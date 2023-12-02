package qengine.program;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.rio.helpers.AbstractRDFHandler;



/**
 * Le RDFHandler intervient lors du parsing de données et permet d'appliquer un traitement pour chaque élément lu par le parseur.
 * 
 * <p>
 * Ce qui servira surtout dans le programme est la méthode {@link #handleStatement(Statement)} qui va permettre de traiter chaque triple lu.
 * </p>
 * <p>
 * À adapter/réécrire selon vos traitements.
 * </p>
 */
/****************************************CREATION INDEX ET DICO*****************************/

public final class MainRDFHandler extends AbstractRDFHandler {
	
	//valeur pour les temps d"exécution 
	long startTime = System.currentTimeMillis();
	long timeElapsedDico;
	long timeElapsedIndex;
	int nbTriplet; 
	
	//les dictionnaires 
	Dictionnaire<Integer,String> dictionnaireEncode;
	Dictionnaire<String,Integer > dictionnaireDecode;
	
	//Les six combinaisons  d'index  
	Index index_spo;
	Index index_sop;
	Index index_pos;
	Index index_pso;
	Index index_ops;
	Index index_osp;
	
	int index =0 ; 

	
	//constructeur 
	public MainRDFHandler() {
		dictionnaireEncode = new Dictionnaire<Integer, String>();
		dictionnaireDecode = new Dictionnaire<String,Integer >();
		
		index_spo = new Index();
		index_sop = new Index();
		index_pos = new Index();
		index_pso = new Index();
		index_ops = new Index();
		index_osp = new Index();
		nbTriplet = 0; 
		}
	
	@Override
	public void handleStatement(Statement st) {
		
		//on compte le nombre de triplet du fichier data
		nbTriplet++; 
		
		//on récupére chaque terme des triplets RDF : objet, sujet, predicat
		String s = st.getSubject().toString() ;
		String p = st.getPredicate().stringValue();
		String o = st.getObject().toString().replaceAll("\"","");
		
		List<String> liste = new ArrayList<>();
		liste.add(s);
		liste.add(p);
		liste.add(o);
		
		//creation du dictionnaire<K,V>
		for(int i = 0; i < 3 ;i++) {
				int size = dictionnaireEncode.size();
				dictionnaireEncode.remplissageEncode(size, liste.get(i));
			}
		
		//creation du dictionnaire<V,K>
		dictionnaireDecode = dictionnaireEncode.invert();
		
		//mesure de la création du dico
		long endTimeDico = System.currentTimeMillis();
		timeElapsedDico = endTimeDico - startTime;
			
		//création des index
		index_spo.addTriple(dictionnaireDecode,s,p,o );
		index_sop.addTriple(dictionnaireDecode,s,o,p );
		index_pos.addTriple(dictionnaireDecode,p,o,s );
		index_pso.addTriple(dictionnaireDecode,p,s,o );
		index_ops.addTriple(dictionnaireDecode,o,p,s );
		index_osp.addTriple(dictionnaireDecode,o,s,p );
		
		//mesure de la création de l'index
		long endTimeIndex = System.currentTimeMillis();
		timeElapsedIndex = endTimeIndex - startTime; 	
	};
	
	//GETTERS
	public int getnbTriplet() {
		return nbTriplet;
	}
	
	public long getTempsExecutionDico() {
		return timeElapsedDico;
	}
	public long getTempsExecutionIndex() {
		return timeElapsedIndex;
	}
	
	public Dictionnaire<Integer, String> getDictionnaireEncode() {
		return dictionnaireEncode;
		
		
	}
	
	public Dictionnaire<String, Integer > getDictionnaireDecode() {
		return dictionnaireDecode;
		
		
	}
	
	public Index getIndex(String type) {
		
		
		switch(type) {
		
		case "SPO":
			
			return index_spo;
			
		case "PSO":
			 
			return index_pso;
		
		case "POS":
			
			return index_pos;
			
		case "OSP":
			
			return index_osp;
			
		case "OPS":
		
			return index_ops;
			
		case "SOP":
			
			return index_sop;
			
		 default:
			 return index_spo;
			 
		}
		
		
		
		
	}
	

}
