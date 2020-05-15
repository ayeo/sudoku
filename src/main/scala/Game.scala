package pl.ayeo.sudoku

import scalafx.application.JFXApp
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.control.Button
import scalafx.scene.layout.{BorderPane, GridPane, VBox}
import scalafx.scene.Scene
import scalafx.Includes._

object Game extends JFXApp {
  val mainPane: BorderPane = new BorderPane()
  val scene: Scene = new Scene(mainPane, 400, 400)
  scene.getStylesheets.add("styles.css")
  val loader = new Loader(Levels.SIMPLE)
  val builder = new BoardPrinter(40, 1)
  mainPane.setCenter(builder.getPane())

  val solve = new Button()
  solve.setText("Solve")

  val newGame = new Button()
  newGame.setText("New game")
  newGame.onAction = (event: ActionEvent) => loadNewBoard()

  val xxxx = new VBox();
  xxxx.getChildren().add(solve)
  xxxx.getChildren().add(newGame)
  mainPane.setRight(xxxx)

  def loadNewBoard(): Unit = {
    val board = loader.loadRandomPuzzle.getOrElse(throw new RuntimeException)
    //val board = Domain.fromString("123456789 000000000 123456789 000000000 111111111 000000000 000000000 000000000 000000000", 9)
    //val userInput = Domain.fromString("000000000 888999222 000000000 000000000 000000000 000000000 000000000 000000000 000000000", 9)
    val userInput = Domain.fromString("000000000 000000000 000000000 000000000 000000000 000000000 000000000 000000000 000000000", 9)
    val state = State(board, userInput)
    builder.printBoard(state)

    solve.onAction = solveBoard(state)
  }

  def solveBoard(state: State)(event: ActionEvent): Unit = {
    val solver = Solver
    val solution = solver.solve(state.given)

    builder.printBoard(state.solve(solution.get))
  }

  mainPane.padding = Insets(10, 10, 10, 10)
  loadNewBoard()


  stage = new JFXApp.PrimaryStage
  stage.setScene(scene)
  stage.show()
}
