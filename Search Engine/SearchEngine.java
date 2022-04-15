// Assessment 3: Search Engine
// SearchEngine maps each term in a document to the document. It then
// takes a query and, ignoring case and symbols, returns all documents 
// that contains all indexed terms in the query.

import java.util.*;
import java.io.*;
import java.nio.file.*;

public class SearchEngine {
    private Map<String, Set<String>> termWebpage;
    
    // Post: Makes an empty SearchEngine instance.
    public SearchEngine() {
        termWebpage = new HashMap<>();
    }

    // Pre: Takes in a string.
    // Post: Maps each term in a document to the document, ignoring both
    // case and symbols. Also maps each document to a split version of itself.
    // The split version of the document ignores case and symbols. 
    public void index(String document) {
        Set<String> documentStringSet = split(document);
        for (String key : documentStringSet) {
            if (termWebpage.containsKey(key)) {
                termWebpage.get(key).add(document);
            } else {
                Set<String> documentAsString = new HashSet<>();
                documentAsString.add(document);
                termWebpage.put(key, documentAsString);
            }
        }
    }

    // Pre: Takes in a string.
    // Post: Splits the query string and checks to see if terms in the query 
    // are in the index. Ignores any non-indexed terms. Adds documents that
    // contain all indexed terms in the query. If the query is empty, returns 
    // a empty set. 
    public Set<String> search(String query) {
        Set<String> querySplit = split(query);
        Set<String> myResult = new HashSet<String>();
        Boolean first = true;
        for (String s : querySplit) {
            if (termWebpage.containsKey(s)) {
                if (first) {
                    myResult.addAll(termWebpage.get(s));    
                    first = false;
                } else {
                    myResult.retainAll(termWebpage.get(s));
                }            
            }
        }
        return myResult;
    }

    // Return the set of normalized terms split from the given text
    private static Set<String> split(String text) {
        Set<String> result = new HashSet<>();
        for (String term : text.split("\\s+")) {
            term = normalize(term);
            if (!term.isEmpty()) {
                result.add(term);
            }
        }
        return result;
    }

    // Return a standardized lowercase representation of the given string
    private static String normalize(String s) {
        return s.toLowerCase().replaceAll("(^\\p{P}+\\s*)|(\\s*\\p{P}+$)", "");
    }

    public static void main(String[] args) throws IOException {
        SearchEngine engine = new SearchEngine();

        engine.index("File 1 Title apple BALL carrot");
        engine.index("File 2 Title ball !carrot! ,!Dog*&");

        System.out.println(engine.search("qwfga apple title"));

    }
}
