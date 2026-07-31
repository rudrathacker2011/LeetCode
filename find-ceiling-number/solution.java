class solution {
    public static int findCeilingNumber(int[] arr,int key) {
        int s = 0;
        int e = arr.length-1;
        int ans = 0;
        while(s<=e){
            int mid = s+(e-s)/2;
            if(arr[mid]>key){
                ans = arr[mid];
                e = mid - 1;
            }
            else{
                s = mid + 1;
            }
        }
        return ans; // also return s ; works in alternate of return ans.
    }
}
<<<<<<< HEAD
=======
/*
After binary search finishes:

           e   s
Array: ... | ...

e = Last element on the LEFT side
s = First element on the RIGHT side

Ceiling = arr[s]
Floor   = arr[e]
*/
>>>>>>> 929dd03 (Add floor and ceiling binary search solutions)
