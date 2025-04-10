public class Solution {
    public bool CanPartition(int[] nums) {
        // Absolute brute-force approach:
        // - compute total sum of the array; if it's odd, return false immediately
        // - if it's even, compute half of that sum; this is the sum of our partition
        // - then, sort the array from high to low
        // - starting from the high end, try combinations until we get one that matches
        // - ^ this last step is exponential, and that's really bad
        //
        // Alternate approach: dynamic programming.
        // - Core idea: keep track of ALL sums that we can reach (initially just '0')
        //   and with each new number encountered update the relevant sums

        // Compute one-half the sum of the array; this is the value we need to sum up to
        int targetSum = 0;
        for (int i = 0; i < nums.Length; ++i) targetSum += nums[i];
        if (1 == targetSum % 2) return false; // If total array sum is odd, we can't split it in two
        targetSum /= 2;

        // Check for any elements greater than one-half the total sum; if any exist, split is impossible
        for (int i = 0; i < nums.Length; ++i) if (nums[i] > targetSum) return false;

        // Set up the reachable-sums array - from 0 up to the half-sum, inclusively
        bool[] reachableSums = new bool[targetSum + 1]; // All false by default
        reachableSums[0] = true;

        // For each element in nums, update the sums
        int maxSumSoFar = 0;
        for (int i = 0; i < nums.Length; ++i) {
            // If we can reach target sum by adding this number, immediately return
            if (reachableSums[targetSum - nums[i]]) return true;

            // Don't update beyond the maximum sum so far; there's no point
            maxSumSoFar += nums[i];
            int upperLimit = Math.Min(maxSumSoFar, targetSum);

            // Update reachable sums
            for (int j = upperLimit; j >= nums[i]; --j) {
                reachableSums[j] = reachableSums[j] || reachableSums[j - nums[i]];
            }
        }

        // If no combination so far got to the target, it is unreachable
        return false;
    }

    // Time complexity: O(N * Sum), where Sum is the sum of all elements in the array (possibly
    //                  as high as N * MaxValue, but often lower), because we must process each
    //                  element once, and processing each element potentially requires updating
    //                  most of the reachable sums stored (or which there are Sum/2 in total).
    // Memory complexity: O(Sum), where Sum is the sum of all elements in the array (see above,
    //                    but at most on the order of N * MaxValue), because we need to keep
    //                    track of which sums are reachable with the numbers we have processed
    //                    so far.
    // Possible improvements: reducing memory any further would require a different approach most
    //                        likely. I don't really see any further improvements right away.
}