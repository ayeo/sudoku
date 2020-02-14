import javafx.application.Application
import javafx.stage.Stage
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.BorderPane

class Window extends Application {
  var btn: Button = new Button()

  override def start(primaryStage: Stage): Unit = {
    btn.setText("Click me")
    val pane: BorderPane = new BorderPane()
    pane.setCenter(btn)
    val scene: Scene = new Scene(pane, 300, 300)
    primaryStage.setScene(scene)
    primaryStage.show()
  }
}
