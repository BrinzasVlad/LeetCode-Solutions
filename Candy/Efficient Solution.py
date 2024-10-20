class Solution:
    def candy(self, ratings: List[int]) -> int:
        # Approach:
        # Imagine the ratings list like a histogram, with each child's column
        # as high as their rating.
        #
        # We will go through the list searching for *local maxima*.
        # A child is a local maximum if all of its neighbours are lower-rated.
        # From those local maxima, imagine "flowing" down along the histogram.
        # We "flow" down in both directions (left and right) until we meet either
        # 1. a child with a higher rating than the previous (histogram starts sloping back up)
        # 2. the end of the list (histogram ends)
        #
        # Then, we return backwards:
        # the bottommost-rated child (the local minimum at which we stopped) gets 1 candy,
        # the next one gets 2 candies, the next 3 candies, and so on.
        #
        # Finally, when both paths converge back at the original local maximum,
        # that one gets 1 candy more than the highest of its neighbours.
        #
        # An important consideration are "plateaus". A "plateau" is a sequence of consecutive
        # children with the same rating. "Plateaus" can matter in three circumstances:
        # 1. when searching for a local maximum, the entire "plateau" is the maximum
        # 2. when going back up assigning candy, the first child in a "plateau" below
        #    the maximum gets 1 candy more than the previous... AFTER that, they all get
        #    only 1 candy (because children of equal rating NEED NOT get equal candy)
        # 3. if the local maximum is a "plateau" and the neighbours on the side of the "plateau"
        #    have different numbers of candies, then ONE child in the "plateau" on the higher-candies
        #    side gets that many plus 1, ONE MORE child in the "plateau" on the lower-candies side
        #    gets that lower number plus 1... and EVERYONE ELSE in the maximum plateau only gets 1
        #
        # i.e. if the ratings are  [0, |1, 1|, 3, |4, 4, 4, 4|, 2]
        # then the candies will be [1, |2, 2|, 3, |4, 2, 2, 2|, 1]
        # ("plateau" regions marked with ||)
        #
        # Notably, as long as we're careful about double-counting, we don't even need to consider
        # storing the candy values - we can simply add them to a running total and report that one.
        # Addendum to this "notably": we should also take great care when "plateaus" happen in the
        # region at risk for double-counting!

        LAST_INDEX = len(ratings) - 1

        def calculateCandiesForLocalMaximum(ratings, maximumStartIndex, maximumEndIndex) -> int:
            # Receives coordinates of a local maximum starting at maximumStartIndex and
            # ending at maximumEndIndex (if equal, it's a single element, if not, it's a
            # "plateau"), and calculates the total amount of candies for that maximum and
            # all children "downstream" of it.
            # To avoid double-counting, only counts the left-side final element if it is
            # the very first element of ratings.

            candiesForThisRun = 0

            # Flow down leftwards until we find a local minimum or the end of the list
            leftwardsIndex = maximumStartIndex
            while 0 != leftwardsIndex and ratings[leftwardsIndex - 1] <= ratings[leftwardsIndex]:
                leftwardsIndex -= 1
            
            # Flow down rightwards until we find a local minimum or the end of the list
            rightwardsIndex = maximumEndIndex
            while LAST_INDEX != rightwardsIndex and ratings[rightwardsIndex + 1] <= ratings[rightwardsIndex]:
                rightwardsIndex += 1

            # To avoid double-counting, we want to not count the left-most
            # minimum (whether that be a "plateau" or a single child), because
            # it has already been counted by the previous maximum
            #
            # Exception: it is possible this minimum has NOT been counted yet,
            # if it starts at the very start of the list of ratings (because
            # there was no maximum before it); if so, we MUST count it
            # Note that this also neatly covers the case in which
            # leftwardsIndex = maximumStartIndex, since that only happens
            # at the very start of the list.
            #
            # We will take the approach of compensating for the leftmost
            # entries by subtracting them, since it's likely they will be few
            if 0 != leftwardsIndex:
                # Values have already been counted, must subtract them
                # to compensate for double-counting them later
                compensatingIndex = leftwardsIndex

                # Un-count the first child
                candiesForThisRun -= 1
                while ratings[compensatingIndex + 1] == ratings[compensatingIndex]:
                    # Check for "plateau"
                    candiesForThisRun -= 1
                    compensatingIndex += 1

            # Climb back upwards on the left and count candies
            leftwardsCurrentCandies = 1
            while leftwardsIndex < maximumStartIndex:
                candiesForThisRun += leftwardsCurrentCandies
                # If next child up is higher-rated, they must get more candies
                if ratings[leftwardsIndex + 1] > ratings[leftwardsIndex]:
                    leftwardsCurrentCandies += 1
                else:
                    # We are in a below-maximum plateau; candy count resets to 1
                    leftwardsCurrentCandies = 1
                leftwardsIndex += 1
            
            # Climb back upwards on the right and count candies
            rightwardsCurrentCandies = 1
            while rightwardsIndex > maximumEndIndex:
                candiesForThisRun += rightwardsCurrentCandies
                # If next child up is higher-rated, they must get more candies
                if ratings[rightwardsIndex - 1] > ratings[rightwardsIndex]:
                    rightwardsCurrentCandies += 1
                else:
                    # We are in a below-maximum plateau; candy count resets to 1
                    rightwardsCurrentCandies = 1
                rightwardsIndex -= 1
            
            # Calculate candies for maximum
            localMaximumPlateauLength = maximumEndIndex - maximumStartIndex + 1
            # If there is at least one child (always true), they will get 1 candy more
            # than the highest-candy neighbour of the "plateau"
            candiesForThisRun += max(leftwardsCurrentCandies, rightwardsCurrentCandies)
            # If there is at least one more child (two+ total), the second child will
            # be on the opposite end of the "plateau" from the first, and will get 1
            # candy more than the lowest-candy neighbour of the "plateau"
            if localMaximumPlateauLength >= 2:
                candiesForThisRun += min(leftwardsCurrentCandies, rightwardsCurrentCandies)
            # If there are any more children in the "plateau", all of them will only
            # receive 1 candy each
            if localMaximumPlateauLength >= 3:
                candiesForThisRun += localMaximumPlateauLength - 2

            return candiesForThisRun

        totalCandies = 0
        # Step 1: find a local maximum or local maximum "plateau"
        for index in range(len(ratings)):
            # Leftwards
            if 0 == index or ratings[index] > ratings[index - 1]:
                # Higher-rated than previous child, might be the start of a maximum
                consideringLocalMaximum = True
                localMaximumLeftwardsIndex = index
            
            # Rightwards
            if LAST_INDEX == index or ratings[index] > ratings[index + 1]:
                # Higher-rated than next child, might be the end of a maximum
                if consideringLocalMaximum:
                    # Found a local maximum
                    localMaximumRightwardsIndex = index
                    totalCandies += calculateCandiesForLocalMaximum(ratings,
                                                                    localMaximumLeftwardsIndex,
                                                                    localMaximumRightwardsIndex)
                    consideringLocalMaximum = False

            # No need to clear consideringLocalMaximum flag while going "upwards",
            # since localMaximumLeftwardsIndex will get updated as long as we keep climbing
            # and once we actually start going down again, we'll first process the maximum
            # then clear the flag
        
        return totalCandies

        # Complexity: O(N) time (about 3 passes over each element), O(1) memory