import models._

class GameEngine(playerInteraction: PlayerInteraction) {
  val gameStateUpdater = Rules.updateGameState(playerInteraction) _

  def play(gameState: GameState): GameState = gameState match {
    case gameState@CompletedGame(_,endState) => {
      playerInteraction.showEndState(endState)
      gameState
    }
    case gameState@InProgressGame(board, currentPlayer) => {
      playerInteraction.showBoardToPlayer(currentPlayer, board)

      gameStateUpdater(gameState) match {
        case Right(newGameState) => play(newGameState)
        case Left(error) => {
          playerInteraction.showErrorToPlayer(currentPlayer, error)
          play(gameState)
        }
      }
    }
  }
}