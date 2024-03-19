package week9;

/**
* Class representing a 9x9 Sukoku table
* This class can hold Integer values for each cell
* as well as null if a value is not knonw.
* @author Gavldac Klasmov (Edwin Casady)
*/
public class Table {

	private Integer[][] cells = new Integer[9][9];

	public Table() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				cells[i][j] = null;
			}
		}
	}

	/**
	 * Returns an array of Integer values that
	 * represent the current state of the puzzle.
	 * 
	 * @param row - should be a value [0-8] - referencing one of the 9 rows of a sudoku puzzle
	 * @return Integer[]  - should be an array of length 9 with Integer values of known cells and null for unknown.
	 */
	public Integer[] getRow(int row) {
		Integer[] results = new Integer[9];
		for (int i = 0; i < cells[row].length; i++) {
			if (cells[row][i] != null)
				results[i] = cells[row][i];
		}
		return results;
	}

	public Integer[] getColumn(int col) {
		Integer[] results = new Integer[9];
		for (int i = 0; i < cells[col].length; i++) {
			if (cells[i][col] != null)
				results[i] = cells[i][col];
		}
		return results;
	}

	public Integer[] getBox(int row, int col) {
		Integer[] results = new Integer[9];
		int rowCurrent, colCurrent;
		if (row < 3)
			rowCurrent = 0;
		else if (row > 5)
			rowCurrent = 6;
		else
			rowCurrent = 3;

		if (col < 3)
			colCurrent = 0;
		else if (col > 5)
			colCurrent = 6;
		else
			colCurrent = 3;

		for (int i = 0; i < 9; i++) {
			if (i > 0 && i % 3 == 0) { // Keep the rows/cols in the correct range
				rowCurrent++;
				colCurrent -= 3; // start the new row at the first appropriate column.
			}
			if (cells[rowCurrent][colCurrent] != null)
				results[i] = cells[rowCurrent][colCurrent];
			colCurrent++;
		}
		return results;
	}

	/**
	 * Console output print command to print 
	 * the entire puzzle in its current state.
	 * 
	 */
	public void printTable() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				System.out.print(cells[i][j] + " ");
			}
			System.out.println();
		}
	}
}
