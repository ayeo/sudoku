import scalafx.application.JFXApp

import scala.collection.mutable.Seq
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.{BorderPane, GridPane, HBox}
import scalafx.Includes._
import scalafx.scene.input.KeyEvent

object Gui extends JFXApp {
  val cells = Array.ofDim[Button](9, 9)
  val board: Seq[Seq[Int]] = Seq(
    Seq(8, 0, 0, 0, 0, 7, 0, 9, 0),
    Seq(0, 7, 0, 0, 2, 0, 0, 0, 8),
    Seq(0, 0, 9, 6, 0, 0, 5, 0, 0),
    Seq(0, 0, 5, 3, 0, 0, 9, 0, 0),
    Seq(0, 1, 0, 0, 8, 0, 0, 0, 2),
    Seq(6, 0, 0, 0, 0, 4, 0, 0, 0),
    Seq(3, 0, 0, 0, 0, 0, 0, 1, 0),
    Seq(0, 4, 0, 0, 0, 0, 0, 0, 7),
    Seq(0, 0, 7, 0, 0, 0, 3, 0, 0)
  )

  val mainPane: BorderPane = new BorderPane()
  val scene: Scene = new Scene(mainPane, 500, 500)
  val pane: GridPane = new GridPane()
  //pane.setStyle("-fx-background-color: black;")
  mainPane.setCenter(pane)

  val menu = new HBox()
  mainPane.setBottom(menu)

  val clearButton = new Button()
  clearButton.setText("Clear")
  menu.children.add(clearButton)
  clearButton.onAction = (event: ActionEvent) => {
    for (x <- cells; y <- x) y.setText("")
  }

  val solveButton = new Button()
  solveButton.setText("Solve")
  menu.children.add(solveButton)
  solveButton.onAction = (event: ActionEvent) => {
    val all: Vector[Int] =
      for (x <- cells.toVector; y <- x.toVector) yield {
        if (y.getText().isEmpty()) 0
        else y.getText().toInt
      }
    val sliced = all.sliding(9, 9).toVector
    val result = Solver.solve(sliced) //todo: run in in thread

    (0 to 8).foreach(row => {
      (0 to 8).foreach(column => {
        val x = result match {
          case Some(value) => value(column)(row)
          case None => "-"
        }
        cells(column)(row).setText(x.toString)
      })
    })
  }

  (0 to 8).foreach(row => {
    (0 to 8).foreach(column => {
      val btn = new Button()
      cells(column)(row) = btn
      if (board(column)(row ) > 0) {
        btn.setText(board(column)(row).toString())
      }

      btn.setMinSize(40, 40)
      btn.setMaxSize(40, 40)
      btn.setId(s"cell_${row * column}")
      pane.add(btn, row, column)
      btn.onKeyPressed = (event: KeyEvent) => {
        val given: Char = event.getText.toCharArray()(0)
        if (given.isDigit) {
          btn.setText(given.toString)
        } else {
          btn.setText("")
        }
      }
    })
  })

  stage = new JFXApp.PrimaryStage
  stage.setScene(scene)
  stage.show()
}
