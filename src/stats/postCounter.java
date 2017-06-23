package stats;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Collections;
import java.util.Comparator;


public class postCounter {
	static List<String> records = new ArrayList<String>();
	static int lineCount = 0;
	static String line;
	static String postDate = null;
	static String timestamp = null;
	static String author = null;
	
	public static boolean ASC = true;
	public static boolean DESC = false;

	public static void main(String[] args) {
		
		System.out.print("Here are the results");
		//System.out.println(data);
		//System.out.println(records);
		
		//show the sorted list of those who posted
		Map<String, Integer> data = startMachine();
		Map<String, Integer> sortedMapDesc = sortByComparator(data, DESC);
		showAuthorCountSorts(sortedMapDesc);
		
		//show posts which were skipped and not included in our stats
		//printNonRelevantMessages(records);
	}
	

	private static void showAuthorCountSorts(Map<String, Integer> data) {
		// TODO Auto-generated method stub
		// for (int value : data.values()) {
		// data.get(key)
		// }

		int count = 0;
		for (Entry<String, Integer> entry : data.entrySet()) {
			count++;
			// System.out.println("Key : " + entry.getKey() + " Value : "+
			// entry.getValue());
			// if (count<13)
			System.out.println(count + ". " + entry.getKey() + ": " + entry.getValue());
		}
	}

	private static void printNonRelevantMessages(List<String> nonRelevantMessages) {
		int count = 0;
		for (String message : nonRelevantMessages) {
			count++;
			//if (count<1650) 
				System.out.println(count + ".  " + message);
		}
	}

	private static Map<String, Integer> startMachine() {
		// TODO Auto-generated method stub
		String filename = "data//Lifetime-Data-FullWithFaisals-merge.txt";
		System.out.println(" for filename " + filename);
		HashMap<String, Integer> authors = new HashMap<String, Integer>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String postData = "";
			int authorPostCount = 0;

			while ((line = reader.readLine()) != null) {
				// now check for the data of the post
				lineCount++;
				postDate = "";
				timestamp = "";
				author="";
				postData = "";
				authorPostCount = 0;

				// figure out if this is a useful line with our needed data
				if (line.indexOf(',') > -1 & line.indexOf(": ") > -1 & line.indexOf('-') > -1 & line.indexOf(',') < 10 & line.indexOf('-') < 21) {
					// what is the time stamp
					if (line.indexOf(',') > 0)
						postDate = line.substring(0, line.indexOf(',')).trim();

					//
					if (line.indexOf('-') > 0)
						timestamp = line.substring(line.indexOf(','), line.indexOf('-')).trim();

					// who is the author
					if (line.indexOf('-') > 0 && line.indexOf(": ") > 0 && line.indexOf(": ") > line.indexOf('-')) {
						author = line.substring(line.indexOf('-') + 1, line.indexOf(": ")).trim();
						if (authors.containsKey(author)) {
							authorPostCount = authors.get(author);
						}
						authors.put(author, authorPostCount + 1);
					} else {
						records.add(line);
						postDate = "";
						timestamp = "";
						author="";
						postData = "";
						authorPostCount = 0;
					}

					// Content: what did he post
					if (line.indexOf(": ") > 0)
						postData = line.substring(line.indexOf(": "), line.length()).trim();
				} else {
					// This is a useless line so SKIP IT
					records.add(line);
					postDate = "";
					timestamp = "";
					author="";
					postData = "";
					authorPostCount = 0;
				}

			}
			reader.close();
			return authors;
		} catch (Exception e) {
			System.err.format("Exception occurred trying to read '%s'.", filename);
			System.out.println("lines processed so far: " + lineCount);
			System.out.println("Last successfully processed line: " + postDate + timestamp + " " + author);
			e.printStackTrace();
			return null;
		}

	}

	

	private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order) {

		List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				if (order) {
					return o1.getValue().compareTo(o2.getValue());
				} else {
					return o2.getValue().compareTo(o1.getValue());
				}
			}
		});

		// Maintaining insertion order with the help of LinkedList
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Entry<String, Integer> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

}
