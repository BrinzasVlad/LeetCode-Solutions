class Solution {
public:
    int lengthAfterTransformations(string s, int t) {
        const long long modulo = 1000000007;

        vector<long long> letterCounts(26, 0); // modulo modulo
        // 'a' to 'z'; letterCounts[letter - 'a'] = <occurences of letter>

        for(int i = 0; i < s.size(); ++i) {
            letterCounts[s[i] - 'a']++;
        }

        // Brute force attempt:
        // After 26 transformations, every character (except 'z')
        // goes from 'X' to 'XY' ('z' becomes 'zab')
        // So we can just brute-force the solution 26 transforms
        // at a time.
        while(t >= 26) {
            const long long originalZCount = letterCounts['z' - 'a'];
            for(int i = 24; i >= 0; --i) {
                // 'y' to 'a'
                letterCounts[i+1] = (letterCounts[i+1] + letterCounts[i]) % modulo;
            }
            // Add the 'z' effect
            letterCounts['a' - 'a'] = (letterCounts['a' - 'a'] + originalZCount) % modulo;
            letterCounts['b' - 'a'] = (letterCounts['b' - 'a'] + originalZCount) % modulo;

            t -= 26;
        }

        // Less than 26 transformations left; all elements where
        // t + (letter - 'a') sums to 26 or more will count double, others single
        long long totalCharactersModulo = 0;
        for (int i = 0; i < 26; ++i) {
            // i is (letter - 'a'), recall
            if (t + i >= 26) {
                totalCharactersModulo = (totalCharactersModulo + 2 * letterCounts[i]) % modulo;
            }
            else {
                totalCharactersModulo = (totalCharactersModulo + letterCounts[i]) % modulo;
            }
        }

        return totalCharactersModulo;
        
        // Time complexity: O(N + T), where N is the length of the input string and T is
        // the total amount of transformations. I expected the problem to require stricter
        // mathematical optimization of the answer (e.g. finding a formula), but it seems that
        // linear scaling with T was good enough.
        // Memory complexity: O(1), we're only storing a 26-entry vector with the total count
        // of each letter (and can use modulo to keep values reasonable).
        // Optimization ideas: I'm fairly certain that with enough mathematical analysis, it
        // would be possible to develop a formula that takes my character-count vector and the
        // number of transformations as parameters and directly outputs the final character count,
        // similar to how there are formulas for directly calculating the Nth term of the
        // Fibonacci sequence. Nevertheless, my solution beats 98% of submission in runtime, so
        // this is probably not necessary.
    }
};