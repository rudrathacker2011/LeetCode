class Solution {
    public int heightChecker(int[] heights) {

        // Find maximum height
        int max = heights[0];

        for (int h : heights) {
            if (max < h) {
                max = h;
            }
        }

        // Frequency array
        int[] cnt = new int[max + 1];

        for (int h : heights) {
            cnt[h]++;
        }

        int index = 0;
        int answer = 0;

        // Reconstruct sorted order
        for (int i = 1; i <= max; i++) {

            while (cnt[i] > 0) {

                if (heights[index] != i) {
                    answer++;
                }

                index++;
                cnt[i]--;
            }
        }

        return answer;
    }
}