package lse;

import java.io.*;
import java.util.*;
/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in 
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}
	
	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile) 
	throws FileNotFoundException {
		/** COMPLETE THIS METHOD **/
		
		try {
			Scanner sc = new Scanner (new File(docFile));
			
			HashMap<String, Occurrence> b = new HashMap<String, Occurrence>();
			String thisLine = "";
			
			while (sc.hasNext()) {
				thisLine = sc.nextLine();
				
				StringTokenizer stk = new StringTokenizer(thisLine);
				
				while (stk.hasMoreTokens()) {
					String s = stk.nextToken();
					Occurrence oc = new Occurrence (docFile, 1);
					
					String trun = getKeyword(s);
					
					if (trun != null) {
						if (!trun.isEmpty()) {
							if(!noiseWords.contains(trun)) {
								if(b.containsKey(trun)) {
									b.get(trun).frequency++;
								} else {
									b.put(trun, oc);
								}
							} else {	
							}
						}
						
					}
				}
			}
			sc.close();
			return b;
		} catch (FileNotFoundException f) {
			throw f;
		} catch (NullPointerException n) {
			throw n;
		}
		
	}
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeywords(HashMap<String,Occurrence> kws) {
		/** COMPLETE THIS METHOD **/
		ArrayList<Integer> intE = new ArrayList <Integer>();
		
		for (String key: kws.keySet()) {
			if(noiseWords.contains(key)) {
			continue;
			}
			if (keywordsIndex.containsKey(key)) {
				keywordsIndex.get(key).add(kws.get(key));
				
				intE = insertLastOccurrence(keywordsIndex.get(key));
				
			} else {
				ArrayList<Occurrence> occ = new ArrayList<Occurrence> ();
				occ.add(kws.get(key));
				keywordsIndex.put(key, occ);
				
				intE = insertLastOccurrence(keywordsIndex.get (key));
			}
		}
	}
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * trailing punctuation, consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyword(String word) {
		/** COMPLETE THIS METHOD **/
		boolean bullsEye = false;
		
		if (word == null) {
			return null;
		}
		
		if (word.equals(" ")) {
			return null;
		}
		
		word = word.toLowerCase();
		
		for (int i = word.length() - 1; i >= 0; i--) {
			if (Character.isAlphabetic(word.charAt(i))) {
				bullsEye = true;
			}
			
			if (!bullsEye && !Character.isAlphabetic(word.charAt(i))) {
				word = word.substring(0,i);
			}
			
			if(bullsEye && !Character.isAlphabetic(word.charAt(i))) {
				return null;
			}
		}
		if (noiseWords.contains(word.toLowerCase())) {
			return null;
		}
		word = word.toLowerCase();
		
		return word;
		
		
	}
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		/** COMPLETE THIS METHOD **/
		int last = binSearch (occs,occs.get(occs.size() - 1), 0, occs.size()-1);
		Occurrence occVal = occs.get(occs.size() -1);
		ArrayList<Integer> intE = new ArrayList<Integer>(occs.size() * 2);
		
		for (int j= 0; j <= last; j++) {
			Occurrence hold = occs.get(j);
			intE.add(hold.frequency);
		}
		intE.add(occVal.frequency);
		
		for (int i = last +1; i < occs.size() - 1; i++) {
			Occurrence hold = occs.get(i);
			intE.add(hold.frequency);
		}
		return intE;
	}
	
	private int binSearch(ArrayList<Occurrence> occs, Occurrence target, int lo, int high) {
		if(lo == high || lo > high) {
			return lo;
		} else {
			int middle = (high + lo)/2;
			Occurrence midOcc = occs.get(middle);
			
			if(midOcc.frequency > target.frequency) {
				return binSearch (occs,target,middle+1,high);
			} else if (midOcc.frequency < target.frequency) {
				return binSearch(occs,target,lo,middle-1);
			} else {
				return middle;
			}
		}
	}
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> mind = loadKeywordsFromDocument(docFile);
			mergeKeywords(mind);
		}
		sc.close();
	}
	
	private ArrayList<Occurrence> sort (ArrayList<Occurrence> occs){
		ArrayList<Occurrence> occ2 = new ArrayList<Occurrence> (occs.size() * 2);
		int largest = 0;
		int a = 0, i =0;
		int prev = 0;
		ArrayList<Integer> used = new ArrayList<Integer>();
		boolean first = true;
		
		for(i = 0; i < occs.size(); i++) {
			
			for(a= 0; a < occs.size(); a++) {
				if(!used.contains(a)) {
					if(first) {
						largest = occs.get(a).frequency;
						prev = a;
					} else {
						if(occs.get(a).frequency > largest) {
							largest = occs.get(a).frequency;
							prev = a;
						}
					}
					first = false;
				}
			}
			first = true;
			used.add(prev);
			occ2.add(occs.get(prev));
		}
		return occ2;
	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies. (Note that a
	 * matching document will only appear once in the result.) Ties in frequency values are broken
	 * in favor of the first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2
	 * also with the same frequency f1, then doc1 will take precedence over doc2 in the result. 
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches, returns null.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		/** COMPLETE THIS METHOD **/
		
		ArrayList<String> topList = new ArrayList<String>();
		ArrayList<Occurrence> temp = new ArrayList<Occurrence>();
		
		if (keywordsIndex.containsKey(kw1.toLowerCase())) {
			for(Occurrence o: keywordsIndex.get(kw1.toLowerCase())) {
				if(temp.indexOf(o)==-1) {
					temp.add(o);
					}
			}
		}
		if (keywordsIndex.containsKey(kw2.toLowerCase())) {
			
			for(Occurrence o: keywordsIndex.get(kw2.toLowerCase())) {
				if(temp.indexOf(o)==-1) {
					temp.add(o);
					}
			}
		}
		if(!temp.isEmpty()) {
			temp = sort(temp);
		}
		
		if(temp.size() < 6) {
			for (int i = 0; i < temp.size(); i++) {
				if (!topList.contains(temp.get(i).document)) {
					topList.add(i, temp.get(i).document);
				} else {
			}
		}
	
	} else {
		for(int i = 0; i <temp.size();i++) {
			if(topList.size()==5) {
				break;
			}
			if(!topList.contains(temp.get(i).document)) {
				topList.add(i,temp.get(i).document);
			}
		} 
	}
		if(topList.isEmpty()) {
			return topList;
			
		} else {
			return topList;
		}
}
}
