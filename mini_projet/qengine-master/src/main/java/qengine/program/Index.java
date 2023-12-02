package qengine.program;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Index {
	
	
	Map<Integer, Map<Integer, Set<Integer>>> index_spo;
	
	
	public Index() {
		
	
		index_spo = new HashMap<>();
		
		
	}
	
	public String toString() {
		return index_spo.toString(); 
	}
	
	

	//CREATION DES INDEXES 
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
	
	public int size() {
		return index_spo.size(); 
	}

	
	
	
	public  Set<Integer> get1(int v1, int v2) {
		
		//Map<Integer, Set<Integer>> value1 = new HashMap<>(); 
		Set<Integer> set =  new HashSet<>();
		for (Map.Entry<Integer, Map<Integer, Set<Integer>>> entry : index_spo.entrySet()) {
			
			Integer key1 = entry.getKey();
			
			
			//je compare la clée 
			if(v1 == key1) {
				
				Map<Integer, Set<Integer>> value  = entry.getValue();
         
				
				// je compare la clée de la deuxieme maps
				
				for (Map.Entry<Integer, Set<Integer>> entry2 : value.entrySet()) {
					Integer key2 = entry2.getKey();
					
				
					
					//je compare la clée 
					if(v2 == key2) {
						
					    set = entry2.getValue();
					    return set; 
					//	System.out.println("Clé1 : " + key1 + "Clé2 : " + key2 + ", Valeur: " + set);
						
						
					}
						
					
					
				}
				
			
			
        }
			
		
	}
	
	 return set; 
	
	}
	
	
	
	
	
	
	
	

	
	
	
	

}
