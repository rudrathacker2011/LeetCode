class Solution {

    // Main function called by LeetCode
    public int findInMountainArray(int target, MountainArray mountainArr) {

        // Step 1: Find the peak element of the mountain
        int peak = peakIndex(mountainArr);

        // Step 2: Search for target in the ascending part
        int firstTry = orderAgnostic(
            mountainArr,
            target,
            0,
            peak,
            true
        );

        // If target is found on the ascending side,
        // return its index immediately.
        if (firstTry != -1) {
            return firstTry;
        }

        // Step 3: If not found on ascending side,
        // search in the descending part.
        //
        // peak + 1 because peak was already searched above.
        return orderAgnostic(
            mountainArr,
            target,
            peak + 1,
            mountainArr.length() - 1,
            false
        );
    }


    // Binary search that works for both:
    // 1. Ascending array
    // 2. Descending array
    //
    // isAsc = true  -> ascending
    // isAsc = false -> descending
    public int orderAgnostic(
        MountainArray arr,
        int target,
        int start,
        int end,
        boolean isAsc
    ) {

        while (start <= end) {

            // Find middle element
            int mid = start + (end - start) / 2;

            // MountainArray does NOT support arr[mid].
            // We must use arr.get(mid).
            if (arr.get(mid) == target) {
                return mid;
            }


            // -------------------------
            // ASCENDING PART
            // -------------------------
            if (isAsc) {

                // Example:
                // [1, 3, 5, 7, 9]
                //
                // If target is smaller than middle,
                // search on the left.
                if (target < arr.get(mid)) {
                    end = mid - 1;
                }

                // Otherwise search on the right.
                else {
                    start = mid + 1;
                }
            }


            // -------------------------
            // DESCENDING PART
            // -------------------------
            else {

                // Example:
                // [9, 7, 5, 3, 1]
                //
                // If target is greater than middle,
                // search on the left.
                if (target > arr.get(mid)) {
                    end = mid - 1;
                }

                // Otherwise search on the right.
                else {
                    start = mid + 1;
                }
            }
        }

        // Target does not exist in this part
        return -1;
    }


    // Find the peak index of the mountain
    //
    // Example:
    //
    //        9
    //       / \
    //      5   7
    //     /     \
    //    3       4
    //
    // Peak = 9
    //
    // We use binary search because:
    //
    // arr[mid] > arr[mid + 1]
    //      -> we are on descending side
    //      -> peak is at mid or before mid
    //
    // arr[mid] < arr[mid + 1]
    //      -> we are on ascending side
    //      -> peak is after mid
    public int peakIndex(MountainArray arr) {

        int start = 0;

        // MountainArray uses length()
        int end = arr.length() - 1;


        // Continue until start and end point
        // to the same element.
        while (start < end) {

            // Find middle
            int mid = start + (end - start) / 2;


            // We are on the descending side
            //
            // Example:
            //        9
            //       / \
            //      7   6
            //         ^
            //        mid
            //
            // Peak can be mid or somewhere before mid.
            if (arr.get(mid) > arr.get(mid + 1)) {

                end = mid;
            }


            // We are on the ascending side
            //
            // Example:
            //      5
            //     / \
            //    3   7
            //         ^
            //        mid
            //
            // Peak must be after mid.
            else {

                start = mid + 1;
            }
        }

        // start == end
        // Both point to the peak.
        return start;
    }
}