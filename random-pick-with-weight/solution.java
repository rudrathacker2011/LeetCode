import java.util.Random;

class Solution {
    // This array is our Mela Register Book. It stores the *last* ticket number for each stall.
    private int[] prefixSums; 
    
    // This is our blindfolded chief guest who will pick a random chit from the matka.
    private Random rand;

    // CONSTRUCTOR: It's morning, the mela is starting, and we are setting up the register.
    public Solution(int[] w) {
        prefixSums = new int[w.length];
        int currentSum = 0; // Keeping track of the tickets handed out so far.
        
        // We visit each stall one by one...
        for (int i = 0; i < w.length; i++) {
            currentSum += w[i];       // We add this stall's tickets to our running total.
            prefixSums[i] = currentSum; // We write the *last* ticket number in the register.
        }
        
        // The chief guest is ready.
        rand = new Random();
    }

    // THE DRAW: The evening event where we pick a winner!
    public int pickIndex() {
        // We look at the very last entry in our register to see the total number of tickets.
        int totalSum = prefixSums[prefixSums.length - 1];
        
        // The chief guest puts a hand in the matka and pulls out a ticket!
        // rand.nextInt(totalSum) gives 0 to (totalSum-1). Adding 1 makes it 1 to totalSum.
        int target = rand.nextInt(totalSum) + 1;
        
        // Now, we need to find which stall this ticket belongs to.
        // We could flip the register pages one by one (Linear Search), but the crowd is impatient.
        // So, we use Binary Search to flip exactly to the right page quickly!
        int left = 0;
        int right = prefixSums.length - 1;
        
        while (left < right) {
            // Open the register book to the middle stall.
            int mid = left + (right - left) / 2;
            
            // If the middle stall's last ticket number is less than our winning ticket...
            if (prefixSums[mid] < target) {
                // The winning ticket must have been given to a stall further down the line.
                // Ignore the left half and move forward.
                left = mid + 1;
            } else {
                // The middle stall's last ticket is greater than or equal to our winning ticket.
                // This stall might be the winner, or it might be a stall before it.
                // So we cut off the right half, but keep this stall (mid) as a candidate.
                right = mid;
            }
        }
        
        // When left and right meet, we've found the exact stall that holds the winning ticket!
        return left;
    }
}