class Solution {
    public int climbStairs(int n) {
        // Base cases
        if (n <= 2) {
            return n;
        }
        
        // prev2 represents climbStairs(n-2), prev1 represents climbStairs(n-1)
        int prev2 = 1;
        int prev1 = 2;
        
        // Iteratively calculate ways up to n
        for (int i = 3; i <= n; i++) {
            int current = prev1 + prev2;
            prev2 = prev1;
            prev1 = current;
        }
        
        return prev1;
    }
}
