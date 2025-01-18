import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Scratch {
    public static void main(String[] args) {
        System.out.println("subsetSum is exist: " + subsetSum(new int[]{3, 4, 7, 8}, 11));
        System.out.println("subsetSum first: " + subsetSumFirst(new int[]{3, 4, 7, 8}, 11));
        System.out.println("subsetSum all: " + subsetSumAll(new int[]{3, 4, 7, 8}, 11));
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
        }   if (!dp[input.length][k]) {
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
        }   if (!dp[input.length][k]) {
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

}
