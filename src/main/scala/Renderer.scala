import models._

object Renderer {
  def printBoardPiece(piece: Option[BoardPiece]) = piece match {
    case Some(RedToken) => "R"
    case Some(BlueToken) => "B"
    case None => "0"
  }

  def printGame(board: GameBoard) = {
    (1 to board.height).reverse.foreach { y =>
      (1 to board.width).foreach { x =>
        val location = (x,y)
        print(
          printBoardPiece(board.boardSpaces.get(location).flatten)
        )
      }
      println()
    }
    println()
  }
}