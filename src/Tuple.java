import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.StringTokenizer;

public class Tuple {
	private int size;
	private ArrayList<String> words;
	
	/*
	 * Constructor to initialize the setOfWords
	 */
 
	public Tuple(int n) {
		words = new ArrayList<String>();
		this.size = n;
	}
	
	/*
	 * Setter and Getter methods
	 */
	
	public void setSize(int n) {
		this.size = n;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void addWord(String word) {
		words.add(word);
	}
	
	public void addWords(String[] words) {
		this.words.addAll(Arrays.asList(words));
	}
	
	public void setWord(ArrayList<String> words) {
		this.words = words;
	}
	
	public ArrayList<String> getWords() {
		return words;
	}
	
	public String getWordAt(int index) {
		String ret = "";
		try {
			ret = words.get(index);
		} catch(IndexOutOfBoundsException e) {
		}
		
		return ret;
	}
	
	/*
	 * Utility Function to compare 2 tuples
	 */
	
	public boolean compareTuples(Tuple a, Tuple b, HashMap<String, Set<String>> synMap) {
		ArrayList<String> aWords = new ArrayList<>(a.getWords());
		ArrayList<String> bWords = new ArrayList<>(b.getWords());
		
		/*
		 * Check if the array of String are equal,
		 * return true straight away
		 */
		
		if (aWords.equals(bWords)) return true;
		
		for (int i=0; i <this.size; i++) {
			String aWord = aWords.get(i);
			String bWord = bWords.get(i);
			
			/*
			 * We have to check for the exact order only ?
			 * eg ) [He ran today, Today he jog] would return false ?
			 */
			if (!aWord.equals(bWord)) {
				/*
				 * If synMap doesn't contain the key, we are good to go, tuples differ by a word.
				 */
				if (!synMap.containsKey(aWord)) {
					return false;
				}
				
				Set<String> listOfSynonyms = synMap.get(aWord);
				
				if (!listOfSynonyms.contains(bWord)) {
					/*
					 * If there is a key present in synMap but the synonyms set doesn't
					 * contain the word, the tuple differs by a word. So we are good to go!
					 */
					return false;
				}
			}
			
		}
		/*
		 * Everything matched so far. So we are good to go!
		 */
		return true;
	}
	
	/*
	 * Another form of the above method
	 */

	public boolean checkIfMatches(Tuple a, HashMap<String, Set<String>> synMap ) {
		return compareTuples(this, a, synMap);
	}
	
	/*
	 * ToString overridden
	 */
	
	@Override
	public String toString() {
		return "SIZE: " + this.size + " WORDS: " + this.words;
	}
	
	/*
	 * Generate N-Tuples from a given line.
	 */
	
	public static ArrayList<Tuple> generateTuples(int N, String line) {
		ArrayList<Tuple> listOfTuples = new ArrayList<Tuple>();
		
		String[] wordsInLine = generateWords(line);
		/*for (String w : wordsInLine) {
			System.out.println(w);
		  }
		*/
		
		/*
		 * Check if the list of words is less than N
		 */
		
		if (wordsInLine.length < N) {
			return listOfTuples;
		}
		
		for(int i=0; i<=wordsInLine.length - N; i++) {
			Tuple tup = new Tuple(N);
			tup.addWords(Arrays.copyOfRange(wordsInLine, i, i+N));
			listOfTuples.add(tup);
		}
		return listOfTuples;
	}
	
	/*
	 * Generate Words List from a sentence
	 */
	
	private static String[] generateWords(String line) {
		ArrayList<String> wordsList = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(line);
		while(tokenizer.hasMoreTokens()) {
			wordsList.add(tokenizer.nextToken());
		}
		
		String[] words = new String[wordsList.size()];
		return wordsList.toArray(words);
	}
}
