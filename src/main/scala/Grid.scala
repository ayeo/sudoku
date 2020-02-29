import scalafx.application.JFXApp
import scalafx.geometry.Insets
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.Button
import scalafx.scene.layout.{BorderPane, GridPane, Pane}
import scalafx.scene.paint.Color
import scalafx.scene.Scene
import scala.collection.mutable.Seq

object BoardBuilder {
  type Board = Seq[Seq[Int]]
  val cells = Array.ofDim[Button](9, 9)

  def buildBoard(data: Board, cellSize: Int, borderSize: Int): Pane = {
    val boardPane = new BorderPane()
    val pane: GridPane = new GridPane()

    pane.padding = Insets(borderSize, borderSize, borderSize, borderSize)
    boardPane.setCenter(pane)
    boardPane.children.add(BoardBuilder.buildCanvas(cellSize, borderSize))
    (0 to 8).foreach(row => {
      (0 to 8).foreach(column => {
        val btn = new Button()
        cells(column)(row) = btn
        if (data(column)(row ) > 0) {
          btn.setText(data(column)(row).toString())
        }

        btn.setMinSize(cellSize, cellSize)
        btn.setMaxSize(cellSize, cellSize)
        btn.setStyle(s"-fx-background-color: #FFFFFF; -fx-font-size: ${cellSize/2};")
        btn.setId(s"cell_${row * column}")
        pane.add(btn, row, column)
      })
    })

    return boardPane
  }

  private def buildCanvas(size: Int, borderSize: Int): Canvas = {
    val canvas = new Canvas(9 * size + 2 * borderSize, 9 * size + 2 * borderSize)
    val gc = canvas.graphicsContext2D
    gc.stroke = Color.Black
    gc.lineWidth = borderSize
    (0 to 9).foreach(row => {
      gc.strokeLine(size * row + borderSize, borderSize, size * row + borderSize, size * 9 + borderSize)
      gc.strokeLine(borderSize, size * row + borderSize, size * 9 + borderSize, size * row + borderSize)
    })

    gc.lineWidth = borderSize * 2
    (0 to 3).foreach(row => {
      gc.strokeLine(size * 3 * row + borderSize, borderSize, size * 3  * row + borderSize, size * 9 + borderSize)
      gc.strokeLine(borderSize, size * 3 * row + borderSize, size * 9 + borderSize, size * 3 * row + borderSize)
    })

    return canvas
  }
}

object Grid extends JFXApp {
  val board: BoardBuilder.Board = Seq(
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
  val scene: Scene = new Scene(mainPane, 400, 400)
  mainPane.padding = Insets(10, 10, 10, 10)
  mainPane.setCenter(BoardBuilder.buildBoard(board, 40, 1))

  stage = new JFXApp.PrimaryStage
  stage.setScene(scene)
  stage.show()
}
