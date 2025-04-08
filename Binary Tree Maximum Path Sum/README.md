# [124. Binary Tree Maximum Path Sum](https://leetcode.com/problems/binary-tree-maximum-path-sum/)

A **path** in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge connecting them. A node can only appear in the sequence **at most once**. Note that the path does not need to pass through the root.

The **path sum** of a path is the sum of the node's values in the path.

Given the `root` of a binary tree, return _the maximum **path sum** of any **non-empty** path_.


**Example 1:**
![A graph with root node = 1 with node = 2 as a leaf child on the left and node = 3 as a leaf child on the right](https://assets.leetcode.com/uploads/2020/10/13/exx1.jpg)
> **Input:** root = [1,2,3]  
> **Output:** 6  
> **Explanation:** The optimal path is 2 -> 1 -> 3 with a path sum of 2 + 1 + 3 = 6.

**Example 2:**
![A graph with root node = -10, node = 9 as a leaf child on the left and as the right child a sub-tree with root node = 20, left leaf child = 15, and right leaf child = 7](https://assets.leetcode.com/uploads/2020/10/13/exx2.jpg)
> **Input:** root = [-10,9,20,null,null,15,7]  
> **Output:** 42  
> **Explanation:** The optimal path is 15 -> 20 -> 7 with a path sum of 15 + 20 + 7 = 42.

**Constraints:**
- The number of nodes in the tree is in the range `[1, 3 * 10⁴]`.
- `-1000 <= Node.val <= 1000`