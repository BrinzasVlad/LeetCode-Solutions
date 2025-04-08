/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public int maxPathSum(TreeNode root) {
        // We are guaranteed at least one node, so no null-check needed
        // Return maximum path in the tree, with or without the root node
        return maxPathWithWithoutRoot(root)[2];
    }

    /**
     * Returns three integers:
     * 1. the maximum path sum in this tree *that includes the root node*
     * 2. the maximum path sum in this tree that includes the root node and *at most one child* (for chaining)
     * 3. the maximum path sum in this tree even without the root node
     */
    int[] maxPathWithWithoutRoot(TreeNode root) {
        // Get left and right maximum paths
        // Make defaults really negative, so as to never prefer a 0-node solution to a 1-bad-node one
        // (Bit of a hack, to be fair)
        int[] leftMax = (root.left == null) ? new int[]{-1001, -1001, -1001} : maxPathWithWithoutRoot(root.left);
        int[] rightMax = (root.right == null) ? new int[]{-1001, -1001, -1001} : maxPathWithWithoutRoot(root.right);

        // Maximum path sum in this tree that includes the root node is
        // (optionally) left-maximum-with-root-and-1-child + root + (optionally) right-maximum-with-root-and-1-child
        int maxPathSumWithRoot = ((leftMax[1] > 0) ? leftMax[1] : 0)
                               + root.val
                               + ((rightMax[1] > 0) ? rightMax[1] : 0);

        // Maximum path sum with root and just one child is
        // root + max(left-with-1-child, right-with-1-child, nothing-at-all)
        int maxPathSumWithRootAndOneChild = root.val
                                          + Math.max(Math.max(leftMax[1], rightMax[1]), 0);
        
        // Maximum path sum in this tree with or without the root node is greater of:
        // - maximum path sum with-or-without-root on the left
        // - maximum path sum with-or-without-root on the right
        // - maximum path sum that includes this root node (computed earlier)
        int maxPathSumWithoutRoot = Math.max(leftMax[2],
                                    Math.max(maxPathSumWithRoot,
                                             rightMax[2]));
        
        return new int[]{maxPathSumWithRoot, maxPathSumWithRootAndOneChild, maxPathSumWithoutRoot};
    }

    // Time Complexity: O(N). Each node must be visited by maxPathWithWithoutRoot exactly once.
    // Memory Complexity: O(1) plus the stack (~ O(logN) deep if tree approximately balanced). We
    //                    compute and store 2 x 3 integers on each maxPathWithWithoutRoot call,
    //                    so at maximum stack depth we might have some ~ O(logN) triplets stored, to a
    //                    theoretical worst of O(N) if the entire tree is on the right-hand side.
    // Possible improvements: avoiding recursion seems complicated, and even then I do not think the
    //                        memory can be improved much without a significant approach change. There
    //                        might be some redundant calculation when computing the maximum sum path
    //                        with both children allowed vs. with only one child allowed. Finally,
    //                        returning max-path-with-both-children-allowed is irrelevant on all returns...
    //                        ...except for the one at the very start, which is however important. This
    //                        could probably be optimized away by treating the first node separately, 
    //                        though that would require some additional null checks, and we anyway need to
    //                        *compute* the value (even if not returning it) for max-path-without-root.
}