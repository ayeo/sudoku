§§§package pl.ayeo.sudoku

import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.Button
import scalafx.scene.layout.{BorderPane, GridPane, Pane, RowConstraints}
import scalafx.scene.paint.Color

class BoardPrinter(cellSize: Int, borderSize: Int) {
  val boardPane = new BorderPane()
  val pane: GridPane = new GridPane()

  pane.padding = Insets(borderSize, borderSize, borderSize, borderSize)
  boardPane.setCenter(pane)
  boardPane.children.add(buildGrid(cellSize, borderSize))

  def getPane(): Pane = boardPane

  def printBoard(state: State): Unit = {
    pane.getChildren.clear()

    (0 to 9).foreach { _ =>
      val con = new RowConstraints();
      con.setMinHeight(cellSize);
      con.setMaxHeight(cellSize);
      pane.getRowConstraints().add(con)
    }

    for ( //already filled by user
      (value, row: Int) <- state.user.zipWithIndex;
      (digit: Int, column: Int) <- value.zipWithIndex.filter { case (d, _) => d > 0 }
    ) {
      val btn = new Button()
      btn.onKeyPressed = Handler.handleNewInput(this)(state)
      btn.onMouseClicked = Handler.handleClick(this)(state)
      btn.setText(digit.toString)
      addButton(btn, column, row, state.focus)
    }

    for ( //empty cells
      (value, row: Int) <- state.emptyCells.zipWithIndex;
      (_, column: Int) <- value.zipWithIndex.filter { case (d, _) => d > 0 }
    ) {
      val btn = new Button()
      btn.onKeyPressed = Handler.handleNewInput(this)(state)
      btn.onMouseClicked = Handler.handleClick(this)(state)
      btn.setText("")
      addButton(btn, column, row, state.focus)
    }

    for ( //given numbers (unchangeable)
      (value, row: Int) <- state.given.zipWithIndex;
      (digit: Int, column: Int) <- value.zipWithIndex.filter { case (d, _) => d > 0 }
    ) {
      val btn = new Button()
      btn.setText(digit.toString)
      btn.onMouseClicked = Handler.handleClick(this)(state)
      btn.getStyleClass.add("hard")
      addButton(btn, column, row, state.focus)
    }
  }

  private def addButton(btn: Button, column: Int, row: Int, focus: (Int, Int)): Unit = {
    btn.setMinSize(cellSize, cellSize)
    btn.setMaxSize(cellSize, cellSize)
    btn.setStyle(s"-fx-font-size: ${(cellSize/2.2).toInt};")
    btn.getStyleClass.add("default")
    pane.add(btn, column, row)
    if (focus == (column, row)) {
      btn.requestFocus();
    }
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
    canvas
  }
}