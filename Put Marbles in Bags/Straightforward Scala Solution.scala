object Solution {
    def putMarbles(weights: Array[Int], k: Int): Long = {
        // Approach:
        // The problem can be better thought of as deciding where to place
        // *delimiters* between bags. If each delimiter's "value" is considered
        // to be the sum of the weights to its left and right (so e.g. a delimiter
        // between indices 0 and 1 in [1, 3, 5, 1] has "value" 1 + 3 = 4), then the
        // score of a given distribution will be the sum of its delimiters' values,
        // plus the weights of the very first and very last marbles.
        // Since we only care about the *difference* between the maximum and minimum
        // scores, we can ignore these first and last marbles (they're always the same).
        // Therefore, the problem becomes one of maximising / minimising delimiter value.
        // Proposed approach:
        // - (optional) if k == weights.length, return 0 right away (there's only one
        //   possible distribution) -> O(1)
        // - convert the original array into a 1-size-smaller array representing the "value"
        //   a delimiter would have if placed after index i. (e.g. [1, 3, 5, 1] becomes
        //   [4, 8, 6]) -> O(N)
        // - sort this 1-size-smaller array -> O(NlogN)
        // - get the sum of the first k-1 entries as the minimum score, and the sum of the
        //   last k-1 entries as the maximum score -> O(K) <= O(N)
        // - return the difference between the minimum and maximum scores -> O(1)

        if k == weights.length then return 0

        // Cast to Long to avoid overflow issues
        val delimiterValues = weights.sliding(2).map(pair => pair(0).toLong + pair(1).toLong).toArray
        delimiterValues.sortInPlace()

        val maximalSum = delimiterValues.takeRight(k - 1).sum
        val minimalSum = delimiterValues.take(k - 1).sum
        
        return maximalSum - minimalSum
    }

    // Time complexity: O(NlogN) - sorting the delimiter values array is what takes the most time
    // Memory complexity: O(N) - we store delimiterValues, which has length N-1
    // Possible improvements: at the expense of making the code less brief/pretty/readable, we
    //                        could eschew sorting delimiterValues, and instead just extract the
    //                        K - 1 smallest / largest elements from it. (E.g. by iterating through
    //                        it while keeping two PriorityQueue-like constructions for min and max.)
    //                        If K is usually significantly smaller than N, this would reduce the
    //                        "sorting" step to O(KlogK) or so, which would be better.
}