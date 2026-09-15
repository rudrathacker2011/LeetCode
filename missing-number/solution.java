class Solution {
    public int missingNumber(int[] nums) {
        int i = 0;

        // Place each number x at index x, only if x < n
        while (i < nums.length) {
            int val = nums[i];
            if (val < nums.length && nums[i] != nums[val]) {
                swap(nums, i, val);
            } else {
                i++;
            }
        }

        // First index where nums[index] != index is the missing number
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != j) {
                return j;
            }
        }

        // If all indices match, missing number is n
        return nums.length;
    }

    static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}