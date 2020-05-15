package pl.ayeo.sudoku

import scalafx.application.JFXApp
import scalafx.scene.Scene

object Game extends JFXApp {
  val boardPrinter = new BoardPrinter(40, 1)
  val printer = new Gui(boardPrinter)

  val scene: Scene = new Scene(printer.getPane(), 400, 400)
  scene.getStylesheets.add("styles.css")

  stage = new JFXApp.PrimaryStage
  stage.setScene(scene)
  stage.show()
}
