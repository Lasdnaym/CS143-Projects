// Assessment 6: Election Simulator
// Election simulator determines the least amount of popular votes needed to
// obtain the least number of electoral votes needed to win. It returns a set of
// states that satisfy this condition.

import java.util.*;
import java.io.*;

public class ElectionSimulator {
    private Map<Arguments, Set<State>> combinations;
    private List<State> stateList;

    // Post: Constructs a new instance of ElectionSimulator for a given list of
    // states.
    public ElectionSimulator(List<State> states) {
        combinations = new HashMap<Arguments, Set<State>>();
        stateList = states;
    }

    // Post: If the set of states is empty, returns the empty set. Else, it will
    // return the set of states which garuntees the minimum amount of popular and 
    // electoral votes.
    public Set<State> simulate() {
        if (stateList.isEmpty()) {
            return new HashSet<State>();
        } else {
            return simulate(minElectoralVotes(stateList), 0);
        }
    }

    // Pre: Takes in the current number of electoralVotes and an index keeping track
    // of the current position on the set of states.
    // Post: Recursively calls itself to compute the set of states which gives the
    // minimum amount of popular and electoral votes needed to win a election, returns
    // the optimal set.
    private Set<State> simulate(int electoralVotes, int index) {
        Arguments mapArgument = new Arguments(electoralVotes, index);
        Set<State> stateSet = new HashSet<State>();
        if (combinations.containsKey(mapArgument)) {
            return combinations.get(mapArgument);
        } else if (electoralVotes <= 0) {
            return new HashSet<State>();
        } else if (index >= stateList.size()) {
            return null;
        } else {
            Set<State> with = simulate(electoralVotes - stateList.get(index).electoralVotes, index + 1);
            if (with == null) {
                return null; 
            }
            Set<State> without = simulate(electoralVotes, index + 1);
            stateSet.addAll(with);
            stateSet.add(stateList.get(index));
            int testOne = minPopularVotes(stateSet);
            if (without == null) {
                return stateSet; 
            }
            int testTwo = minPopularVotes(without);
            if (testTwo < testOne) {
                stateSet = without;
            }
            combinations.put(mapArgument, stateSet);
        }
        return stateSet;      
    }

    public static int minElectoralVotes(List<State> states) {
        int total = 0;
        for (State state : states) {
            total += state.electoralVotes;
        }
        return total / 2 + 1;
    }

    public static int minPopularVotes(Set<State> states) {
        int total = 0;
        for (State state : states) {
            total += state.popularVotes / 2 + 1;
        }
        return total;
    }

    private static class Arguments implements Comparable<Arguments> {
        public final int electoralVotes;
        public final int index;

        public Arguments(int electoralVotes, int index) {
            this.electoralVotes = electoralVotes;
            this.index = index;
        }

        public int compareTo(Arguments other) {
            int cmp = Integer.compare(this.electoralVotes, other.electoralVotes);
            if (cmp == 0) {
                cmp = Integer.compare(this.index, other.index);
            }
            return cmp;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (!(o instanceof Arguments)) {
                return false;
            }
            Arguments other = (Arguments) o;
            return this.electoralVotes == other.electoralVotes && this.index == other.index;
        }

        public int hashCode() {
            return Objects.hash(electoralVotes, index);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<State> states = new ArrayList<>(51);
        try (Scanner input = new Scanner(new File("data/1828.csv"))) {
            while (input.hasNextLine()) {
                states.add(State.fromCsv(input.nextLine()));
            }
        }
        Set<State> result = new ElectionSimulator(states).simulate();
        System.out.println(result);
        System.out.println(minPopularVotes(result) + " votes");
    }
}
