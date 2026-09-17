class Solution {
    public int findDuplicate(int[] nums) {
        int i=0;
        while(i<nums.length){
            if(nums[0]==nums[1]) return nums[i];
            int correctIndex = nums[i] - 1;
            if(nums[i]!=nums[correctIndex])
            swap(nums,i,correctIndex);
            else{
                if(i!=correctIndex && nums[i]==nums[correctIndex])
                return nums[i];

                i++;  
            }  
        }
        return -1;
    }
public void swap(int[] nums,int i,int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}