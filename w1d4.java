import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Scratch {
    public static void main(String[] args) {
        System.out.println("subsetSum is exist: " + subsetSum(new int[]{3, 4, 7, 8}, 11));
        System.out.println("subsetSum first: " + subsetSumFirst(new int[]{3, 4, 7, 8}, 11));
        System.out.println("subsetSum all: " + subsetSumAll(new int[]{3, 4, 7, 8}, 11));
        knapsack();


        ItemValue[] arr = {new ItemValue(25, 5),
                new ItemValue(12, 6),
                new ItemValue(24, 8),
                new ItemValue(16, 2),
                new ItemValue(28, 7),
        };


        double maxValue = fractionalKnapsack(arr, 20);
        System.out.println("Maximum fractional value: " + maxValue);
    }

    static boolean subsetSum(int[] input, int k) {
        boolean[][] dp = new boolean[input.length + 1][k + 1];

        for (int i = 0; i < input.length; i++) {
            dp[i][0] = true;
        }
        for (int i = 1; i <= input.length; i++) {
            for (int j = 1; j <= k; j++) {
                if (j - input[i - 1] >= 0) {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - input[i - 1]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[input.length][k];
    }

    static List<Integer> subsetSumFirst(int[] input, int k) {
        boolean[][] dp = new boolean[input.length + 1][k + 1];

        for (int i = 0; i < input.length; i++) {
            dp[i][0] = true;
        }
        for (int i = 1; i <= input.length; i++) {
            for (int j = 1; j <= k; j++) {
                if (j - input[i - 1] >= 0) {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - input[i - 1]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        if (!dp[input.length][k]) {
            return Collections.emptyList();
        }

        // Trace back to find one subset
        List<Integer> subset = new ArrayList<>();
        int i = input.length;
        int j = k;

        while (i > 0 && j > 0) {
            // If the value comes from dp[i-1][j], the current element is not included
            if (dp[i - 1][j]) {
                i--;
            } else {
                // Current element is included
                subset.add(input[i - 1]);
                j -= input[i - 1];
                i--;
            }
        }

        // Convert the subset list to an array
        return subset;
    }

    static List<List<Integer>> subsetSumAll(int[] input, int k) {
        boolean[][] dp = new boolean[input.length + 1][k + 1];

        for (int i = 0; i < input.length; i++) {
            dp[i][0] = true;
        }
        for (int i = 1; i <= input.length; i++) {
            for (int j = 1; j <= k; j++) {
                if (j - input[i - 1] >= 0) {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - input[i - 1]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        if (!dp[input.length][k]) {
            return Collections.emptyList();
        }
        List<List<Integer>> allSubsets = new ArrayList<>();
        findSubsets(dp, input, input.length, k, new ArrayList<>(), allSubsets);
        return allSubsets;
    }

    private static void findSubsets(boolean[][] dp, int[] input, int i, int j, List<Integer> current, List<List<Integer>> allSubsets) {
        // Base case: if j == 0, we found a valid subset
        if (j == 0) {
            allSubsets.add(new ArrayList<>(current));
            return;
        }

        // If we've used all elements, stop
        if (i == 0) {
            return;
        }

        // If the subset sum without including input[i-1] is possible, backtrack without including it
        if (dp[i - 1][j]) {
            findSubsets(dp, input, i - 1, j, current, allSubsets);
        }

        // If the subset sum including input[i-1] is possible, backtrack including it
        if (j >= input[i - 1] && dp[i - 1][j - input[i - 1]]) {
            current.add(input[i - 1]);
            findSubsets(dp, input, i - 1, j - input[i - 1], current, allSubsets);
            // Backtrack (remove the last element added)
            current.remove(current.size() - 1);
        }
    }

    static void knapsack() {
        int Wmax = 20;
        int[] values = {25, 12, 24, 16, 28};
        int[] weights = {5, 6, 8, 2, 7};
        int n = values.length;

        // DP table
        int[][] dp = new int[n + 1][Wmax + 1];

        // Fill DP table
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= Wmax; w++) {
                if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - weights[i - 1]] + values[i - 1]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // The maximum value
        System.out.println("Maximum value: " + dp[n][Wmax]);

        // Backtrack to find the items included
        List<Integer> itemsIncluded = new ArrayList<>();
        int w = Wmax;
        for (int i = n; i > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                itemsIncluded.add(i - 1); // Add the item (0-based index)
                w -= weights[i - 1];
            }
        }

        // Print items included
        System.out.print("Items included (0-based index): ");
        for (int item : itemsIncluded) {
            System.out.print((char) ('a' + item) + " ");
        }
        System.out.println();
    }


    // Item value class
    static class ItemValue {

        int profit, weight;

        // Item value function
        public ItemValue(int val, int wt) {
            this.weight = wt;
            this.profit = val;
        }
    }

    private static double fractionalKnapsack(ItemValue[] arr,
                                             int capacity) {
        // Sorting items by profit/weight ratio;
        Arrays.sort(arr, (item1, item2) -> {
            double cpr1
                    = new Double((double) item1.profit
                    / (double) item1.weight);
            double cpr2
                    = new Double((double) item2.profit
                    / (double) item2.weight);

            if (cpr1 < cpr2)
                return 1;
            else
                return -1;
        });

        double totalValue = 0d;

        for (ItemValue i : arr) {

            int curWt = (int) i.weight;
            int curVal = (int) i.profit;

            if (capacity - curWt >= 0) {

                // This weight can be picked whole
                capacity = capacity - curWt;
                totalValue += curVal;
            } else {

                // Item cant be picked whole
                double fraction
                        = ((double) capacity / (double) curWt);
                totalValue += (curVal * fraction);
                capacity
                        = (int) (capacity - (curWt * fraction));
                break;
            }
        }

        return totalValue;
    }

}
