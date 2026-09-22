import java.util.Arrays;

class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] nums = new int[m + n];
        int i = 0, j = 0, z = 0;
        
        while (i < m && j < n) {
            if (nums1[i] < nums2[j]) {
                nums[z++] = nums1[i++];
            } else {
                nums[z++] = nums2[j++];
            }
        }
        
        // Fix 1: Copy remaining elements from nums1 (if any)
        while (i < m) {
            nums[z++] = nums1[i++];
        }
        
        // Fix 2: Copy remaining elements from nums2 (if any)
        while (j < n) {
            nums[z++] = nums2[j++];
        }
        
        // Print to see the complete merged array
        System.out.println(Arrays.toString(nums));
        
        // Fix 3: Copy the result back into nums1 so LeetCode registers it
        for (int k = 0; k < m + n; k++) {
            nums1[k] = nums[k];
        }
    }
}
