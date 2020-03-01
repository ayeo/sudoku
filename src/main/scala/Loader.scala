package pl.ayeo.sudoku

import scala.util.Random

class Loader(level: Levels.Level) {
  private lazy val puzzles = init(level)

  private def init(level: Levels.Level): Option[List[String]] = {
    try {
      val data = io.Source.fromFile(s"resources/${level.toString}.txt")
      val result = data.getLines.toList.filter(p => p.length == 81)
      data.close
      Some(result)
    } catch {
      case _ => None
    }
  }

  def loadRandomPuzzle: Option[Domain.Board] = {
    puzzles.map(list => {
      val position = Random.nextInt(list.size)
      list.drop(position).head.toVector.map(_.toInt - 48).sliding(9, 9).toVector
    })
  }
}
