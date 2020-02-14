import scalafx.application.JFXApp
import scalafx.stage.Stage
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.GridPane
import scalafx.Includes._

object Window extends JFXApp {
  stage = new JFXApp.PrimaryStage
  start(stage)
  
  def start(primaryStage: Stage): Unit = {
    val pane: GridPane = new GridPane()
    val scene: Scene = new Scene(pane, 500, 500)

    (1 to 9).foreach(row => {
      (1 to 9).foreach(column => {
        val btn = new Button()
        btn.setText("0")
        btn.setId(s"cell_${row*column}")
        pane.add(btn, row, column)
        btn.onAction = (event: ActionEvent) => {
          println("Bomber")
        }
      })
    })

    primaryStage.setScene(scene)
    primaryStage.show()
  }
}
