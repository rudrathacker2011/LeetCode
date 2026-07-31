class solution {
    public static int findCeilingNumber(int[] arr,int key) {
        int s = 0;
        int e = arr.length-1;
        int ans = 0;
        while(s<e){
            int mid = s+(e-s)/2;
            if(arr[mid]>key){
                ans = arr[mid];
                e = mid - 1;
            }
            else{
                s = mid + 1;
            }
        }
        return ans;
    }
}