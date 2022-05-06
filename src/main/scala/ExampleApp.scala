object ExampleApp extends App {
  println("Connect 4!")

  val startingGameState = Rules.defaultStartingGameState
  val connect4Game = new GameEngine(SimplePlayerInteraction)

  connect4Game.play(startingGameState)

  println("Bye!")
}