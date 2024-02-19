import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.*;

// This class will wrap up all of the game squares and keep track of the current player turn
public class GameBoard
{
	// define the number of rows and columns as constants
	static public final int NUM_ROWS = 8;
	static public final int NUM_COLS = 8;
	
	// this 2-dimensional array will hold the 8x8 grid of GameSquare objects
	private GameSquare[][] squares;
	
	// The GameBoard constructor will build the visible 8x8 grid of game squares
	public GameBoard(JPanel boardPanel, ActionListener listener)
	{
		// create a grid layout with the 9x9 grid, row-major order
		GridLayout layout = new GridLayout(NUM_ROWS,NUM_COLS);
		
		// we don't want any gap between the buttons
		layout.setHgap(0);
		layout.setVgap(0);
		
		boardPanel.setLayout(layout);

		// create new 2D array of GameSquare objects
		squares = new GameSquare[NUM_ROWS][NUM_COLS];
		
		// Now iterate over every element in the array, creating a new GameSquare object
		// The inner-most loop is "columns" because we want to add items to the GridLayout
		// from left-to-right across the top first, then drop down to the next row.
		for (int row = 0; row < NUM_ROWS; row++)
		{
			for (int col = 0; col < NUM_COLS; col++)
			{
				// create new GameSquare object for this row and column.  Pass in
				// the panel and action listener so the button can be linked up!
				squares[row][col] = new GameSquare(row,col,boardPanel,listener);
			}
		}

		// Mark the checkered pattern black and white squares
		squares[0][1].setType(GameSquare.TYPE_BLACK);
		squares[0][3].setType(GameSquare.TYPE_BLACK);
		squares[0][5].setType(GameSquare.TYPE_BLACK);
		squares[0][7].setType(GameSquare.TYPE_BLACK);
		squares[1][0].setType(GameSquare.TYPE_BLACK);
		squares[1][2].setType(GameSquare.TYPE_BLACK);
		squares[1][4].setType(GameSquare.TYPE_BLACK);
		squares[1][6].setType(GameSquare.TYPE_BLACK);
		squares[2][1].setType(GameSquare.TYPE_BLACK);
		squares[2][3].setType(GameSquare.TYPE_BLACK);
		squares[2][5].setType(GameSquare.TYPE_BLACK);
		squares[2][7].setType(GameSquare.TYPE_BLACK);
		squares[3][0].setType(GameSquare.TYPE_BLACK);
		squares[3][2].setType(GameSquare.TYPE_BLACK);
		squares[3][4].setType(GameSquare.TYPE_BLACK);
		squares[3][6].setType(GameSquare.TYPE_BLACK);
		squares[4][1].setType(GameSquare.TYPE_BLACK);
		squares[4][3].setType(GameSquare.TYPE_BLACK);
		squares[4][5].setType(GameSquare.TYPE_BLACK);
		squares[4][7].setType(GameSquare.TYPE_BLACK);
		squares[5][0].setType(GameSquare.TYPE_BLACK);
		squares[5][2].setType(GameSquare.TYPE_BLACK);
		squares[5][4].setType(GameSquare.TYPE_BLACK);
		squares[5][6].setType(GameSquare.TYPE_BLACK);
		squares[6][1].setType(GameSquare.TYPE_BLACK);
		squares[6][3].setType(GameSquare.TYPE_BLACK);
		squares[6][5].setType(GameSquare.TYPE_BLACK);
		squares[6][7].setType(GameSquare.TYPE_BLACK);
		squares[7][0].setType(GameSquare.TYPE_BLACK);
		squares[7][2].setType(GameSquare.TYPE_BLACK);
		squares[7][4].setType(GameSquare.TYPE_BLACK);
		squares[7][6].setType(GameSquare.TYPE_BLACK);
		
		squares[0][0].setType(GameSquare.TYPE_WHITE);
		squares[0][2].setType(GameSquare.TYPE_WHITE);
		squares[0][4].setType(GameSquare.TYPE_WHITE);
		squares[0][6].setType(GameSquare.TYPE_WHITE);
		squares[1][1].setType(GameSquare.TYPE_WHITE);
		squares[1][3].setType(GameSquare.TYPE_WHITE);
		squares[1][5].setType(GameSquare.TYPE_WHITE);
		squares[1][7].setType(GameSquare.TYPE_WHITE);
		squares[2][0].setType(GameSquare.TYPE_WHITE);
		squares[2][2].setType(GameSquare.TYPE_WHITE);
		squares[2][4].setType(GameSquare.TYPE_WHITE);
		squares[2][6].setType(GameSquare.TYPE_WHITE);
		squares[3][1].setType(GameSquare.TYPE_WHITE);
		squares[3][3].setType(GameSquare.TYPE_WHITE);
		squares[3][5].setType(GameSquare.TYPE_WHITE);
		squares[3][7].setType(GameSquare.TYPE_WHITE);
		squares[4][0].setType(GameSquare.TYPE_WHITE);
		squares[4][2].setType(GameSquare.TYPE_WHITE);
		squares[4][4].setType(GameSquare.TYPE_WHITE);
		squares[4][6].setType(GameSquare.TYPE_WHITE);
		squares[5][1].setType(GameSquare.TYPE_WHITE);
		squares[5][3].setType(GameSquare.TYPE_WHITE);
		squares[5][5].setType(GameSquare.TYPE_WHITE);
		squares[5][7].setType(GameSquare.TYPE_WHITE);
		squares[6][0].setType(GameSquare.TYPE_WHITE);
		squares[6][2].setType(GameSquare.TYPE_WHITE);
		squares[6][4].setType(GameSquare.TYPE_WHITE);
		squares[6][6].setType(GameSquare.TYPE_WHITE);
		squares[7][1].setType(GameSquare.TYPE_WHITE);
		squares[7][3].setType(GameSquare.TYPE_WHITE);
		squares[7][5].setType(GameSquare.TYPE_WHITE);
		squares[7][7].setType(GameSquare.TYPE_WHITE);
	}

