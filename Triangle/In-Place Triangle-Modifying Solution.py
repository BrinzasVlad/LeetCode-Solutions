class Solution:
    def minimumTotal(self, triangle: List[List[int]]) -> int:
        # Approach:
        # Going from bottom to top, compute the minTotal for each element as
        # triangle[row][elementIndex] + min(triangle[row + 1][elementIndex], triangle[row + 1][elementIndex + 1])
        #
        # If modifying the triangle is allowed, this can be done in-place
        # If not, it can be done by storing two lists:
        # - one for the minTotal values of elements in row [row + 1] (fixed)
        # - one for the minTotal values of elements in row [row] (currently being computed)
        if len(triangle) == 1:
            return triangle[0][0] # return the one single value
        
        for row in range(len(triangle) - 2, -1, -1):
            # Go through each row from the second-to-last one up through the first
            # range from len(triangle) - 2: second-to-last row
            # range to -1: range stop is NOT included, so to cover row 0 we stop at -1
            # range step -1: go down 1 by 1
            for index in range(len(triangle[row])):
                triangle[row][index] += min(triangle[row + 1][index], triangle[row + 1][index + 1])
        
        return triangle[0][0]
    
    # Time complexity: O(N): each element aside from those on the last line must be processed once
    # Memory complexity: O(1): process happens in-place. See notes for an O(√N) (memory for two
    #                          triangle rows) approach.
    # Possible improvements: in-place editing might be disallowed if the triangle structure is still needed.