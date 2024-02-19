public class WhiteKing extends AbstractGamePiece
{
	// the Henchman constructor
	public WhiteKing()
	{
		// initialize the AbstractGamePiece constructor with required data
		super("White King","WK",AbstractGamePiece.PLAYER_WHITE);
	}

	// override the base class method isCaptured()
	public boolean isCaptured(GameBoard gameBoard)
	{
		// Note:  the GameBoard.getPiece() method will return null if we pass in
		// a column or index value that is off the game board (< 0 or >= 8).
		
		// get a reference to the pieces (if any) on all 4 sides of my location
		AbstractGamePiece nearbyPiece1 = gameBoard.getPiece(myRow - 1, myCol);	// get above piece
		AbstractGamePiece nearbyPiece2 = gameBoard.getPiece(myRow + 1, myCol);	// get below piece
		AbstractGamePiece nearbyPiece3 = gameBoard.getPiece(myRow, myCol - 1);	// get left piece
		AbstractGamePiece nearbyPiece4 = gameBoard.getPiece(myRow, myCol + 1);	// get right piece
		
		// if all pieces exist and belong to the opposing player
		if ((nearbyPiece1 != null) && (nearbyPiece1.getPlayerType() != myPlayerType) &&
				(nearbyPiece2 != null) && (nearbyPiece2.getPlayerType() != myPlayerType) &&
				(nearbyPiece3 != null) && (nearbyPiece3.getPlayerType() != myPlayerType) &&
				(nearbyPiece4 != null) && (nearbyPiece4.getPlayerType() != myPlayerType) ) 
		{
			return true;	// bad news, the Kingpin has been captured!
		}
		
		return false;	// not captured yet!
	}	

}