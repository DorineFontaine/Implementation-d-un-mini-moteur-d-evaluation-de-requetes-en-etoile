package qengine.program;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;


/******************************CLASSE DICTIONNAIRE *************************************/

public class Dictionnaire<K, V> {
	
	Dictionary<K,V> dictionnaireEncode;
	
	
	//constructeur 
	public Dictionnaire() {
		dictionnaireEncode = new Hashtable<>();
	}
	
	//remplissage du dictionnaire 
	public void remplissageEncode(K u, V v) {
		if(!((Hashtable<K,V>) dictionnaireEncode).containsValue(v)) {
				dictionnaireEncode.put( u,v);
		}
	}
	
	public String toString() {
		return dictionnaireEncode.toString(); 
		}
	
	//on renvoie la taille du dictionnaire 
	public int size() {
		return dictionnaireEncode.size(); 
		}
	
	
	//affichage du dictionnaire 
	public void affichage() {
		for (Map.Entry<K, V> entry : ((Hashtable<K, V>) dictionnaireEncode).entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            System.out.println(  key + " : " + value);
        }
	}
	
	//On inverse le dictionnaire  Dictionnaire<K, V> --> Dictionnaire<V, K>
	public Dictionnaire<V, K> invert( ) {
		Dictionnaire<V, K> inv = new Dictionnaire<V, K>();
		for (Map.Entry<K, V> entry : ((Hashtable<K, V>) dictionnaireEncode).entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            inv.remplissageEncode(value, key);
        }
		return inv;
	}
	
	//On obtient la valeur a partir de la clée
	//Dictionnaire<K, V>
	public V decode(int valeur) {
		return dictionnaireEncode.get(valeur);
			}
		
	//Dictionnaire<V, K>	
	public V encode(String valeur) {
		return dictionnaireEncode.get(valeur);
			}
		
	
	
}
