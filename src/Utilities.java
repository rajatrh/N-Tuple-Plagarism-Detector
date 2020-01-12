import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Utilities {
    
	 // Parse the synonyms into an hashmap with each word and corresponding synonyms
    public static HashMap<String, Set<String>> mapTheSynonyms(String synonymsArray)
    {
        HashMap<String, Set<String>> synMap = new HashMap<String, Set<String>>();
        
        String[] setOfWords = synonymsArray.split("\\|");
        for(String set : setOfWords) {
        	String[] words = set.toLowerCase().trim().split("\\s+");
        	Set<String> wordSet = new HashSet<String>();
        	wordSet.addAll(Arrays.asList(words));
        	for(String word: words) {
        		if (!synMap.containsKey(word)) {
        			synMap.put(word, wordSet);
        		}
	        }
        }
        
        return synMap;
    }
}