// Assessment 1: Autocomplete
// Compares instances of term class by lexicographic order then by weight.

public class Term implements Comparable<Term>{
    // The query of the term.
    private String query;
    // The weight assigned to the term.
    private int weight;

    // Pre: Takes in a non-null query and weight.
    // Post: Constructs a term using a non-null query and weight.
    public Term(String query, int weight) {
        this.query = query;
        this.weight = weight;
    }

    // Pre: Takes in terms.
    // Post: Compares two instances of term to each other lexicographically
    // and ignores case.
    public int compareTo(Term other) {
        int cmp = this.query.compareToIgnoreCase(other.query);  
        return cmp;
    }

    // Pre: Takes in terms.
    // Post: Compares to instances of term to each other by weight in descending order.
    int compareToByReverseWeight(Term other) {
        int cmp = -Integer.compare(this.weight, other.weight);
        return cmp;
    }

    // Pre: None.    
    // Post: Returns the query of a term.
    public String query() {
        return query;
    }

    // Pre: None.
    // Post: Returns the weight of a term.
    public int weight() {
        return weight;
    }

    // Pre: None.
    // Post: Returns the query of a term.
    public String toString() {
        return query;
    }

    public static void main(String[] args) {
        Term t1 = new Term("hello world", 0);
        Term t2 = new Term("hi", 1);
        int cmp = t1.compareTo(t2);
        System.out.println(cmp);
    }
}
