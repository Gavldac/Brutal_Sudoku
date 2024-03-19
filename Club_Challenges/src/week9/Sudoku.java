package week9;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * A Sudoku puzzle is a 9x9 grid of numbers, where each row, column, and 3x3
 * subgrid contains the numbers 1-9 exactly once.
 * 
 * The goal of this challenge is to write a program that can solve Sudoku
 * puzzles.
 * 
 * Given an input .sdku file (a plain-text file containing the initial state of
 * a Sudoku puzzle), find the solution to the puzzle and write the solution to a
 * new .sdku file.
 * 
 * The input file will contain 9 lines, each containing 9 characters. Each
 * character will be a digit (1-9) or a period (.), representing an empty cell.
 * 
 * The output file should contain the same 9x9 grid, with the empty cells filled
 * in with the correct numbers.
 * 
 * For example, given the following input file:
 * 
 * 53..7.... 6..195... .98....6. 8...6...3 4..8.3..1 7...2...6 .6....28.
 * ...419..5 ....8..79
 * 
 * The output file should be:
 * 
 * 534678912 672195348 198342567 859761423 426853791 713924856 961537284
 * 287419635 345286179
 * 
 * This challenge is broken up into parts. In Programming Club, we will split
 * into teams of 3-4 people to work on each method.
 */
public class Sudoku {

	public static void main(String[] args) {
		System.out.println("Welcome to the Sudoku Solver!");

		Scanner in = new Scanner(System.in);
		System.out.print("Enter the name of the input file (do not include the file extension): ");
		String inputFilename = in.nextLine();
		System.out.print("Enter the name of the output file (do not include the file extension): ");
		String outputFilename = in.nextLine();
		in.close();

		Sudoku app = new Sudoku();
		Table table = new Table();
		app.loadPuzzle(inputFilename, table);
		table.printTable();
		app.solve(table);
		
//		app.saveSolution(outputFilename, grid);
	}

	/**
	 * Given an input filename, read the Sudoku puzzle from the file and return it
	 * as a 9x9 grid numbers, with no value for spaces in the file.
	 * 
	 * @param filename
	 * @return
	 * @return
	 */
	public void loadPuzzle(String filename, Table table) {
		System.out.println("Read sudoku puzzle from " + filename + ".sdku");
		try (BufferedReader br = Files.newBufferedReader(Paths.get(filename + ".sdku"))) {
			String line;
			int row = 0;
			while ((line = br.readLine()) != null) {
				Integer[] nums = new Integer[9];
				for (int i = 0; i < line.length(); i++) {
					if (Character.isDigit(line.charAt(i)))
						nums[i] = Character.getNumericValue(line.charAt(i));
					else
						nums[i] = null;
				}
				table.setRow(row++, nums);
			}
		} catch (IOException e) {
			System.out.println("Mistakes were made. Good luck");
			e.printStackTrace();
		}
	}

	/**
	 * Given an output filename and a 9x9 grid of numbers, write the Sudoku puzzle
	 * to the file.
	 * 
	 * @param grid
	 * @return
	 */
	public void saveSolution(String filename, Table table) {
		System.out.println("Sudoku puzzle solution to " + filename + ".sdku");
		// TODO: Account for possible invalid solution from isSolved and handle an
		// error.
		// TODO: Write the solution to the file.
	}

	/**
	 * Solve the Sudoku puzzle.
	 * 
	 * @param grid
	 */
	public void solve(Table table) {
		int row = 0;
		int col = 0;
		boolean solution = solve(table, 0, 0);
	}

	private boolean solve(Table table, int row, int col) {
		int nextCol = (col + 1) % 9;
		int nextRow = (nextCol == 0) ? row + 1 : row;
		// Base case - progressed past the last row and column.
		if (row == 9) {
			table.printTable();
			return true;
		}
		// If the cell has a value it is skipped.
		if (table.getCell(row, col) != null) {
			solve(table, nextRow, nextCol);
		} else {
			// An empty cell prompts a generation of possible numbers
			List<Integer> possibleNumbers = getPossibleNumbers(table, row, col);
			if (possibleNumbers.size() == 0) // If there are no available numbers the solve() returns false
				return false;
			// each possible number is given in ascending order
			for (Integer el : possibleNumbers) {
				table.setCell(el, row, col);
				// here is the check to see if the next possible number needs to be tested
				if (solve(table, nextRow, nextCol))
					return true;
				// reset the cell so that it is not assumed to be solved after failing the
				// current tested values.
				table.setCell(null, row, col);
			}
		}
		return false;
	}

	private List<Integer> getPossibleNumbers(Table table, int row, int col) {
		List<Integer> possibleNums = new ArrayList<>();
		HashSet<Integer> currentKnown = new HashSet<>();
		
		// Build a hashset of known values based on row, column, and grouping
		// null values are added to the hastset but are not checked against.
		for (Integer el : table.getRow(row)) {
				currentKnown.add(el);
		}
		for (Integer el : table.getColumn(col)) {
				currentKnown.add(el);
		}
		for (Integer el : table.getBox(row, col)) {
				currentKnown.add(el);
		}
		// Use i to check if numbers 1-9 are in the hashset. 
		// If not in the set then they should be a possible solution for a cell.
		for (int i = 1; i <= 9; i++) {
			if (! currentKnown.contains(i)) 
				possibleNums.add(i);
			}
		return possibleNums;
	}

	/**
	 * Given a 9x9 grid of numbers, return true if the grid is a valid Sudoku puzzle
	 * solution, and false otherwise.
	 * 
	 * A valid Sudoku puzzle is one where each row, column, and 3x3 subgrid contains
	 * the numbers 1-9 exactly once.
	 * 
	 * @param grid a 9x9 grid of numbers
	 * @return true if the grid is a valid Sudoku puzzle, and false otherwise
	 */

	/**
	 * Given an array of 9 numbers, return true if the array contains the numbers
	 * 1-9 exactly once, and false otherwise.
	 * 
	 * @param set an array of 9 numbers
	 * @return true if the array contains the numbers 1-9 exactly once, and false
	 *         otherwise
	 */
	private boolean isValidSet(int[] set) {
		boolean[] found = new boolean[9];
		for (int i = 0; i < 9; i++) {
			if (set[i] < 1 || set[i] > 9) {
				return false;
			} else if (found[set[i] - 1]) {
				return false;
			} else {
				found[set[i] - 1] = true;
			}
		}
		return true;
	}
}