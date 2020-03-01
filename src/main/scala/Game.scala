package pl.ayeo.sudoku

import scalafx.application.JFXApp
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.control.Button
import scalafx.scene.layout.BorderPane
import scalafx.scene.Scene
import scalafx.Includes._

object Game extends JFXApp {
  val mainPane: BorderPane = new BorderPane()
  val scene: Scene = new Scene(mainPane, 400, 400)

  val loader = new Loader(Levels.EASY)
  val board: Domain.Board = loader.loadRandomPuzzle

  mainPane.padding = Insets(10, 10, 10, 10)
  mainPane.setCenter(BoardBuilder.buildBoard(board, 40, 1))

  val newGame = new Button()
  newGame.setText("New game")
  mainPane.setRight(newGame)
  newGame.onAction = (event: ActionEvent) => {
    val board: Domain.Board = loader.loadRandomPuzzle
    mainPane.setCenter(BoardBuilder.buildBoard(board, 40, 1))
  }

  stage = new JFXApp.PrimaryStage
  stage.setScene(scene)
  stage.show()
}
