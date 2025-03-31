class Solution {
    public boolean isValidSudoku(char[][] board) {
        // We will use the following technique:
        // boolean encounteredDigit[9];
        // Use a boolean array as a super-basic hash map for
        // "did this digit show up before?".
        // A digit character corresponds to
        // encounteredDigit[digit - '1']
        // which also serves as a nice char-to-int parse.
        // (It's -'1' instead of -'0' because Sudoku doesn't
        // use '0'. This lets us save one whole bit of memory. :D)

        // Check rows
        for (short rowIndex = 0; rowIndex < 9; ++rowIndex) {
            boolean encounteredDigit[] = new boolean[9]; // Defaults to false

            for (short colIndex = 0; colIndex < 9; ++colIndex) {
                if ('.' != board[rowIndex][colIndex]) {
                    // Check for duplicate in column
                    if (encounteredDigit[board[rowIndex][colIndex] - '1']) return false;

                    // Else mark digit as encountered
                    encounteredDigit[board[rowIndex][colIndex] - '1'] = true;
                }
            }
        }

        // Check columns
        for (short colIndex = 0; colIndex < 9; ++colIndex) {
            boolean encounteredDigit[] = new boolean[9]; // Defaults to false

            for (short rowIndex = 0; rowIndex < 9; ++rowIndex) {
                if ('.' != board[rowIndex][colIndex]) {
                    // Check for duplicate in column
                    if (encounteredDigit[board[rowIndex][colIndex] - '1']) return false;

                    // Else mark digit as encountered
                    encounteredDigit[board[rowIndex][colIndex] - '1'] = true;
                }
            }
        }

        // Check 3x3s
        // Adjust left-right / up-down to select which 3x3 square we're checking
        for (short leftRightAdjust = 0; leftRightAdjust < 9; leftRightAdjust += 3) {
        for (short upDownAdjust = 0; upDownAdjust < 9; upDownAdjust += 3) {
            // Check the individual 3x3
            boolean encounteredDigit[] = new boolean[9]; // Defaults to false

            for (short squareRowIndex = 0; squareRowIndex < 3; ++squareRowIndex) {
            for (short squareColIndex = 0; squareColIndex < 3; ++squareColIndex) {
                char digit = board[squareRowIndex + upDownAdjust][squareColIndex + leftRightAdjust];

                if ('.' != digit) {
                    // Check for duplicate in 3x3
                    if (encounteredDigit[digit - '1']) return false;

                    // Else mark digit as encountered
                    encounteredDigit[digit - '1'] = true;
                }
            }
            }
        }
        }

        // If all was well, board is valid
        return true;
    }

    // Time complexity: O(9 * 9 * 3). I parse the board three times - once for rows, once for columns,
    //                  and once for 3x3s. It's quite certainly possible to increase the memory usage
    //                  in order to only require one pass, but that seems somewhat unnatural.
    // Memory complexity: I allocate one array of 9 booleans (to serve as hash map for encountered
    //                    digits when checking for duplicated) and one char (to make code more readable).
    //                    At such low memory usage, JVM decisions are likely to matter more than mine.
}