import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// The Chess class implements ActionListener to receive button click events
public class Chess implements ActionListener{
	// Declare class member variables to keep track of the UI controls on the JFrame
	private JFrame myFrame = null;
	private JPanel mainPanel = null;
	private JLabel playerTurnLabel = null;
	private JButton resetButton = null;
	
	// This member will contain the main game board object
	private GameBoard gameBoard = null;
	
	// This member will track which square (if any) is currently selected
	private GameSquare selectedSquare = null;
		
	// This flag will be set to true when the game is over, or should be false otherwise
	private boolean gameOver = false;

	// AbstractGamePiece.PLAYER_Black or AbstractGamePiece.PLAYER_White
	private int currentPlayerTurn;	
		
	// This member will hold a reference to the king so we can check it later
	private AbstractGamePiece kingPiece = null;

	// The main method just creates a new Chess object to run the game
	public static void main(String[] args)
	{
		new Chess();
	}
	
	// The Chess constructor will build all of the GUI elements, initialize the game board,
	// and reset the game to its initial state
	public Chess()
	{
		// Create new JFrame and set the title. 
		myFrame = new JFrame();
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setTitle("Chess");
		myFrame.setResizable(false);	// don't let the user resize the board
		
		// The main panel will contain a vertical box layout 
		mainPanel = (JPanel)myFrame.getContentPane();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		
		// The top panel just contains the player turn label
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		playerTurnLabel = new JLabel();
		topPanel.add(playerTurnLabel);
		mainPanel.add(topPanel);
		
		// The middle panel contains the game board.  The GameBoard constructor
		// will build out all of the panel details, including all of the squares!
		JPanel boardPanel = new JPanel();
		gameBoard = new GameBoard(boardPanel,this);
		mainPanel.add(boardPanel);
		
		// The bottom panel will contain the reset button.
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		resetButton = new JButton("Reset");
		resetButton.addActionListener(this);
		bottomPanel.add(resetButton);
		mainPanel.add(bottomPanel);
		
		// reset the game to it's initial state
		reset();
		
		// we're ready, so pack and show the screen!
		myFrame.pack();
		myFrame.setVisible(true);
	}
	
	// This method is called when the game is first created, and any
	// time you want to restart a new game.
	private void reset()
	{
		gameOver = false;	// game is not over
		gameBoard.reset();	// reset the game board to the initial setup
		
		// create new King, remember the object, and place it on the center square
		kingPiece = new BlackKing();
		gameBoard.setPiece(0,4,kingPiece);
		
		kingPiece = new WhiteKing();
		gameBoard.setPiece(7,4, kingPiece);
		
		// create new Black pieces on the top squares
		gameBoard.setPiece(0,0,new BlackRook());
		gameBoard.setPiece(0,1,new BlackKnight());
		gameBoard.setPiece(0,2,new BlackBishop());
		gameBoard.setPiece(0,3,new BlackQueen());
		gameBoard.setPiece(0,5,new BlackBishop());
		gameBoard.setPiece(0,6,new BlackKnight());
		gameBoard.setPiece(0,7,new BlackRook());
		gameBoard.setPiece(1,0,new BlackPawn());
		gameBoard.setPiece(1,1,new BlackPawn());
		gameBoard.setPiece(1,2,new BlackPawn());
		gameBoard.setPiece(1,3,new BlackPawn());
		gameBoard.setPiece(1,4,new BlackPawn());
		gameBoard.setPiece(1,5,new BlackPawn());
		gameBoard.setPiece(1,6,new BlackPawn());
		gameBoard.setPiece(1,7,new BlackPawn());
		
		// create new White pieces on the bottom squares
		gameBoard.setPiece(6,0,new WhitePawn());
		gameBoard.setPiece(6,1,new WhitePawn());
		gameBoard.setPiece(6,2,new WhitePawn());
		gameBoard.setPiece(6,3,new WhitePawn());
		gameBoard.setPiece(6,4,new WhitePawn());
		gameBoard.setPiece(6,5,new WhitePawn());
		gameBoard.setPiece(6,6,new WhitePawn());
		gameBoard.setPiece(6,7,new WhitePawn());
		gameBoard.setPiece(7,0,new WhiteRook());
		gameBoard.setPiece(7,1,new WhiteKnight());
		gameBoard.setPiece(7,2,new WhiteBishop());
		gameBoard.setPiece(7,3,new WhiteQueen());
		gameBoard.setPiece(7,5,new WhiteBishop());
		gameBoard.setPiece(7,6,new WhiteKnight());
		gameBoard.setPiece(7,7,new WhiteRook());
		
		
		// reset the current player turn to Black
		currentPlayerTurn = AbstractGamePiece.PLAYER_WHITE;
		
		setPlayerTurnLabel();	// show the current player turn
	}
	// switch the current player's turn
	private void changePlayerTurn()
	{
		if (currentPlayerTurn == AbstractGamePiece.PLAYER_WHITE)
			currentPlayerTurn = AbstractGamePiece.PLAYER_BLACK;
		else
			currentPlayerTurn = AbstractGamePiece.PLAYER_WHITE;
	}
	
	
	// This method is provided as part of the activity starter.  No student modification is needed.
	// change the playerTurnLabel to reflect the current player's turn
	private void setPlayerTurnLabel()
	{
		if (currentPlayerTurn == AbstractGamePiece.PLAYER_WHITE)
			playerTurnLabel.setText("Player Turn: White!");
		else
			playerTurnLabel.setText("Player Turn: Black!");
	}
	
