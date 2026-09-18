class Solution {
    public List<Integer> findDuplicates(int[] nums) {
        int i = 0;
        List<Integer> ans = new ArrayList<>();
        
        while (i < nums.length) {
            // Skip elements that are out of bounds (our dummy 0s)
            if (nums[i] == 0) {
                i++;
                continue;
            }
            
            int correctIndex = nums[i] - 1;
            
            if (nums[i] != nums[correctIndex]) {
                swap(nums, i, correctIndex);
            } else {
                // If it's the same value but at a different index, we found a duplicate!
                if (i != correctIndex) {
                    ans.add(nums[i]);
                    nums[i] = 0; // Turn it to 0 so we don't process or count it again
                }
                i++;
            }
        }
        return ans;
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}