// Assessment 5: Language Generator
// Language Generator takes in a starting target and applies production rules
// from one of the three types: English, formula, and music. It will continuously
// apply the production rules until the manipulated target string contains all
// terminals.

import java.util.*;
import java.util.function.*;

public class LanguageGenerator {
    private Map<String, String[]> prodRules;
    private Random r;

    // Pre: Takes in a grammar from the Grammar class.
    // Post: Gets the production rules for a specific type of production
    // and creates a new random object.
    public LanguageGenerator(Grammar grammar) {
        this(grammar, new Random());
    }

    // Pre: Takes in a grammar from the Grammar class and a random object.
    // Post: Gets the production rules for a specific type of production
    // and assigns the random object to the random object passed in.
    public LanguageGenerator(Grammar grammar, Random random) {
        prodRules = grammar.productionRules.get();
        r = random;
    }

    // Post: Returns a string set of all non-terminals.
    public Iterable<String> nonterminals() {
        return prodRules.keySet();
    }

    // Pre: Takes in a target string.
    // Post: If the target is a non-terminal, uses the production rules to
    // randomly change the target to other word(s), character(s), or symbol(s).
    // continues to do so until all targets are terminals. 
    public String generate(String target) {
        if (!prodRules.containsKey(target)) {
            return target;
        } else {
            String result = "";
            String[] rules = prodRules.get(target);
            String[] random = rules[r.nextInt(rules.length)].trim().split("\\s+");
            for (int i = 0; i < random.length; i++) {
                result = result + generate(random[i]) + " ";
            }
            return result.trim();
        }
    }

    public enum Grammar {
        FORMULA(() -> {
            Map<String, String[]> result = new TreeMap<>();
            result.put("E", "T, E OP T".split(", "));
            result.put("T", "x, y, 1, 2, 3, ( E ), F1 ( E ), - T, F2 ( E . E )".split(", "));
            result.put("OP", "+, -, *, %, /".split(", "));
            result.put("F1", "sin, cos, tan, sqrt, abs".split(", "));
            result.put("F2", "max, min, pow".split(", "));
            return result;
        }),
        MUSIC(() -> {
            Map<String, String[]> result = new TreeMap<>();
            result.put("measure", "pitch-w, half half".split(", "));
            result.put("half", "pitch-h, quarter quarter".split(", "));
            result.put("quarter", "pitch-q, pitch pitch".split(", "));
            result.put("pitch", "C, D#, F, F#, G, A#, C6".split(", "));
            result.put("chordmeasure", "chord-w, halfchord halfchord".split(", "));
            result.put("halfchord", "chord-h, chord-q chord-q".split(", "));
            result.put("chord", "Cmin, Cmin7, Fdom7, Gdom7".split(", "));
            result.put("bassdrum", "O..o, O..., O..o, OO..".split(", "));
            result.put("snare", "..S., S..s, .S.S".split(", "));
            result.put("crash", "...*, *...".split(", "));
            result.put("claps", "x..x, xx..x".split(", "));
            return result;
        }),
        ENGLISH(() -> {
            Map<String, String[]> result = new TreeMap<>();
            result.put("SENTENCE", "NOUNP VERBP".split(", "));
            result.put("NOUNP", "DET ADJS NOUN, PROPNOUN".split(", "));
            result.put("PROPNOUN", "Seattle, Matisse, Kim, Zela, Nia, Remi, Alonzo".split(", "));
            result.put("ADJS", "ADJ, ADJ ADJS".split(", "));
            result.put("ADJ", "fluffy, bright, colorful, beautiful, purple, calming".split(", "));
            result.put("DET", "the, a".split(", "));
            result.put("NOUN", "cat, dog, bagel, apple, person, school, car, train".split(", "));
            result.put("VERBP", "TRANSVERB NOUNP, INTRANSVERB".split(", "));
            result.put("TRANSVERB", "ate, followed, drove, smacked, embraced, helped".split(", "));
            result.put("INTRANSVERB", "shined, smiled, laughed, sneezed, snorted".split(", "));
            return result;
        });

        public final Supplier<Map<String, String[]>> productionRules;

        private Grammar(Supplier<Map<String, String[]>> productionRules) {
            this.productionRules = productionRules;
        }
    }

    public static void main(String[] args) {
        LanguageGenerator generator = new LanguageGenerator(Grammar.ENGLISH);
        System.out.println(generator.generate("SENTENCE"));
    }
}
