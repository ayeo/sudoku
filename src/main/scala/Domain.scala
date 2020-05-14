package pl.ayeo.sudoku

object Domain {
  type Board = Vector[Vector[Int]]

  def fromString(data: String, size: Int): Board =
    data.replaceAll(" +", "").toVector.map(_.toInt - 48).sliding(size, size).toVector
}
