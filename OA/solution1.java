import java.util.ArrayList;
import java.util.Collections;

public class solution1 {
    public static int[] findPartitionCost(int[] cost, int k) {
        int n = cost.length;
        long fixed = (long) cost[0] + cost[n - 1];
        if (k == 1) {
            return new int[]{(int) fixed, (int) fixed};
        }

        ArrayList<Long> sums = new ArrayList<>();
        for (int i = 0; i < n - 1; i++) {
            sums.add((long) cost[i] + cost[i + 1]);
        }

        Collections.sort(sums);

        long minSum = 0;
        for (int i = 0; i < k - 1; i++) {
            minSum += sums.get(i);
        }

        long maxSum = 0;
        int start = sums.size() - (k - 1);
        for (int i = start; i < sums.size(); i++) {
            maxSum += sums.get(i);
        }

        int minResult = (int) (fixed + minSum);
        int maxResult = (int) (fixed + maxSum);

        return new int[]{minResult, maxResult};
    }
}