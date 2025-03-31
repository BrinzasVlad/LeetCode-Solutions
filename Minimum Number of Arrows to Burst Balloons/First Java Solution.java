class Solution {
    public int findMinArrowShots(int[][] points) {
        // Step 1: filter out any balloons that completely overlap some smaller balloon
        // (The bigger one would surely be hit if the small one is hit.)

        // Use a priority queue as a sorted list
        PriorityQueue<Pair<Integer, Integer>> nonRedundantBalloons
            = new PriorityQueue<Pair<Integer, Integer>>(
                11, // Default queue size
                (first, second) -> Integer.compare(first.getKey(), second.getKey())
            );

        for (int i = 0; i < points.length; ++i) {
            boolean isBalloonCompletelyOverlappingSmallerBalloon = false;

            for (int j = 0; j < points.length; ++j) {
                if (i == j) continue; // Do not self-compare

                if (points[i][0] == points[j][0]
                 && points[i][1] == points[j][1]) {
                    // For exact overlap, use i < j to allow one element to pass
                    // Last identical element passes with this approach
                    if (i < j) {
                        isBalloonCompletelyOverlappingSmallerBalloon = true;
                        break;
                    }
                }
                else if (points[i][0] <= points[j][0] && points[j][1] <= points[i][1]) {
                    isBalloonCompletelyOverlappingSmallerBalloon = true;
                    break;
                }
            }

            if (!isBalloonCompletelyOverlappingSmallerBalloon) {
                // Balloon is not redundant, add to sorted list
                nonRedundantBalloons.add(new Pair<>(points[i][0], points[i][1]));
            }
        }

        // Step 2: go left-to-right, shooting arrows as needed
        // Take the leftmost balloon, shoot an arrow through it rightmost end,
        // and remove it and any other balloons this bursts. Repeat until no
        // balloons are left.

        // There is always at least one balloon, so start from the first one
        // Shoot through its rightmost end; this will pop it plus any other balloons
        // that start before its rightmost end.
        // (We know this is true because any balloon that starts after this one
        // must also end after this one (and therefore overlap with its rightmost end),
        // else one of the two would have been removed in step 1.)
        int shotAllBalloonsStartingUpTo = nonRedundantBalloons.poll().getValue();
        int totalArrowsShot = 1;

        while (!nonRedundantBalloons.isEmpty()) {
            Pair<Integer, Integer> currentBalloon = nonRedundantBalloons.poll();
            
            if (currentBalloon.getKey() <= shotAllBalloonsStartingUpTo) continue;
            // Balloon was burst by the latest arrow we shot, move on

            else {
                // Balloon wasn't burst by latest arrow, repeat process by shooting
                // rightmost end of this balloon
                shotAllBalloonsStartingUpTo = currentBalloon.getValue();
                totalArrowsShot++;
            }
        }

        return totalArrowsShot;
    }

    // Time complexity: O(N²). Step 1 is O(N²) because I compare every balloon to every other
    //                  balloon to see if it completely covers that other balloon. I am not
    //                  sure how to avoid this - removing redundant ballons is critical to my
    //                  proposed solution. Creating the sorted list (queue, here) is ~O(NlogN),
    //                  since each insertion is NlogN. Notably, since some balloons get filtered
    //                  out, this is probably a smaller N. Step 2 is then in the range of N times
    //                  polling the queue, so O(N)-O(NlogN), keeping in mind that the queue shrinks
    //                  as we poll (making polling faster).
    // Memory complexity: O(N). I make a (filtered) copy of the array for ease of work. It's might
    //                    be possible to modify the array in-place if memory is a limiting factor
    //                    (e.g. shifting the array to remove redundant balloons in-place, then
    //                    sorting it in-place at the end), but this would negatively affect time
    //                    performance.
    // Possible improvements: if a better idea than my "remove redundant ballons first" is found,
    //                        or if a cleverer way to filter out redundancies is found, it could
    //                        improve the solution. Notably, the heavy step is the filtering (O(N²)),
    //                        and that can benefit strongly from parallelization.
    //
    // Full thought process below:
    //
    // How can we think about this?
    // We always want to shoot at intersections rather than lone balloons
    // So if a balloon has an intersection, we'll prefer that over non-intersect
    // It's not always true that we start from area with most overlapping balloons
    //
    // We can safely ignore any balloon that fully overlaps a smaller balloon.
    // (Since we need to pop the small one, and that'll hit the big one too.)
    //
    // Once we've done this reduction, we can be sure (can we? this is important!)
    // that at least one (actually two, one at each end) balloon has exactly just one
    // overlap. We will always shoot an arrow at that overlap (must pop first balloon),
    // so we can eliminate the first and the second balloons that way.
    // Then, the process repeats.
    // Total arrow yield will be K/2 arrows for each contiguous group of K balloons,
    // rounded up.
    //
    // How do we do the ignoring.
    // Naive approach: for every balloon, check every other balloon for overlap.
    // Works, but is O(N²).
    // Fancier approach: sort the balloons somehow. Question: how? ...Maybe by start
    // coordinate? Maybe by size?
    // Sorting them by size allows you to only need to check one of "this balloon is
    // invalid because it fully overlaps a smaller balloon" and "this balloon invalidates
    // a previous balloon because it is fully overlapped by it".
    // But sorting them by start or end coordinate is what we'll need afterwards, when
    // processing the balloons.
    //
    // EDIT: it's not even true that you get to "one balloon has at most 1 overlap" -
    // consider test case 6, where 4 long baloons overlap each other in steps. But it
    // should remain true that shooting the rightmost end (assuming we go left-to-right)
    // of that balloon will hit the maximum number of other overlapping balloons.
    //
    // Okay, best plan so far:
    // 1. remove from the list any balloon that completely overlaps some other, smaller
    // balloon; we'll always hit the bigger one when hitting the smaller one. This sounds
    // like O(N²); we *might* be able to cut it to O(NlogN) via clever tricks?
    // 2. going from left to right (might really want to sort balloons first, so O(NlogN)),
    // find the first balloon and shoot an arrow through its rightmost end. Remove all
    // balloons this hits.
}