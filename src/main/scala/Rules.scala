import models._

object Rules {
  val defaultWidth = 6
  val defaultHeight = 7

  val defaultStartingGameBoard: GameBoard = {
    val startingSpaces = (1 to defaultWidth).flatMap { x =>
      (1 to defaultHeight).map { y =>
        (x, y) -> None
      }
    }.toMap

    GameBoard(
      height = defaultHeight,
      width = defaultWidth,
      boardSpaces = startingSpaces
    )
  }

  val defaultStartingGameState: InProgressGame = InProgressGame(
    board = defaultStartingGameBoard,
    currentPlayer = RedPlayer
  )

  def nextPlayer(player: GamePlayer): GamePlayer = player match {
    case RedPlayer => BluePlayer
    case BluePlayer => RedPlayer
  }

  def updateGameBoard(gameBoard: GameBoard, playerMove: PlayerMove): Either[GameError, GameBoard] = {
    // Collect only interesting column from playerMove
    val boardCells = gameBoard.boardSpaces.collect {
      case ((row, playerMove.column), piece) => row -> piece
    }

    // Find the highest valued empty row if it exist
    val lowestValueEmptyRow = boardCells.foldLeft[Option[Int]](None) {
      // No previous lowest, current row is empty - return current row
      case (None, (row, None)) => Some(row)

      // No previous lowest, current row is not empty - return None
      case (None, (_, Some(_))) => None

      // Previous lowest found, current row is empty - return highest row
      case (last@Some(lastLowest), (row, None)) => if(row < lastLowest) Some(row) else last

      // Previous lowest found, current row is not empty - return last row
      case (last@Some(_), (_, Some(_))) => last
    }

    val boardCellToUpdate = lowestValueEmptyRow.map(x => (x,playerMove.column))

    boardCellToUpdate.map { location =>
      val pieceToPlace = Some(playerMove.player match {
        case RedPlayer => RedToken
        case BluePlayer => BlueToken
      })

      gameBoard.copy(
        boardSpaces = gameBoard.boardSpaces.updated(
          key = location,
          value = pieceToPlace
        )
      )
    } match {
      case Some(gameBoard) => Right(gameBoard)
      case None => Left(InvalidBoardPlacement)
    }
  }

  def assessGameProgress(gameBoard: GameBoard): GameProgress = {
    GameUnfinished
  }

  def updateGameState(playerInteraction: PlayerInteraction)(gameState: InProgressGame): Either[GameError, GameState] = {
    for {
      playerMove <- playerInteraction.getPlayerMove(gameState.currentPlayer)
      gameBoard <- updateGameBoard(gameState.board, playerMove)
    } yield {
      assessGameProgress(gameBoard) match {
        case GameUnfinished => InProgressGame(gameBoard, nextPlayer(gameState.currentPlayer))
        case completedGame: GameComplete => CompletedGame(gameBoard, completedGame)
      }
    }
  }
}