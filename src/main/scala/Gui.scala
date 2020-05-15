package pl.ayeo.sudoku

import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.control.{Button, CheckBox}
import scalafx.scene.layout.{BorderPane, VBox}

class Gui(boardPrinter: BoardPrinter) {
  val mainPane: BorderPane = new BorderPane()
  mainPane.setCenter(boardPrinter.getPane())
  mainPane.padding = Insets(10, 10, 10, 10)

  val solve = new Button()
  solve.setText("Solve")

  val newGame = new Button()
  newGame.setText("New game")

  val hint = new Button()
  hint.setText("Hint")

  val showErrors = new CheckBox("Show errors");

  val buttonsPane = new VBox();

  buttonsPane.getChildren().add(newGame)
  buttonsPane.getChildren().add(hint)
  buttonsPane.getChildren().add(solve)
  buttonsPane.getChildren().add(showErrors);
  mainPane.setRight(buttonsPane)

  loadNewBoard()

  def getPane(): BorderPane = mainPane

  def loadNewBoard(): Unit = {
    val loader = new Loader(Levels.SIMPLE)
    val board = loader.loadRandomPuzzle.getOrElse(throw new RuntimeException)
    val userInput = Domain.empty(9)
    val solution = Solver.solve(board)
    val state = State(board, userInput, solution.get, showErrors.isSelected)
    print(state)
  }

  def toggleErrors(state: State)(event: ActionEvent): Unit = print(state.showErrors(showErrors.isSelected))
  def solveBoard(state: State)(event: ActionEvent): Unit = print(state.solve)
  def showHint(state: State)(event: ActionEvent): Unit = print(state.hint)

  def print(state: State): Unit = {
    solve.onAction = solveBoard(state)
    showErrors.onAction = toggleErrors(state)
    newGame.onAction = (event: ActionEvent) => loadNewBoard()
    hint.onAction = showHint(state)
    boardPrinter.printBoard(state, this)
  }
}