	// reset will start the game over
	public void reset()
	{
		// clear every square on the game board
		for (int row = 0; row < NUM_ROWS; row++)
		{
			for (int col = 0; col < NUM_COLS; col++)
			{
				// remove any piece and yellow selection border
				squares[row][col].clearSquare();
				squares[row][col].deselect();
			}
		}
	}

	// add the game piece to the GameSquare at the specified column and row
	public void setPiece(int row, int col, AbstractGamePiece piece)
	{
		// if we are given a good column and row (0-7)
		if ((row >= 0) && (row < NUM_ROWS) &&
			(col >= 0) && (col < NUM_COLS))
		{
			squares[row][col].setPiece(piece);
		}
	}
	
	// find which game square, if any, matches the click event object (button)
	public GameSquare getClickedSquare(Object source)
	{
		// check every game square in the array
		for (int row = 0; row < NUM_ROWS; row++)
		{
			for (int col = 0; col < NUM_COLS; col++)
			{
				GameSquare square = squares[row][col];
				
				// if this square was the one that was clicked
				if (square.isClicked(source))
				{	
					return square;	// return the clicked square!
				}
			}
		}
		
		return null;	// could not find clicked square
	}	
	
	// return the game piece at the indicated row and column
	AbstractGamePiece getPiece(int row, int col)
	{
		// if we are given a good column and row (0-7)
		if ((row >= 0) && (row < NUM_ROWS) &&
			(col >= 0) && (col < NUM_COLS))
		{
			// return the piece that is on the valid square
			return squares[row][col].getPiece();
		}
		
		return null;	// invalid row or column provided
	}

	// remove the indicated piece from the game board
	public void removePiece(AbstractGamePiece piece)
	{
		// get the row and column of the game piece
		int row = piece.getRow();
		int col = piece.getCol();
		
		// get the game square this piece is sitting on
		GameSquare square = squares[row][col];
		
		// clear the square!
		square.clearSquare();
	}
	
	// build a list of GameSquare objects from the starting square to the targetSquare
	public List<GameSquare> buildPath(GameSquare startingSquare, GameSquare targetSquare)
	{
		// create new ArrayList that we will return.  Initially the path is empty
		List<GameSquare> path = new ArrayList<GameSquare>();
		
		// get starting and ending row and column
		int currentRow = startingSquare.getRow();
		int currentCol = startingSquare.getCol();
		
		int endRow = targetSquare.getRow();
		int endCol = targetSquare.getCol();
		
        // if destination is the same as the origin
        if ( (currentRow == endRow) && (currentCol == endCol))
            return path;       // return the empty path...not valid

        // if both destination row and column are different, cannot make straight line to target
        if ((currentRow != endRow) && (currentCol != endCol) )
            return path;       // return the empty path...not valid

        // if we are moving down
        if ((currentCol == endCol) && (currentRow < endRow))
        {
            // while we haven't passed by our target square yet
            while (currentRow < endRow)
            {
            	currentRow++; // move one square down
                path.add(squares[currentRow][currentCol]);
            }
        }
        // if we are moving up
        else if ((currentCol == endCol) && (currentRow > endRow))
        {
            // while we haven't passed by our target square yet
            while (currentRow > endRow)
            {
            	currentRow--; // move one square up
                path.add(squares[currentRow][currentCol]);
            }
        }
        // if we are moving left
        else if ((currentCol > endCol) && (currentRow == endRow))
        {
            // while we haven't passed by our target square yet
            while (currentCol > endCol)
            {
            	currentCol--; // move one square left
                path.add(squares[currentRow][currentCol]);
            }
        }
        // if we are moving right
        else if ((currentCol < endCol) && (currentRow == endRow))
        {
            // while we haven't passed by our target square yet
            while (currentCol < endCol)
            {
            	currentCol++; // move one square right
                path.add(squares[currentRow][currentCol]);
            }
        }

        // now return whatever we found to the calling function
        return path;
	}
}