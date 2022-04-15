// Assessment 2: Letter Inventory
// The LetterInventory class is used to keep track of the letters of the
// alphabet in a given input. Letters can be added or removed.

public class LetterInventory {
    private int[] elementData;
    private int size;
    public final int defaultCapacity = 26;

    // Pre: Takes in a string. 
    // Post: Constructs an LetterInventory of a given string ignoring case
    // and symbols that are not letters. The LetterInventory records the
    // number of times each letter appears in the string.
    public LetterInventory(String data) {
        size = data.length();
        elementData = new int[defaultCapacity];
        for (int i = 0; i < data.length(); i++) {
            char character = Character.toLowerCase(data.charAt(i));
            if (Character.isLetter(character)) {
                elementData[character - 'a']++;
            } else {
                size--;
            }
        }
    }
    
    // Pre: Takes in a letter. If it is not an alphabet or the value
    // is less than 0, throws IllegalArgumentException.
    // Post: Returns the count of how many of a certain letter are in the
    // inventory. Ignores case.
    public int get(char letter) {
        if (!Character.isLetter(letter)) {
            throw new IllegalArgumentException();
        }
        return elementData[Character.toLowerCase(letter) - 'a'];
    }

    // Pre: Takes in a letter and value. If it is not an alphabet 
    // or the value is less than 0, throws IllegalArgumentException. 
    // Post: Sets the count for a letter to a given value. Ignores case.
    public void set(char letter, int value) {
        if (!Character.isLetter(letter) || value < 0) {
            throw new IllegalArgumentException();
        }
        int indexSet = Character.toLowerCase(letter) - 'a';
		size += value - elementData[indexSet];
		elementData[indexSet] = value;
    }

    // Pre: None.
    // Post: Returns the sum of all counts.
    public int size() {
        return size;
    }

    // Pre: Inventory must be empty.
    // Post: Returns true.
    boolean isEmpty() {
        return size == 0;
    }

    // Pre: None.
    // Post: Prints out a string with brackets of the inventory of letters
    // in all lower case and sorted order. 
    public String toString() {
        String finalResult = "[";
        for (int i = 0; i < defaultCapacity; i++) {
            for (int n = 0; n < elementData[i]; n++) {
                finalResult += (char)('a' + i);
            }
        }
        return finalResult + "]";
    }

    // Pre: Takes an other LetterInventory.
    // Post: Constructs and returns a new LetterInventory that is the
    // sum of this LetterInventory and other LetterInventory.
    // Does not alter this nor other LetterInventory.
    public LetterInventory add(LetterInventory other) {
        LetterInventory result = new LetterInventory("");
        for (int i = 0; i < defaultCapacity; i++) {
            result.elementData[i] = this.elementData[i] + other.elementData[i];
        }
        result.size = this.size + other.size;
        return result;       
    }

    // Pre: Takes an other LetterInventory.
    // Post: Constructs and returns a new LetterInventory that is the
    // difference between this LetterInventory and other LetterInventory.
    // Does not alter this nor other LetterInventory. If any subtraction
    // results in a negative value, returns null.
    public LetterInventory subtract(LetterInventory other) {
        LetterInventory result = new LetterInventory("");
        for (int i = 0; i < defaultCapacity; i++) {
            result.elementData[i] = this.elementData[i] - other.elementData[i];
            if (result.elementData[i] < 0){
                return null;
            }
            result.size += result.elementData[i];
        }
        return result;
    }

    public static void main(String[] args) {
        LetterInventory inventory = new LetterInventory("washington state");
        System.out.println(inventory.get('a'));
    }

    // Returns true if the other object stores the same character counts as this inventory.
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof LetterInventory)) {
            return false;
        }
        LetterInventory other = (LetterInventory) o;
        LetterInventory diff = this.subtract(other);
        return diff != null && diff.isEmpty();
    }

    // Returns a hash code value for this letter inventory.
    public int hashCode() {
        int result = 0;
        for (char c = 'a'; c <= 'z'; c++) {
            result += c * get(c);
        }
        return result;
    }

    // Returns the cosine similarity between this inventory and the other inventory.
    public double similarity(LetterInventory other) {
        long product = 0;
        double thisNorm = 0;
        double otherNorm = 0;
        for (char c = 'a'; c <= 'z'; c++) {
            int a = this.get(c);
            int b = other.get(c);
            product += a * (long) b;
            thisNorm += a * a;
            otherNorm += b * b;
        }
        if (thisNorm <= 0 || otherNorm <= 0) {
            return 0;
        }
        return product / (Math.sqrt(thisNorm) * Math.sqrt(otherNorm));
    }
}
