class Solution {
    public int mySqrt(int x) {
        long start = 1;
        long end = x;
        long res = 1;

        if (x == 0) return 0;

        while (start <= end) {
            long mid = start + (end - start) / 2;

            if (mid * mid <= x) {
                res = mid;
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        return (int) res;
    }
}