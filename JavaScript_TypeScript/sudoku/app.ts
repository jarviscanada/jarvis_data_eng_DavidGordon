class SudokuSolver {
	public board: number[][];

	constructor() {
		this.board = [
			[5, 3, 0, 0, 7, 0, 0, 0, 0],
			[6, 0, 0, 1, 9, 5, 0, 0, 0],
			[0, 9, 8, 0, 0, 0, 0, 6, 0],
			[8, 0, 0, 0, 6, 0, 0, 0, 3],
			[4, 0, 0, 8, 0, 3, 0, 0, 1],
			[7, 0, 0, 0, 2, 0, 0, 0, 6],
			[0, 6, 0, 0, 0, 0, 2, 8, 0],
			[0, 0, 0, 4, 1, 9, 0, 0, 5],
			[0, 0, 0, 0, 8, 0, 0, 7, 9]
		]
	}

	public solveBoard() {
		this.toString()
		for (let x = 0; x < 9; x++) {
			for (let y = 0; y < 9; y++) {
				if (this.board[x][y] == 0) {
					for (let n = 1; n < 10; n++) {
						if (this.place(x, y, n)) {
							this.board[x][y] = n;
							this.solveBoard();
							this.board[x][y] = 0;
						}
					}
					return;
				}
			}
		}
		console.log(this.toString())
	}

	/** Determines if `n` can be placed in row `x` column `y` */
	private place(x: number, y: number, n: number): boolean {
		// Check row (x - left -> right)
		for(let i = 0; i < 9; i++) {
			if(this.board[x][i] == n) return false; // Hit a duplicate
		}

		// Check column (y - top -> bottom)
		for(let i = 0; i < 9; i++) {
			if(this.board[i][y] == n) return false; // Hit a duplicate
		}

		// Check square
		let x0 = (x/3)*3;
		let y0 = (y/3)*3;
		for(let i = 0; i < 3; i++) {
			for(let j = 0; j < 3; j++) {
				if(this.board[x0][y0] == n) return false;
			}
		}

		return true;
	}

	private toString(): string {
		let result = "";
		for(let i = 0; i < this.board.length; i++) {
			for(let j = 0; j < this.board[i].length; j++) {
				result += this.board[i][j];
				result += ' | ';
			}
			result += '\n';
		}
		return result;
	}
}

let solver = new SudokuSolver();
console.log(solver.solveBoard())

/* X AND Y ARE BACKWARDS, FIX THIS AT THE END */