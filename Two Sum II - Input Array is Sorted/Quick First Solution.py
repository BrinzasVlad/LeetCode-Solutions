class Solution:
    def twoSum(self, numbers: List[int], target: int) -> List[int]:
        # Brute-force solution: for each number in the array, traverse the rest
        # of the array until we either find a different number that sums up
        # correctly, we run out of numbers, or we get to a sum higher than
        # the target sum
        #
        # Slight optimization: skip duplicates

        previousFirst = "null" 
        for indexOne in range(len(numbers) - 1):
            # Crude implementation of duplicate skipping
            if numbers[indexOne] == previousFirst:
                continue
            else:
                previousFirst = numbers[indexOne]
            
            previousSecond = "null"
            for indexTwo in range(indexOne + 1, len(numbers)):
                # Crude implementation of duplicate skipping
                if numbers[indexTwo] == previousSecond:
                    continue
                else:
                    previousSecond = numbers[indexTwo]
                
                if numbers[indexOne] + numbers[indexTwo] > target:
                    break
                elif numbers[indexOne] + numbers[indexTwo] == target:
                    return [indexOne + 1, indexTwo + 1]
        
        return [-1, -1] # Should never happen, if tests always have a solution
        
        # Worst-case O(N²) time complexity (so pretty slow), O(1) memory usage
        # Passed all tests on LeetCode after optimizing to skip duplicates,
        # but it's still arguably quite ugly, and could be optimized with
        # something like a binary search for the second number perhaps, to take
        # advantage of the numbers array being already sorted