package qengine.program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Index {
	
	//OPS
	//List<Triplet<Integer, Integer, Integer>> giantTable;
	Map<Integer, Map<Integer, Set<Integer>>> index;
	
	
	public Index() {
		
	//	giantTable = new ArrayList<>();
		index = new HashMap<>();
	}
	
	public String toString() {
		return index.toString(); 
	}
	
	
	public void addTriple(Dictionnaire dico, String premier, String second, String troisieme){
        if (index.containsKey(dico.getKey(premier))){
            if (index.get(dico.getKey(premier)).containsKey(dico.getKey(second))){
                index.get(dico.getKey(premier)).get(dico.getKey(second)).add(dico.getKey(troisieme));
            } else {
                Set<Integer> leaf = new HashSet<>();
                leaf.add(dico.getKey(troisieme));
                index.get(dico.getKey(premier)).put(dico.getKey(second), leaf);
            }
        } else {
            Set<Integer> leaf = new HashSet<>();
            leaf.add(dico.getKey(troisieme));
            Map<Integer, Set<Integer>> map = new HashMap<>();
            map.put(dico.getKey(second), leaf);
            index.put(dico.getKey(premier), map);
        }
    }
	
	
	public Map<Integer, Set<Integer>> get(int valeur) {
		
		return index.get(valeur); 
	}
	
	
	
	// index hasmap d'une hasmap d'un arraylist 
	
	
	
	
	

	
	
	
	

}
