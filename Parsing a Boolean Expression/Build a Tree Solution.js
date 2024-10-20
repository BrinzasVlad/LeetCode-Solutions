/**
 * @param {string} expression
 * @return {boolean}
 */
var parseBoolExpr = function(expression) {
    // We will build a tree out of the string expression, then parse that tree
    class BoolExprNode {
        constructor(parent, type) {
            this.parent = parent;
            this.type = type;
            this.children = [];
        }
        addChild(child) {
            this.children.push(child)
        }
    }

    const rootNode = new BoolExprNode(null, '&'); // Any neutral type with children works
    let currentParentNode = rootNode;
    for(let indexInExpression = 0; indexInExpression < expression.length; ++indexInExpression) {
        const currentExprType = expression[indexInExpression];

        switch (currentExprType) {
            case ',':
                // We're done with the current child, nothing to do
                // TODO: nothing to do?
                break;
            case ')':
                // We're done with the current parent
                currentParentNode = currentParentNode.parent;
                break;
            case 't':
            case 'f':
                // Make a basic child node
                currentParentNode.addChild(new BoolExprNode(currentParentNode, currentExprType));
                break;
            case '!':
            case '&':
            case '|':
                // Make a child node
                const currentNode = new BoolExprNode(currentParentNode, currentExprType);
                currentParentNode.addChild(currentNode);
                // Skip one extra character for the opening parenthesis '('
                // We could also just let it go through the switch, match nothing, and continue
                indexInExpression++;
                // Set current node as parent for upcoming nodes
                currentParentNode = currentNode;
                break;
        }
    }

    // Parsing the tree
    const parseBoolTree = function(boolExprNode) {
        switch (boolExprNode.type) {
            case 't':
                return true;
            case 'f':
                return false;
            case '!':
                return ! parseBoolTree(boolExprNode.children[0]); // Always just one child
            case '&':
                for (const childBoolExprNode of boolExprNode.children) {
                    // If any sub-expression is false, short-circuit the AND
                    if (false === parseBoolTree(childBoolExprNode)) return false;
                }
                return true;
            case '|':
                for (const childBoolExprNode of boolExprNode.children) {
                    // If any sub-expression is true, short-circuit the OR
                    if (true === parseBoolTree(childBoolExprNode)) return true;
                }
                return false;
            default:
                // Should never get here
                console.log("Something went wrong! Current node type: " + boolExprNode.type);
        }
    };
    return parseBoolTree(rootNode);

    // Time complexity: if N is the expression length, we parse the expression once to
    // generate the tree, so O(N). The tree is going to have about N/2 elements, since
    // somewhere around half the characters are parentheses. Actually processing the tree
    // will then take O(N/2) ~ O(N).
    // Memory complexity: the tree will have around N/2 nodes, so in the range of O(N).
    // Optimization ideas: finding a way to avoid storing the tree in memory would be
    // useful, but I doubt an O(1) memory space solution is possible (imagine an expression
    // like &(|(&(|(...&(t,t),t)...,t),t),t), where you'd need to remember all the operations
    // as you go down the parentheses.
    // Optimization ideas: it might be possible to make the solution faster (though still
    // O(N)) by processing the expression as we go, rather than first converting it into a
    // more handy data structure.
};