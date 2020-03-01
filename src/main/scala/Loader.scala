import scala.util.Random

class Loader(level: Levels.Level) {
  private lazy val puzzles = init(level)

  private def init(level: Levels.Level): List[String] = {
    val data = io.Source.fromFile(s"resources/${level.toString}.txt")
    val result = data.getLines.toList
    data.close
    result
  }

  def loadRandomPuzzle: Domain.Board = {
    puzzles.drop(Random.nextInt(puzzles.size)).head.toVector.map(_.toInt - 48).sliding(9, 9).toVector
  }
}
