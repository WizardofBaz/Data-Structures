/*package lse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
	public static void main(String[] args) throws FileNotFoundException{
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter the DOC file: ");
		String docsFile = sc.nextLine().trim();
		ArrayList<String> docFile = new ArrayList<String>(10);
		Scanner doc = new Scanner (new File (docsFile));
		
		System.out.println();
		
		while (doc.hasNextLine()) {
			docFile.add(doc.nextLine());
		}
		doc.close();
		
		//noise
		System.out.print("Enter the noise file: ");
		String noise = sc.nextLine().trim();
		
		System.out.println();
		
		System.out.print("what are you searching?");
		String wordsS= sc.nextLine().trim();
		System.out.println();
		
		System.out.print("what are you searching?");
		String wordT = sc.nextLine().trim();
		System.out.println();
		
		sc.close();
		
		
		//empty?
		if(!docFile.isEmpty()) {
			LittleSearchEngine ls = new LittleSearchEngine();
			ls.makeIndex(docsFile, noise);
			
			//anotha one
			ArrayList<Occurrence> test = new ArrayList<Occurrence>();
			for (int i = 0; i<9; i++) {
				Occurrence oc = new Occurrence (noise, 25 -2*i);
				test.add(i,oc);
			}
			test.add(new Occurrence(noise,14));
			for(Occurrence o: test) {
				//System.out.println(o.frequencey);
			}
			System.out.println();
			ArrayList<Integer> intTest = ls.insertLastOccurrence(test);
			for(Integer i: intTest) {
				System.out.println(i);
			}
			//top5search
			ArrayList<String> search = ls.top5search(wordsS, wordT);
			//System.out.println("\n" + search.size());
			
			for(String s: search) {
				System.out.println(s);
			}
			for(int i = 0; i<ls.noiseWords.toArray().length;i++) {
				//System.out.println(ls.noiseWords.toArray()[i]);
			}
			for(String key: ls.keywordsIndex.keySet()) {
				//System.out.println("key::" + key);
				
				for(Occurrence o: ls.keywordsIndex.get(key)) {
					//System.out.println("   document::" + o.document + "  frequency:: " + o.frequency);
				}
				//System.out.println();
			}
		}
	}
}
*/