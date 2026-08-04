class Solution {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        // Always binary search the smaller array.
        // This guarantees O(log(min(m, n))).
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int m = nums1.length;
        int n = nums2.length;


        // Number of elements that should be on LEFT.
        //
        // Odd:
        // 5 elements → 3 left, 2 right
        //
        // Even:
        // 6 elements → 3 left, 3 right
        int leftSize = (m + n + 1) / 2;


        // We are binary searching PARTITION POSITIONS,
        // not array indices.
        //
        // For [1,3], possible partitions are:
        //
        // | 1 3
        // 1 | 3
        // 1 3 |
        //
        // Therefore range is 0 → m.
        int start = 0;
        int end = m;


        while (start <= end) {

            // Choose how many elements nums1
            // contributes to the LEFT side.
            int partition1 =
                    start + (end - start) / 2;


            // Remaining LEFT elements must
            // come from nums2.
            int partition2 =
                    leftSize - partition1;


            // ---------------------------------
            // Find four partition boundaries
            //
            // nums1: ... A | B ...
            // nums2: ... C | D ...
            // ---------------------------------


            // Largest LEFT element from nums1
            int A = (partition1 == 0)
                    ? Integer.MIN_VALUE
                    : nums1[partition1 - 1];


            // Smallest RIGHT element from nums1
            int B = (partition1 == m)
                    ? Integer.MAX_VALUE
                    : nums1[partition1];


            // Largest LEFT element from nums2
            int C = (partition2 == 0)
                    ? Integer.MIN_VALUE
                    : nums2[partition2 - 1];


            // Smallest RIGHT element from nums2
            int D = (partition2 == n)
                    ? Integer.MAX_VALUE
                    : nums2[partition2];


            // Correct partition:
            //
            // A <= D
            // C <= B
            if (A <= D && C <= B) {

                // ODD total number of elements
                if ((m + n) % 2 == 1) {

                    // Extra element is on LEFT.
                    return Math.max(A, C);
                }


                // EVEN total number of elements:
                //
                // median =
                // largest LEFT + smallest RIGHT
                // --------------------------------
                //               2
                return (
                        (double) Math.max(A, C)
                        + Math.min(B, D)
                       ) / 2.0;
            }


            // A > D means nums1 contributed
            // too many/too-large elements to LEFT.
            //
            // Move nums1 partition LEFT.
            else if (A > D) {

                end = partition1 - 1;
            }


            // Otherwise C > B.
            //
            // nums1 contributed too few elements
            // to LEFT.
            //
            // Move nums1 partition RIGHT.
            else {

                start = partition1 + 1;
            }
        }


        // For valid sorted input, we should
        // always find a valid partition.
        return 0.0;
    }
}
//               CORRECT PARTITION

// nums1:  ... A | B ...
// nums2:  ... C | D ...

//           A <= D
//           C <= B
//              ↓
//            FOUND


// A > D                     C > B
//   ↓                         ↓
// too much from nums1      too little from nums1
//   ↓                         ↓
// move LEFT ←              → move RIGHT


// ODD  → max(A,C)

// EVEN → [max(A,C) + min(B,D)] / 2