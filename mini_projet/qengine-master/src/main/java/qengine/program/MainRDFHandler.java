package qengine.program;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.HashMap;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
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
public final class MainRDFHandler extends AbstractRDFHandler {
	
	//mesurer le temps d"exécution 
	long startTime = System.currentTimeMillis();
	long timeElapsedDico;
	long timeElapsedIndex;
	
	
	Dictionnaire<Integer,String> dictionnaireEncode;
	Dictionnaire<String,Integer > dictionnaireDecode;
	
	
	Index index_spo;
	Index index_sop;
	Index index_pos;
	Index index_pso;
	Index index_ops;
	Index index_osp;
	
	int index =0 ; 

	
	
	public MainRDFHandler() {
		dictionnaireEncode = new Dictionnaire<Integer, String>();
		dictionnaireDecode = new Dictionnaire<String,Integer >();
		
		index_spo = new Index();
		index_sop = new Index();
		index_pos = new Index();
		index_pso = new Index();
		index_ops = new Index();
		index_osp = new Index();
		
		
		
		
	}
	
 

	
	@Override
	public void handleStatement(Statement st) {
		
		String s = st.getSubject().toString() ;
		String p = st.getPredicate().stringValue();
		String o = st.getObject().toString().replaceAll("\"","");
		
		List<String> liste = new ArrayList<>();
		liste.add(s);
		liste.add(p);
		liste.add(o);
		
		
		for(int i = 0; i < 3 ;i++) {
				
			
			int size = dictionnaireEncode.size();
			
			dictionnaireEncode.remplissageEncode(size, liste.get(i));
			//dictionnaireDecode.remplissageDecode(dictionnaireEncode.decode(size),size); 
			
			
			}
		
		
		dictionnaireDecode = dictionnaireEncode.invert();
		
	
		
		
		
		/*
		long endTimeDico = System.currentTimeMillis();
		timeElapsedDico = endTimeDico - startTime;
		
		System.out.println("Execution time for the dico in milliseconds: " + timeElapsedDico);
		
		*/
		
		
		
	
		
		//DIFFERENT INDEX 
		index_spo.addTriple(dictionnaireDecode,s,p,o );
		index_sop.addTriple(dictionnaireDecode,s,o,p );
		index_pos.addTriple(dictionnaireDecode,p,o,s );
		index_pso.addTriple(dictionnaireDecode,p,s,o );
		index_ops.addTriple(dictionnaireDecode,o,p,s );
		index_osp.addTriple(dictionnaireDecode,o,s,p );
		
	/*
		long endTimeIndex = System.currentTimeMillis();
		
		timeElapsedIndex = endTimeIndex - startTime - timeElapsedDico; 
		
		System.out.println("Execution time for the index in milliseconds: " + timeElapsedIndex);
		//System.out.println(dictionnaireEncode); 
		
		
		*/
		

		
		
		
		
	
		
	};
	
	public long getTempsExecutionDico() {
		return timeElapsedDico;
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
