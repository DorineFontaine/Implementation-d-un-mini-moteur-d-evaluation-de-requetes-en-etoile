package qengine.program;


import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import org.eclipse.rdf4j.model.Statement;




public class Dictionnaire {
	
	Dictionary<Integer,String> dictionnaire1;
	
	
	
	public Dictionnaire() {
		
		dictionnaire1 = new Hashtable<>();
	}
	
	
	public void remplissage(Statement st) {
		
	
		List<String> liste = new ArrayList<>();
		liste.add(st.getPredicate().stringValue());
		liste.add(st.getObject().toString());
		liste.add(st.getSubject().toString());
		
		
		
		for(int i = 0; i < 3 ;i++) {
			
			if(!((Hashtable) dictionnaire1).containsValue(liste.get(i))) {
				dictionnaire1.put( dictionnaire1.size(),liste.get(i));
			}
		}
		
	}
	
	
	public int size() {
		
		return dictionnaire1.size();
		
	}
	
	public void affichage() {
		for (int i =0; i< dictionnaire1.size(); i++) {
			
			System.out.println(i+" " + dictionnaire1.get(i) );
		}
	}
	
	//renvoie la clée d'un valeur 
	public int getKey( String o) {
		int key;
		String objet;
		Enumeration <Integer> k;
		
		k = dictionnaire1.keys();
		while (k.hasMoreElements()) {
            key   = k.nextElement();
            objet = dictionnaire1.get(key);
            if ( objet.equals(o) ) {
            	
            	return key;
            }
        }  
		
		return -1;
}
	
	//créer une fonction inverse pour décoder 
	
		public String decode(int valeur) {
			
			
			return dictionnaire1.get(valeur);
			
		}
		//faire dans les deux sens le dico 
	
	
}
