object Solution {
    def searchInsert(nums: Array[Int], target: Int): Int = {
        binarySearch(nums, 0, nums.length - 1, target)
    }

    def binarySearch(nums: Array[Int], startIndex: Int, endIndex: Int, target: Int): Int = {
        if endIndex < startIndex then startIndex
        else {
            val middleIndex = (startIndex + endIndex) / 2

            if target == nums(middleIndex) then middleIndex
            else if target < nums(middleIndex) then binarySearch(nums, startIndex, middleIndex - 1, target)
            else /* target > nums(middleIndex) */ binarySearch(nums, middleIndex + 1, endIndex, target)
        }
    }

    // Time complexity: O(logN); the array ~halves each time
    // Memory complexity: O(1); I only store middleIndex, and even that just for convenience
}