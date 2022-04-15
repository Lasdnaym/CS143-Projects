// Assessment 4: DNA Strand
// DNA Strand takes in two strings, a string of DNA and a substrand.
// It then determines the most times the substrand consecutively repeats 
// in the DNA strand. 

public class DNAStrand {
    private Nucleotide front;

    // Pre: Takes in a string of DNA.
    // Post: Loops through the entire string and adds each letter as a 
    // node in a LinkedList. 
    public DNAStrand(String dna) {
        for (int i = 0; i < dna.length(); i++){
            Nucleotide test = new Nucleotide(dna.charAt(i));
            if (front == null){
                front = test; 
            } else {
                Nucleotide current = front;
                while(current.next != null){
                    current = current.next;
                }
                current.next = test;
            }
        }       
    }

    // Pre: Takes in a substrand.
    // Post: If substrand is null, throws IllegalArgumentException. Loops through
    // the DNA strand. Returns the maximum amount of consecutive substrand repeats.
    public int maxConsecutiveRepeats(DNAStrand substrand) {
        int max = 0;
        if (substrand == null) {
            throw new IllegalArgumentException();
        }
        Nucleotide currentDna = front;
        while (currentDna != null) {
            int count = 0;
            count = consecutiveRepeats(substrand, currentDna);
            if (count > max) {
                max = count;
                count = 0;
            }
            currentDna = currentDna.next;
        }        
        return max;
    }

    // Pre: Takes in a LinkedList substrand and currentDna node.
    // Post: Checks to see if the substrand is in the strand of DNA. Continues to check 
    // until there is no next nucleotide in the strand. Counts the amount of consecutive 
    // repeats each time the substrand shows up and returns it.
    public int consecutiveRepeats(DNAStrand substrand, Nucleotide currentDna) {
        Nucleotide subCurrent = substrand.front;
        int countTemp = 0;
        while (currentDna != null && currentDna.data == subCurrent.data) {
            currentDna = currentDna.next;
            if (subCurrent.next != null) {
                subCurrent = subCurrent.next;
            } else if (subCurrent.next == null) {
                subCurrent = substrand.front;
                countTemp++;
            } 
        }
        return countTemp;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Nucleotide current = front; current != null; current = current.next) {
            result.append(current.data);
        }
        return result.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof DNAStrand)) {
            return false;
        }
        return this.toString().equals(o.toString());
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public static void main(String[] args) {
        DNAStrand dna = new DNAStrand("CAACACAAAA");
        DNAStrand str = new DNAStrand("A");
        System.out.println(dna.maxConsecutiveRepeats(str));
    }

    private static class Nucleotide {
        public char data;
        public Nucleotide next;

        public Nucleotide(char data) {
            this.data = data;
            this.next = null;
        }
    }
}
