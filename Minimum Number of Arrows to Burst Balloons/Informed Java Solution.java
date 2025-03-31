class Solution {
    public int findMinArrowShots(int[][] points) {
        // This solution was highly informed by other solutions on LeetCode.com
        // Still, I strive to explain the process below

        // First, sort the ballons in ascending order of where their RIGHTMOST end is
        // (i.e. balloons that end farther left (regardless of where they start) come first)
        Arrays.sort(points, (first, second) -> Integer.compare(first[1], second[1]));

        // Pick out the first balloon from our sorted list (we're sure at least one exists)
        // and shoot an arrow at its RIGHTMOST end.
        // We can be sure this bursts all balloons that start (have their leftmost end)
        // leftwards of this position. Reasoning: for all such balloons, their left end
        // is leftwards of the arrow (we just said so), but their right end is rightwards
        // of the arrow (because the array is sorted and we didn't encounter them yet). We
        // also cannot go any farther right without shooting this arrow, else we would fail
        // to burst the first balloon.
        int alreadyBurstAllBallonsUpToPosition = points[0][1];
        int arrowsShot = 1;

        // Then, go through the list
        for (int i = 0; i < points.length; ++i) {
            // If a ballon's left end is left of our last arrow shot,
            // then it's already been burst and we can ignore it.

            // Otherwise...
            if (points[i][0] > alreadyBurstAllBallonsUpToPosition) {
                // We cannot go any farther right without shooting an arrow, else we
                // fail to burst this ballon.
                arrowsShot++;

                // And we can be certain that this also bursts any other balloons whose
                // left end is somewhere between this balloon's right end and the previous
                // arrow position. Reasoning is similar to before: their right end must be
                // farther right beyond this balloon's right end (since we haven't met them
                // yet), while their left end is on the left side of it. So shooting an arrow
                // through this balloon's right end is sure to also burst those
                alreadyBurstAllBallonsUpToPosition = points[i][1];
            }
        }

        return arrowsShot;
    }

    // Time complexity: O(NlogN). Sorting is O(NlogN), then the actual processing is O(N).
    // Memory complexity: O(1). We don't really use any extra memory.
}