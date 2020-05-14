import org.scalatest.FunSuite
import pl.ayeo.sudoku.Domain.Board
import pl.ayeo.sudoku.{Domain, State}

class StateTest extends FunSuite {
  test("Test if given and user values coordinated does not overlap") {
    val givenValues: Board = Domain.fromString("800 000 404", 3)
    val userValues: Board = Domain.fromString("001 000 100", 3)
    intercept[RuntimeException] (State(givenValues, userValues))
  }

  test("Valid state") {
    val givenValues: Board = Domain.fromString("800 000 404", 3)
    val userValues: Board = Domain.fromString("001 000 010", 3)
    State(givenValues, userValues)
    assert(true)
  }
}