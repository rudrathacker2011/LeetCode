class Solution {
    public int search(int[] arr, int target) {

        int s = 0;
        int e = arr.length - 1;

        while (s <= e) {

            int m = s + (e - s) / 2;

            // MISTAKE 1:
            // You were not checking whether mid itself is the target.
            //
            // Always check this FIRST.
            if (arr[m] == target) {
                return m;
            }


            // ------------------------------------------------
            // CASE 1: LEFT HALF IS SORTED
            // ------------------------------------------------
            //
            // Example:
            //
            // [4, 5, 6, 7, 0, 1, 2]
            //  ↑        ↑
            //  s        m
            //
            // arr[s] <= arr[m]
            //
            // MISTAKE 2:
            // You originally used:
            //
            // arr[s] < arr[m]
            //
            // We use <= because s and m can be the same index.
            //
            if (arr[s] <= arr[m]) {


                // Check whether target lies inside
                // the sorted LEFT half.
                //
                // Range:
                //
                // arr[s] <= target < arr[m]
                //
                // MISTAKE 3:
                // You had:
                //
                // arr[s] < target
                //
                // This misses the case where:
                //
                // target == arr[s]
                //
                if (arr[s] <= target && target < arr[m]) {

                    // Target is in LEFT half
                    e = m - 1;

                } else {

                    // Target is NOT in left half
                    // Therefore search RIGHT
                    s = m + 1;
                }
            }


            // ------------------------------------------------
            // CASE 2: RIGHT HALF IS SORTED
            // ------------------------------------------------
            //
            // If left half isn't sorted,
            // right half must be sorted
            // (assuming distinct elements).
            //
            // Example:
            //
            // [6, 7, 0, 1, 2, 4, 5]
            //           ↑        ↑
            //           m        e
            //
            else {

                // Check whether target lies inside
                // the sorted RIGHT half.
                //
                // Range:
                //
                // arr[m] < target <= arr[e]
                //
                // MISTAKE 4:
                // Your condition excluded target == arr[e].
                //
                if (arr[m] < target && target <= arr[e]) {

                    // Target is in RIGHT half
                    s = m + 1;

                } else {

                    // Target is NOT in right half
                    // Therefore search LEFT
                    e = m - 1;
                }
            }


            // MISTAKE 5:
            //
            // You previously had:
            //
            // else
            //     return m;
            //
            // That is wrong because reaching an else
            // does NOT mean arr[m] == target.
            //
            // We return m ONLY when:
            //
            // arr[m] == target
        }

        // Target does not exist
        return -1;
    }
}
// 1. Missing:
//    arr[m] == target

// 2. Used:
//    arr[s] < arr[m]

//    Better:
//    arr[s] <= arr[m]

// 3. Used:
//    arr[s] < target

//    Correct left range:
//    arr[s] <= target < arr[m]

// 4. Right range should include arr[e]:

//    arr[m] < target <= arr[e]

// 5. Your final:
//    else return m

//    was incorrect.

//    return m ONLY when:
//    arr[m] == target
            //         MID
            //          |
            //  Is target found?
            //     /         \
            //   YES          NO
            //    |            |
            // return m   Which half sorted?
            //            /            \
            //         LEFT            RIGHT
            //          |                |
            //   target inside?    target inside?
            //      /   \             /   \
            //    YES   NO          YES   NO
            //     |     |            |    |
            //   LEFT  RIGHT        RIGHT LEFT