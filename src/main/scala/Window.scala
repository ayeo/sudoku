import scalafx.application.JFXApp
import scala.collection.mutable.Seq
import scalafx.stage.Stage
import scalafx.event.{ActionEvent, EventTarget}
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.{BorderPane, GridPane}
import scalafx.Includes._
import scalafx.scene.input.KeyEvent

object Window extends JFXApp {
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
  mainPane.setCenter(pane)

  val solveButton = new Button()
  solveButton.setText("Solve")
  mainPane.setBottom(solveButton)
  solveButton.onAction = (event: ActionEvent) => {
    val x: Vector[Int] =
      for (
        x <- cells.toVector;
        y <- x.toVector
      ) yield {
        if (y.getText().isEmpty) 0
        else y.getText().toInt
      }
    val y = x.sliding(9).toVector
    println(y)
  }

  (1 to 9).foreach(row => {
    (1 to 9).foreach(column => {
      val btn = new Button()
      cells(column-1)(row-1) = btn
      if (board(column - 1)(row - 1) > 0) {
        btn.setText(board(column - 1)(row - 1).toString())
      }

      btn.setMinSize(40, 40)
      btn.setMaxSize(40, 40)
      btn.setId(s"cell_${row * column}")
      pane.add(btn, row, column)
      btn.onKeyPressed = (event: KeyEvent) => {
        btn.setText(event.getText())
      }
    })
  })

  stage = new JFXApp.PrimaryStage
  stage.setScene(scene)
  stage.show()
}
