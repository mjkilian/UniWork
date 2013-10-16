import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

import static choco.Choco.*;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.Model;
import choco.kernel.solver.Solver;
import choco.kernel.model.variables.integer.IntegerVariable;

public class Sudoku0 {
	static final int ROWSIZE = 9;
	static final int COLUMNSIZE = 9;
	static final int MINVAL = 1;
	static final int MAXVAL = 9;
	static final int BOXWIDTH = 3;
	static final int BOXHEIGHT = 3;
	
	public static void main(String[] args){
		/*Read in file and prepare in scanner*/
		
		File input = null;
		Scanner scan = null;
		
		if(args.length < 1){
			usage();
			System.exit(0);
		}
		try{
			 input = new File(args[0]);
			 scan = new Scanner(input);
		}catch(IOException e){
			System.err.println("File could not be read.");
			e.printStackTrace();
		}
		
		/*Set up model*/
		Model model = new CPModel();
		IntegerVariable[][] board = makeIntVarArray("board",ROWSIZE,COLUMNSIZE,MINVAL,MAXVAL);
		
		/*add column and row constraint 
		 * cell[i][j] of the board is the cell at the intersection of row i and column j
		 * as per sudoku convention
		 */
		
		/*add constraints so that column values must be unique*/
		for(int i = 0; i < ROWSIZE; i++){
			for(int j = 0; j < COLUMNSIZE; j++){
				for(int k = j + 1; k < COLUMNSIZE; k++){
					model.addConstraint(neq(board[j][i],board[k][i]));
				}
			}
		}
		
		/*add constraints so that rows must be unique*/
		for(int i = 0; i < COLUMNSIZE; i++){
			for(int j = 0; j < ROWSIZE; j++){
				for(int k = j + 1; k < ROWSIZE; k++){
					model.addConstraint(neq(board[i][j],board[i][k]));
				}
			}
		}
		
		/*add constraints so that each box has no repeated values
		 * Boxes are indexed by the position of their top-left most cell
		 * on the board.
		 */
		for(int b1 = 0; b1 < ROWSIZE; b1 += BOXWIDTH){
			for(int b2 = 0; b2 < COLUMNSIZE; b2 += BOXHEIGHT){
				for(int curRow = b1; curRow < b1 + BOXWIDTH; curRow++){
					for(int curCol = b2; curCol < b2 + BOXHEIGHT; curCol++){
						for(int i = b1; i < b1 + BOXWIDTH; i++){
							for(int j = b2; j < b2 + BOXHEIGHT; j++){
								//System.out.println("Checking cell (" + curRow + "," + curCol+") against cell ("+i+","+j+")");
								if(i == curRow || j == curCol){ //constraint already set as row/column constraint
									continue;
								}else{
									model.addConstraint(neq(board[curRow][curCol],board[i][j]));
								}
							}
						}
					}
				}
			}
		}
		
	/*	add in the constraints given by the problem clues*/
		for(int i = 0; i < COLUMNSIZE; i++){
			for(int j = 0; j < ROWSIZE; j++){
				int cellVal = scan.nextInt();
				if(cellVal != 0){
					model.addConstraint(eq(board[i][j],cellVal));
				}
			}
		}
		
		/*Build the solver*/
		Solver solver = new CPSolver();
		solver.read(model);
		
		/*Solve*/
		solver.solve();
		
		/*Output Solution*/
		for(int i=0; i< COLUMNSIZE; i++){
			for(int j=0; j< ROWSIZE; j++){
				System.out.print(solver.getVar(board[i][j]).getVal() + " ");
			}
			System.out.print("\n");
		}
	}
	
	public static void printModelToFile(String filename,Model m) throws IOException{
		/*Utility Method for checking constraints*/
		File out = new File(filename);
		Writer writer = new BufferedWriter(new FileWriter(out));
		writer.write(m.pretty());
		writer.close();
	}
	
	public static void usage(){
		System.err.println("Sudoku0 <filename>");
	}

}
