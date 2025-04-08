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
    public void flatten(TreeNode root) {
        // In-place, so O(1) memory
        // If tree is entirely empty (root = null), skip entirely
        if (null != root) flattenThisNode(root, null);
    }

    public void flattenThisNode(TreeNode thisNode, TreeNode attachThisToEnd) {
        // First, flatten the right-side (and attach to its end)
        if (null != thisNode.right) flattenThisNode(thisNode.right, attachThisToEnd);
        else thisNode.right = attachThisToEnd;

        // Then, flatten the left-side, and attach the right-side to it
        if (null != thisNode.left) flattenThisNode(thisNode.left, thisNode.right);
        else thisNode.left = thisNode.right; // a bit redundant, but it's easy to do

        // Finally, put the descendants on the right side
        thisNode.right = thisNode.left;
        thisNode.left = null;
    }

    // Time complexity: O(N): each node must be traversed once by flattenThisNode
    // Memory complexity: O(1): flattening is done in-place, no extra memory needed
    // Possible improvements: having to test for nullness *before* calling flattenThisNode
    //                        is a bit awkward, but we can't just skip cases when thisNode
    //                        is null since we'd lose attachThisToEnd. (Essentially, what
    //                        we'd need is that if thisNode is null, we assign attachThisToEnd
    //                        to it. Which would work smoothly if the function returned a node
    //                        instead of modifying it in-place, but I wanted to be sure-sure that
    //                        no extra memory is needed, hence I did it like this.)
}