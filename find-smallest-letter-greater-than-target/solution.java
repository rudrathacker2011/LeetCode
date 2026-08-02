class Solution {
    public char nextGreatestLetter(char[] letters, char key) {
        int s = 0;
        int e = letters.length-1;
        int n = letters.length;
        while(s<=e){
            int mid = s+(e-s)/2;
            if(letters[mid]>key)
            e=mid-1;
            else
            s=mid+1;
        }
        return letters[s%n] ;
}
}