	// This method is provided as part of the activity starter.  No student modification is needed.
	// Implement the ActionListener interface.  Button clicks from the game board
	// buttons will be received by this method.
	public void actionPerformed(ActionEvent source)
	{
		// if the reset button was clicked
		if (source.getSource() == resetButton)
		{
			reset();	// reset the game
		}
		else if (!gameOver)	// else if the game is not over
		{
			// figure out which square, if any, on the game board was clicked
			GameSquare clickedSquare = gameBoard.getClickedSquare(source.getSource());
			if (clickedSquare != null)
			{
				// pass the clicked square to the main game logic function
				handleClickedSquare(clickedSquare);
				
				// update the current player's turn in case it was changed by the game logic
				setPlayerTurnLabel();
			}
			
		}
	}
	
	// This method is defined but not implemented as part of the activity starter.
	// If this method is called, the clickedSqure parameter will not be null!
	// STUDENT SHOULD COMPLETE THIS ENTIRE METHOD!
	private void handleClickedSquare(GameSquare clickedSquare)
	{
		// If there is no square currently selected
		if (selectedSquare == null)
		{
			// if there is a piece on the square we clicked
			if (clickedSquare.getPiece() != null)
			{
				// and that piece belongs to the current player
				if (clickedSquare.getPiece().getPlayerType() == currentPlayerTurn)
				{
					// this square is now selected.  Store it in the selectedSquare
					// member variable and call the select() method to display the 
					// yellow border.
					selectedSquare = clickedSquare;
					selectedSquare.select();
				}
			}
		}
		// if the clicked square is the same as the square already selected
		else if (clickedSquare == selectedSquare)
		{
			// that is a signal to deselect the current square...
			selectedSquare.deselect();	// remove yellow border
			selectedSquare = null;		// forget about current selected square
		}
		else
		{
			// if we get here, then we have clicked on a target square with a piece
			// already selected, so we are attempting to move a piece!
			
			// get the piece that is on the currently selected square
			AbstractGamePiece selectedPiece = selectedSquare.getPiece();
			
			// build a list of squares between selected piece's current location and the clicked square
			List<GameSquare> path = gameBoard.buildPath(selectedSquare,clickedSquare);
			
			// let the piece determine if it can safely follow the path to the target square
			if (selectedPiece.canMoveToLocation(path))
			{
				// this is a valid move, so move the game piece to the clicked square
				clickedSquare.setPiece(selectedPiece);
				
				// remove the game piece from the selected square
				selectedSquare.clearSquare();
				selectedSquare.deselect();	// remove the yellow border
				selectedSquare = null;		// forget about the current selected square
				
				// assume the king has not been captured by this move
				boolean kingCaptured = false;
				
				// now get a list of any captured opponent pieces in up/down/left/right squares
				List<AbstractGamePiece> capturedPieces = findCapturedOpponents(selectedPiece.getRow(), selectedPiece.getCol());
				
				// for each captured opponent
				for (AbstractGamePiece capturedPiece : capturedPieces)
				{
					// let everyone know which piece has been captured
					JOptionPane.showMessageDialog(myFrame, capturedPiece.toString() + " was captured!");
					
					// remove the captured piece from the game board
					gameBoard.removePiece(capturedPiece);
					
					// check to see if the captured piece was the king piece
					if (capturedPiece == kingPiece)
					{
						kingCaptured = true;	// the king has been captured!
					}
					
				}
				
				// check the two game over conditions...
				
				if (kingCaptured)	// if the king has been captured
				{
					// mark the game as over and show a message
					gameOver = true;
					JOptionPane.showMessageDialog(myFrame,"Game Over, the King has been captured!");
				}
				else
				{
					// game not over yet, so next player's turn
					changePlayerTurn();
				}
			}
		}
		
	}
	
	// This method will return a list of captured opponents if a piece has moved to the indicated row and column.
	private List<AbstractGamePiece> findCapturedOpponents(int row, int col)
	{
		// initialize an ArrayList that will hold any captured opponents
		ArrayList<AbstractGamePiece> capturedOpponents = new ArrayList<AbstractGamePiece>();
		
		// we are going to check each square up/down/left/right to see if there is a piece present.
		// If so, let the piece itself determine if it has been captured or not!
		AbstractGamePiece nearbyPiece = null;
		
		// get above piece
		nearbyPiece = gameBoard.getPiece(row - 1, col);	
		// if this piece exists and has been captured 
		if ((nearbyPiece != null) && (nearbyPiece.isCaptured(gameBoard)))
		{
			capturedOpponents.add(nearbyPiece);	// add captured piece to the list
		}
		
		// get below piece
		nearbyPiece = gameBoard.getPiece(row + 1, col);	
		// if this piece exists and has been captured 
		if ((nearbyPiece != null) && (nearbyPiece.isCaptured(gameBoard)))
		{
			capturedOpponents.add(nearbyPiece);	// add captured piece to the list
		}
		
		// get left piece
		nearbyPiece = gameBoard.getPiece(row, col - 1);	
		// if this piece exists and has been captured 
		if ((nearbyPiece != null) && (nearbyPiece.isCaptured(gameBoard)))
		{
			capturedOpponents.add(nearbyPiece);	// add captured piece to the list
		}
		
		// get right piece
		nearbyPiece = gameBoard.getPiece(row, col + 1);	
		// if this piece exists and has been captured 
		if ((nearbyPiece != null) && (nearbyPiece.isCaptured(gameBoard)))
		{
			capturedOpponents.add(nearbyPiece);	// add captured piece to the list
		}
		
		// return the list of captured opponents (may be empty!)
		return capturedOpponents;
	}
}
	
