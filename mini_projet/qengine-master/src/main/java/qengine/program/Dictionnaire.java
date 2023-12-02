package qengine.program;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;




public class Dictionnaire<K, V> {
	
	Dictionary<K,V> dictionnaireEncode;
	
	
	
	public Dictionnaire() {
		
		dictionnaireEncode = new Hashtable<>();
	}
	
	public void remplissageEncode(K u, V v) {
		
		
		
		
		
		
			
			if(!((Hashtable<K,V>) dictionnaireEncode).containsValue(v)) {
				dictionnaireEncode.put( u,v);
			
		}
		
	}
	
	public void remplissageDecode(K u, V v) {
		
		dictionnaireEncode.put(u,v);
			}
	
	
	
	
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
