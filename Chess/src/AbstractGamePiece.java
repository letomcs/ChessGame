import java.util.*;

abstract public class AbstractGamePiece
{	
	// These two constants define the Black and White side
	static public final int PLAYER_BLACK = 0;
	static public final int PLAYER_WHITE = 1;

	// These variables hold the piece's row and column index
	protected int myRow;
	protected int myCol;
	
	// This variable indicates which team the piece belongs to
	protected int myPlayerType;
	
	// These two strings contain the piece's full name and first letter abbreviation
	private String myAbbreviation;
	private String myName;

	// STUDENT SHOULD ADD AND IMPLEMENT THIS METHOD
	abstract protected boolean isSquareRestricted(GameSquare step);
	
	// The student should complete this constructor by initializing the member
	// variables with the provided data.
	public AbstractGamePiece(String name, String abbreviation, int playerType)
	{
		// STUDENT CODE GOES HERE
		myName = name;
		myAbbreviation = abbreviation;
		myPlayerType = playerType;
		
	}

	// STUDENT SHOULD ADD AND IMPLEMENT THIS METHOD
	public int getPlayerType() 
	{
		return myPlayerType;
	}
	
	// STUDENT SHOULD ADD AND IMPLEMENT THIS METHOD
	public void setPosition(int row, int col)
	{
		myRow = row;
		myCol = col;
	}
	
	// STUDENT SHOULD ADD AND IMPLEMENT THIS METHOD
	public int getRow() 
	{ 
		return myRow; 
	}
	
	// STUDENT SHOULD ADD AND IMPLEMENT THIS METHOD
	public int getCol() 
	{ 
		return myCol; 
	}

	// STUDENT SHOULD ADD AND IMPLEMENT THIS METHOD
	public String getAbbreviation()
	{
		return myAbbreviation;
	}
	
	// STUDENT SHOULD ADD AND IMPLEMENT THIS METHOD
	public String toString()
	{
	   if (myPlayerType == PLAYER_BLACK)
	       return "Black " + myName + " at (" + myRow + "," + myCol + ")";
	   else
	       return "White " + myName + " at (" + myRow + "," + myCol + ")";
	}

	// STUDENT SHOULD ADD AND IMPLEMENT THIS METHOD
	public boolean canMoveToLocation(List<GameSquare> path)
	{
		// first, if it's an empty path, this move is not valid, so return false
		if (path.size() == 0)
			return false;
		
		// now examine each square in the path, step-by-step
		for (GameSquare step : path)
		{
			// check to see if this step is a restricted zone for this piece
			if (isSquareRestricted(step))
				return false;	// invalid move

			// if there is any other piece on this step, then we cannot make the move
			if (step.getPiece() != null)
				return false;	// invalid move
		}
		
		// ok, we walked the whole path without encountering another piece
		// or a restricted square, so it's a valid move!
		return true;
	}
	

	// STUDENT SHOULD ADD AND IMPLEMENT THIS METHOD
	public boolean isCaptured(GameBoard gameBoard)
	{
		// declare some local variables to hold two possible adjacent pieces
		AbstractGamePiece nearbyPiece1 = null;
		AbstractGamePiece nearbyPiece2 = null;

		// Note:  the GameBoard.getPiece() method will return null if we pass in
		// a column or index value that is off the game board (< 0 or >= 7).
		
		// get a reference to the pieces (if any) above and below my location
		nearbyPiece1 = gameBoard.getPiece(myRow - 1,myCol);	// get above piece
		nearbyPiece2 = gameBoard.getPiece(myRow + 1,myCol);	// get below piece

		// if the top piece is not null and belongs to the other player, AND
		// if the bottom piece is not null and belongs to the other player
		if ((nearbyPiece1 != null) && (nearbyPiece1.getPlayerType() != myPlayerType) &&
		    (nearbyPiece2 != null) && (nearbyPiece2.getPlayerType() != myPlayerType) )
	    {
	    	return true;	// yikes, I've been captured!
	    }
		
		// get a reference to the pieces (if any) left and right of location
		nearbyPiece1 = gameBoard.getPiece(myRow, myCol - 1);	// get left piece
		nearbyPiece2 = gameBoard.getPiece(myRow, myCol + 1);	// get right piece

		// if the left piece is not null and belongs to the other player, AND
		// if the right piece is not null and belongs to the other player
		if ((nearbyPiece1 != null) && (nearbyPiece1.getPlayerType() != myPlayerType) &&
		    (nearbyPiece2 != null) && (nearbyPiece2.getPlayerType() != myPlayerType) )
	    {
	    	return true;	// yikes, I've been captured!
	    }
		
		return false; 	// whew, I'm safe...for now!
	}

	
}
