import scala.util.Try

import models._

trait PlayerInteraction {
  def getPlayerMove(player: GamePlayer): Either[GameError, PlayerMove]
  def showBoardToPlayer(player: GamePlayer, board: GameBoard): Unit
  def showErrorToPlayer(player: GamePlayer, error: GameError)
  def showEndState(gameComplete: GameComplete)
}

object SimplePlayerInteraction extends PlayerInteraction {
  def getPlayerMove(player: GamePlayer): Either[GameError, PlayerMove] = {
    println(s"Player to take turn: ${player}")
    val userInput = scala.io.StdIn.readLine()

    Try(userInput.toInt).toEither
      .left.map(_ => BadPlayerInput)
      .map(column => PlayerMove(player, column))
  }

  def showBoardToPlayer(player: GamePlayer, board: GameBoard) ={
    Renderer.printGame(board)
  }

  def showErrorToPlayer(player: GamePlayer, error: GameError): Unit = {
    println(s"Got error: ${error}")
  }

  def showEndState(gameComplete: GameComplete): Unit = {
    gameComplete match {
      case PlayersDraw => println("Players draw")
      case GameWon(player) => println(s"Game won by player: ${player}")
    }
  }
}