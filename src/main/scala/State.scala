package pl.ayeo.sudoku

import Domain.Board

case class State(given: Board, user: Board) {
  if (!check(given, user)) {
    throw new RuntimeException("Invalid state")
  }

  private def check(givenValues: Board, userValues: Board): Boolean = {
    val x = givenValues.flatten.zip(userValues.flatten)
    x.map {
      case (0, 0) => 0
      case (0, i) if i > 0 => 0
      case (i, 0) if i > 0 => 0
      case _ => 1
    }.sum == 0
  }
}
