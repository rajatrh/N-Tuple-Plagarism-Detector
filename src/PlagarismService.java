import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/*
 * Service that can be instantiated to
 * a) Generate the synonym map
 * b) Generate tuples from strings
 * c) measure plagarism b/w any sets of N-tuples 
 */

public class PlagarismService {
	
	HashMap<String, Set<String>> synMap;
	static boolean enableDebugLog;
	static int N;
	
	PlagarismService(String synonymsArray, int n) {
		synMap = mapTheSynonyms(synonymsArray);
		enableDebugLog = false;
		N = n;
	}
	
	PlagarismService(String synonymsArray, int n, boolean enableLogs) {
		synMap = mapTheSynonyms(synonymsArray);
		enableDebugLog = enableLogs;
		N = n;
	}
	
	/*
	 * Parse the synonyms into an Hash Map with each word
	 * and corresponding synonyms
	 */
    private static HashMap<String, Set<String>> mapTheSynonyms(String synonymsArray)
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
        printDebugLogs("Dictionary Created :" + synMap);
        return synMap;
    }
    
    /*
	 * Convert strings into Tuples using the static method in the tuples class
	 * Returns float value
	 */
    
    public float measurePlagarism(String originalString, String plagString) {
    	float measure = 0;

    	ArrayList<Tuple> aTuples = Tuple.generateTuples(N, originalString.toLowerCase());
    	printDebugLogs("Tuples in original String :" + aTuples);
		
		ArrayList<Tuple> bTuples = Tuple.generateTuples(N, plagString.toLowerCase());
		printDebugLogs("Tuples in plagarised String :" + bTuples);
		
		measure = measurePlagarism(aTuples, bTuples);
		printDebugLogs("Plagarism Measure (float) :" + measure);
		return measure;
    }
    
    /*
	 * Plagarism rounded to Integer
	 */
    
    public int measureRoundedPlagarism(String originalString, String plagString) {
    	int measure = 0;
    	measure =  (int) measurePlagarism(originalString, plagString);
		printDebugLogs("Plagarism Measure (int) :" + measure);
		return measure;
    }
	

    /*
	 * Actual method to calculate the plagrism b/w set of tuples
	 */
   
	public float measurePlagarism(ArrayList<Tuple> aTuples, ArrayList<Tuple> bTuples) {
		// Counter to keep track of common tuples
		int matchCount = 0;

		// If original String contains nothing, no plagarism
		if (aTuples.size() == 0 || bTuples.size() == 0) {
			return 0;
		}

		// If Map each tuple in the both the sets of tuples
		for (Tuple aTuple: aTuples) {
			for (Tuple bTuple: bTuples) {
				if (aTuple.checkIfMatches(bTuple, synMap)) {
					matchCount++;
					printDebugLogs("Match Found @ " + aTuple + bTuple);
				}
			}
		}
		
		printDebugLogs("Found Matches :" + matchCount);
		printDebugLogs("Total Tuples in original String :" + aTuples.size());
		printDebugLogs("Total Tuples in plagarized String :" + bTuples.size());
		
		int denom = aTuples.size();
		if (bTuples.size() < aTuples.size()) {
			denom = bTuples.size();
			
		}
		return (float) ((matchCount * 100.0)/denom);
	}
	
	// Logs can be enabled by passing true in the constructor
	static void printDebugLogs(String log) {
		if(enableDebugLog) {
			System.out.println(log);
		}
	}
}
