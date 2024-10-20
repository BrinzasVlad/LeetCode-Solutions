class Solution {
public:
    int candy(vector<int>& ratings) {
        // Algorithm idea:

        // Make n passes through the vector
        // At each pass, if the current element's rating is higher than a neighbour
        // And neighbour has equal or greater candy value
        // Set this element's candy to 1 higher than theirs
        // Stop early if no change was made, also.

        vector<int> candies(ratings.size(), 1); // Everyone starts with 1 candy

        int maximumPasses = ratings.size(); // Worst case is a reverse sorted array
        bool madeChangeThisPass;
        for(int currentPass = 1; currentPass <= maximumPasses; ++currentPass) {
            madeChangeThisPass = false;

            // Handle children in pairs of two, at [index] and [index - 1]
            for(int index = 1; index < ratings.size(); ++index) {
                // If second child is higher-rated than first, second should have more candy
                if(ratings[index] > ratings[index - 1] && candies[index - 1] >= candies[index]) {
                    candies[index] = candies[index - 1] + 1;
                    madeChangeThisPass = true;
                }

                // If first child is higher-rated than second, first should have more candy
                if(ratings[index - 1] > ratings[index] && candies[index] >= candies[index - 1]) {
                    candies[index - 1] = candies[index] + 1;
                    madeChangeThisPass = true;
                }

                // If ratings are equal, candy values can differ freely
            }

            // If no change was made this entire pass, we're done
            if(!madeChangeThisPass) break;
        }

        int totalCandies = 0;
        for(int candyValue : candies) totalCandies += candyValue;

        return totalCandies;

        // Complexity estimates
        // K distinct ratings
        // K <= N
        // Need to do at most K passes over all N elements
        // so worst-case complexity O(N²)
    }
};