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
	
	Dictionnaire dictionnaire;
	Index index;
	List<List<Triplet<Integer, Integer, Integer>>> liste = new ArrayList<>();
	
	

	public MainRDFHandler() {
		 dictionnaire = new Dictionnaire();
		 index = new Index();
		
		
	}
	
 

	
	@Override
	public void handleStatement(Statement st) {
		
		
	//	System.out.println(st.getObject() +""+ st.getPredicate()+ st.getSubject());
		
		dictionnaire.remplissage(st);
		/*bigTable.remplissageGiantTable(dictionnaire.getKey(st.getObject().toString())
										,dictionnaire.getKey(st.getPredicate().stringValue()) 
										,dictionnaire.getKey(st.getSubject().toString()));
		
		
		*/
		index.addTriple(dictionnaire,st.getSubject().toString(),st.getPredicate().stringValue(),st.getObject().toString() );
		
	//	System.out.println(index);
		
		
		
	
		
	};
	
	public Dictionnaire getDictionnaire() {
		return dictionnaire;
		
		
	}
	
	public Index getIndex() {
		return index;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}