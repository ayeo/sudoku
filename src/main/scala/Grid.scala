import Grid.{board, mainPane}
import scalafx.application.JFXApp
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.Button
import scalafx.scene.layout.{BorderPane, GridPane, Pane}
import scalafx.scene.paint.Color
import scalafx.scene.Scene
import scalafx.Includes._
import scalafx.scene.input.KeyEvent

import scala.util.Random

object BoardBuilder {
  type Board = Vector[Vector[Int]]
  val cells = Array.ofDim[Button](9, 9)

  def buildBoard(data: Board, cellSize: Int, borderSize: Int): Pane = {
    val boardPane = new BorderPane()
    val pane: GridPane = new GridPane()

    pane.padding = Insets(borderSize, borderSize, borderSize, borderSize)
    boardPane.setCenter(pane)
    boardPane.children.add(BoardBuilder.buildGrid(cellSize, borderSize))


    (0 to 8).foreach(row => {
      (0 to 8).foreach(column => {
        val btn = new Button()
        cells(column)(row) = btn
        if (data(column)(row ) > 0) {
          btn.setText(data(column)(row).toString())
        }
        btn.onKeyPressed = (event: KeyEvent) => {
          val given: Char = event.getText.toCharArray()(0)
          if (given.isDigit) {
            btn.setText(given.toString)
          } else {
            btn.setText("")
          }
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

  private def buildGrid(size: Int, borderSize: Int): Canvas = {
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

    canvas.setMouseTransparent(true)

    return canvas
  }
}

object Loader {
//  def load(filename: String): BoardBuilder.Board = {
//    val bufferedSource = io.Source.fromFile(s"resources/${filename}")
//    val digits: Iterator[Int] = for (
//      line <- bufferedSource.getLines;
//      digit <- line.split(",").map(_.trim)
//    ) yield digit.toInt
//    val board: BoardBuilder.Board = digits.toVector.sliding(9, 9).toVector
//
//
//    return board
//  }

  def loadRandomPuzzle: BoardBuilder.Board = {
    val data = io.Source.fromFile("resources/easy.txt")
    val (x, y) = data.getLines.duplicate
    val board = y.drop(Random.nextInt(x.size)).next.toVector.map(_.toInt - 48).sliding(9, 9).toVector
    data.close

    return board
  }
}

object Grid extends JFXApp {
  val mainPane: BorderPane = new BorderPane()
  val scene: Scene = new Scene(mainPane, 400, 400)
  val board: BoardBuilder.Board = Loader.loadRandomPuzzle

  mainPane.padding = Insets(10, 10, 10, 10)
  mainPane.setCenter(BoardBuilder.buildBoard(board, 40, 1))

  val newGame = new Button()
  newGame.setText("New game")
  mainPane.setRight(newGame)
  newGame.onAction = (event: ActionEvent) => {
    val board: BoardBuilder.Board = Loader.loadRandomPuzzle
    mainPane.setCenter(BoardBuilder.buildBoard(board, 40, 1))
  }


  stage = new JFXApp.PrimaryStage
  stage.setScene(scene)
  stage.show()
}
