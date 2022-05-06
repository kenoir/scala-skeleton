import models._
import org.scalatest.EitherValues
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers._

class ExampleAppTest extends AnyFunSpec with EitherValues {

  val boardSpaces = Map(
    (1,3) -> None, (2,3) -> None, (3,3) -> None,
    (1,2) -> None, (2,2) -> None, (3,2) -> None,
    (1,1) -> None, (2,1) -> None, (3,1) -> None,
  )

  val emptyGameBoard: GameBoard =
    GameBoard(3,3, boardSpaces)

  val player = RedPlayer

  val playerMove = PlayerMove(player, 1)

  describe("Rules") {
    describe("updateGameBoard"){
      it("should update the correct column") {
        println("Empty board:")
        Renderer.printGame(emptyGameBoard)
        val gameUpdate = Rules.updateGameBoard(emptyGameBoard, playerMove)

        val expectedBoardSpaces = GameBoard(3,3, Map[(Int, Int), Option[BoardPiece]](
          (1,3) -> None, (2,3) -> None, (3,3) -> None,
          (1,2) -> None, (2,2) -> None, (3,2) -> None,
          (1,1) -> Some(RedToken), (2,1) -> None, (3,1) -> None,
        ))

        println("Expected board:")
        Renderer.printGame(expectedBoardSpaces)

        println("Actual board:")
        Renderer.printGame(gameUpdate.value)

        gameUpdate.value shouldBe expectedBoardSpaces
      }
    }
  }
}
