package qengine.program;


import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.rdf4j.model.Statement;




public class Dictionnaire<K, V> {
	
	Dictionary<K,V> dictionnaireEncode;
	
	
	
	public Dictionnaire() {
		
		dictionnaireEncode = new Hashtable<>();
	}
	
	public void remplissageEncode(K u, V v) {
		
		
		
		
		
		
			
			if(!((Hashtable) dictionnaireEncode).containsValue(v)) {
				dictionnaireEncode.put( u,v);
			
		}
		
	}
	
	public void remplissageDecode(K u, V v) {
		
		dictionnaireEncode.put(u,v);
			}
	
	
	/*
	public void remplissageEncode(Statement st) {
		
	
		List<String> liste = new ArrayList<>();
		liste.add(st.getPredicate().stringValue());
		liste.add(st.getObject().toString());
		liste.add(st.getSubject().toString());
		
		
		
		for(int i = 0; i < 3 ;i++) {
			
			if(!((Hashtable) dictionnaireEncode).containsValue(liste.get(i))) {
				dictionnaireEncode.put( dictionnaireEncode.size(),liste.get(i));
			}
		}
		
	}
	*/
	
	public String toString() {
		return dictionnaireEncode.toString(); 
	}
	
	
	
	
	
	
	public int size() {
		
		return dictionnaireEncode.size();
		
	}
	
	public void affichage() {
		
		
        for (Map.Entry<K, V> entry : ((Hashtable<K, V>) dictionnaireEncode).entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            System.out.println(  key + " : " + value);
        }
	}
	
	
	public Dictionnaire<V, K> invert( ) {

		Dictionnaire<V, K> inv = new Dictionnaire<V, K>();
		
		for (Map.Entry<K, V> entry : ((Hashtable<K, V>) dictionnaireEncode).entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            inv.remplissageEncode(value, key);
        }

	    
	    return inv;
	}
	

	
	//créer une fonction inverse pour décoder 
	
	
	//marche bien 
		public V decode(int valeur) {
			
			//je rentre la clée pour obtenir la valeur 
			return dictionnaireEncode.get(valeur);
			
		}
		
		
		//ne marche pas bien 
		public V encode(String valeur) {
			
			
			return dictionnaireEncode.get(valeur);
			
		}
		
	
	
}
