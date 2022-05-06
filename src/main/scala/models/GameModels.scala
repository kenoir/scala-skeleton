package models

case class GameBoard(height: Int, width: Int, boardSpaces: Map[(Int, Int), Option[BoardPiece]])

sealed trait BoardPiece
case object RedToken extends BoardPiece
case object BlueToken extends BoardPiece

sealed trait GamePlayer
case object RedPlayer extends GamePlayer
case object BluePlayer extends GamePlayer

sealed trait GameProgress
case object GameUnfinished extends GameProgress

sealed trait GameComplete extends GameProgress
case object PlayersDraw extends GameComplete
case class GameWon(gamePlayer: GamePlayer) extends GameComplete

sealed trait GameState {
  val board: GameBoard
  val gameProgress: GameProgress
}
case class InProgressGame(board: GameBoard, currentPlayer: GamePlayer) extends GameState {
  val gameProgress = GameUnfinished
}
case class CompletedGame(board: GameBoard, gameProgress: GameComplete) extends GameState

case class PlayerMove(player: GamePlayer, column: Int)

sealed trait GameError
case object BadPlayerInput extends GameError
case object InvalidBoardPlacement extends GameError