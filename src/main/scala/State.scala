package pl.ayeo.sudoku

import Domain.Board

case class State(given: Board, user: Board, solution: Board, showErrors: Boolean = false,focus: (Int, Int) = (0, 0)) {
  val size = 9

  if (!check(given, user)) {
    throw new RuntimeException("Invalid state")
  }

  private def check(givenValues: Board, userValues: Board): Boolean = {
    givenValues.flatten.zip(userValues.flatten).map {
      case (0, 0) => 0
      case (0, i) if i > 0 => 0
      case (i, 0) if i > 0 => 0
      case _ => 1
    }.sum == 0
  }

  def placeNumber(number: Int): State = {
    val newUser: Board = this.user.updated(focus._2, this.user(focus._2).updated(focus._1, number))
    State(given, newUser, solution, showErrors, focus)
  }

  def solve: State = {
    val filteredSolution = solution.flatten.zip(given.flatten).map {
      case (i, 0) if i > 0 => i
      case _ => 0
    }.sliding(size, size).toVector

    State(given, filteredSolution, solution, showErrors, focus)
  }

  def hint: State = placeNumber(solution(focus._2)(focus._1))


  def setFocus(row: Int, col: Int): State = State(given, user, solution, showErrors, (row, col))

  def showErrors(show: Boolean): State = State(given, user, solution, show, focus)

  def emptyCells(): Board = {
    given.flatten.zip(user.flatten).map {
      case (0, 0) => 1
      case _ => 0
    }.sliding(9, 9).toVector
  }
}
