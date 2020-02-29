import scalafx.application.JFXApp
import scalafx.geometry.Insets
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.Button
import scalafx.scene.layout.{BorderPane, GridPane}
import scalafx.scene.paint.Color
import scalafx.scene.Scene
import scala.collection.mutable.Seq

object Grid extends JFXApp {
  val canvas = new Canvas(452, 452)
  val gc = canvas.graphicsContext2D

  val w = 1
  gc.stroke = Color.Black
  gc.lineWidth = w
  (0 to 9).foreach(row => {
    gc.strokeLine(50 * row + w, w, 50 * row + w, 450 + w)
    gc.strokeLine(w, 50 * row + w, 450 + w, 50 * row + w)
  })

  gc.lineWidth = w * 2
  (0 to 3).foreach(row => {
    gc.strokeLine(150 * row + w, w, 150 * row + w, 450 + w)
    gc.strokeLine(w, 150 * row + w, 450 + w, 150 * row + w)
  })

  val mainPane: BorderPane = new BorderPane()
  val scene: Scene = new Scene(mainPane, 500, 500)

  val boardPane = new BorderPane()
  val pane: GridPane = new GridPane()
  pane.padding = Insets(w, w, w, w)
  boardPane.setCenter(pane)
  boardPane.children.add(canvas)

  mainPane.padding = Insets(10, 10, 10, 10)
  mainPane.setCenter(boardPane)


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

  (0 to 8).foreach(row => {
    (0 to 8).foreach(column => {
      val btn = new Button()
      cells(column)(row) = btn
      if (board(column)(row ) > 0) {
        btn.setText(board(column)(row).toString())
      }

      btn.setMinSize(50, 50)
      btn.setMaxSize(50, 50)
      btn.setStyle("-fx-background-color: #FFFFFF; -fx-font-size: 24;")
      btn.setId(s"cell_${row * column}")

      pane.add(btn, row, column)
    })
  })



  stage = new JFXApp.PrimaryStage
  stage.setScene(scene)
  stage.show()
}
