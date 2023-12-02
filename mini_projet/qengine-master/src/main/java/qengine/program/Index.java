package qengine.program;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**********************************************CLASSE INDEX****************************************************/

public class Index {
	
	Map<Integer, Map<Integer, Set<Integer>>> index_spo;
	
	//constructeur
	public Index() {
		index_spo = new HashMap<>();
		}
	
	public String toString() {
		return index_spo.toString(); 
	}
	
	

	//remplissage des index
	public void addTriple(Dictionnaire<String,Integer > dico, String premier, String second, String troisieme){
        if (index_spo.containsKey(dico.encode(premier))){
            if (index_spo.get(dico.encode(premier)).containsKey(dico.encode(second))){
                index_spo.get(dico.encode(premier)).get(dico.encode(second)).add(dico.encode(troisieme));
            } else {
                Set<Integer> leaf = new HashSet<>();
                leaf.add(dico.encode(troisieme));
                index_spo.get(dico.encode(premier)).put(dico.encode(second), leaf);
            }
        } else {
            Set<Integer> leaf = new HashSet<>();
            leaf.add(dico.encode(troisieme));
            Map<Integer, Set<Integer>> map = new HashMap<>();
            map.put(dico.encode(second), leaf);
            index_spo.put(dico.encode(premier), map);
        }
    }
	
	//renvoie la taille de l'index 
	public int size() {
		return index_spo.size(); 
	}

	//comparaison des deux premiere clée de l'index 
	//on renvoie le set correspondant au deux clée 
	public  Set<Integer> get1(int v1, int v2) {
		
		Set<Integer> set =  new HashSet<>();
		for (Map.Entry<Integer, Map<Integer, Set<Integer>>> entry : index_spo.entrySet()) {
			
			//je récupére la premiere clée 
			Integer key1 = entry.getKey();
			
			//je compare la clée 
			if(v1 == key1) {
				
				//je stocke la valeur correspondant a la clée qu'on cherche 
				Map<Integer, Set<Integer>> value  = entry.getValue();
         
				// je compare la clée de la deuxieme maps
				for (Map.Entry<Integer, Set<Integer>> entry2 : value.entrySet()) {
					
					//je récupére la deuxieme clée 
					Integer key2 = entry2.getKey();
					
					//je compare la clée 
					if(v2 == key2) {
						set = entry2.getValue();
					    return set; 
					
					}
				}
			}
		}
	 return set; 
	}
	
}
