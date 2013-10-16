import static choco.Choco.eq;
import static choco.Choco.makeIntVarArray;
import static choco.Choco.allDifferent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.Model;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;


public class Sudoku2 {
	static final int ROWSIZE = 9;
	static final int COLUMNSIZE = 9;
	static final int MINVAL = 1;
	static final int MAXVAL = 9;
	static final int BOXWIDTH = 3;
	static final int BOXHEIGHT = 3;
	
	public static void printModelToFile(String filename,Model m) throws IOException{
		/*Utility Method for checking constraints*/
		File out = new File(filename);
		Writer writer = new BufferedWriter(new FileWriter(out));
		writer.write(m.pretty());
		writer.close();
	}
	
	public static void printSolution(Solver solver, IntegerVariable[][] board){
		for(int i=0; i< COLUMNSIZE; i++){
			for(int j=0; j< ROWSIZE; j++){
				System.out.print(solver.getVar(board[i][j]).getVal() + " ");
			}
			System.out.print("\n");
		}
		System.out.println();
	}
	
	public static void usage(){
		System.err.println("Sudoku2 <filename>");
	}
	
	public static IntegerVariable[] getColumn(IntegerVariable[][] board, int colIndex){
		IntegerVariable[] column = new IntegerVariable[COLUMNSIZE];
		for(int i = 0; i < COLUMNSIZE; i++){
			column[i] = board[i][colIndex];
		}
		return column;
	}
	
	public static IntegerVariable[] getBox(IntegerVariable[][] board, int boxNo){
		/*Boxes are numbered starting at  in typwriter order, left to right*/
		
		IntegerVariable[] box = new IntegerVariable[BOXHEIGHT*BOXWIDTH];
		
		int boxesPerRow = (ROWSIZE/BOXWIDTH);
		int noBoxes = boxesPerRow * (COLUMNSIZE/BOXHEIGHT);
		
		/*Row and column are position of top left cell of box*/
		int boxRow = (boxNo/boxesPerRow) * BOXHEIGHT;
		int boxCol = ((boxNo + 1) % boxesPerRow) * BOXWIDTH;
		
		int cursor = 0;
		for(int i = 0; i < BOXHEIGHT; i++){
			for(int j = 0; j < BOXWIDTH; j++){
				box[cursor++] = board[boxRow + i][boxCol + j];
			}
		}
		return box;
	}
	
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
		
		/*Add row constraints*/
		for(int i = 0; i < COLUMNSIZE; i++){
			model.addConstraint(allDifferent(board[i]));
		}
		
		/*Add column constraints*/
		for(int i = 0; i < ROWSIZE; i++){
			model.addConstraint(allDifferent(getColumn(board,i)));
		}
		
		/*Add box constraints */
		for(int i = 0; i < ((ROWSIZE/BOXWIDTH) * (COLUMNSIZE/BOXHEIGHT)); i++){
			model.addConstraint(allDifferent(getBox(board,i)));
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
		
		try {
			printModelToFile("modelOutput.txt", model);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
}
