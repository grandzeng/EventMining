package edu.fiu.cs.kdrg.evevt.main;

public class Solution {

	public static int minPathSum(int[][] grid) {
		// IMPORTANT: Please reset any member data you declared, as
		// the same Solution instance will be reused for each test case.
		if (grid.length == 0 || grid[0].length == 0)
			return 0;
		int[][] sum = new int[grid.length][];
		for (int i = 0; i < sum.length; i++)
			sum[i] = new int[grid[i].length];
		// initialization
		int temp = 0;
		for (int i = 0; i < sum.length; i++) {
			sum[i][0] = temp + grid[i][0];
			temp = sum[i][0];
		}
		temp = 0;
		for (int j = 0; j < sum[0].length; j++) {
			sum[0][j] = temp + grid[0][j];
			temp = sum[0][j];
		}

		int temp1 = 0;
		int temp2 = 0;
		// dynamic programming
		for (int i = 1; i < sum.length; i++) {
			for (int j = 1; j < sum[i].length; j++) {
				temp1 = sum[i][j - 1] + grid[i][j];
				temp2 = sum[i - 1][j] + grid[i][j];
				sum[i][j] = (temp1 <= temp2 ? temp1 : temp2);
			}
		}

		return sum[sum.length - 1][sum[0].length - 1];
	}

	public static void main(String args[]) {
		int grid[][] = new int[3][4];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++)
				System.out.print(grid[i][j] + "\t");
			System.out.println();
		}
	}
}