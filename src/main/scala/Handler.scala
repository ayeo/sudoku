package pl.ayeo.sudoku

import javafx.scene.control.Button
import scalafx.scene.input.{KeyEvent, MouseEvent}
import scalafx.scene.layout.GridPane
import scalafx.Includes._

object Handler {
  def handleNewInput(painter: BoardPrinter)(state: State)(event: KeyEvent): Unit = {
      val btn: Button = event.getSource().asInstanceOf[Button]
      val colIndex: Int = GridPane.getColumnIndex(btn)
      val rowIndex: Int = GridPane.getRowIndex(btn)
      val newDigit = event.getCode.getName.toInt

      painter.printBoard(state.placeNumber(colIndex, rowIndex, newDigit))
  }

  def handleClick(painter: BoardPrinter)(state: State)(event: MouseEvent): Unit = {
    val btn: Button = event.getSource().asInstanceOf[Button]
    val colIndex: Int = GridPane.getColumnIndex(btn)
    val rowIndex: Int = GridPane.getRowIndex(btn)

    painter.printBoard(state.setFocus(colIndex, rowIndex))
  }
}
