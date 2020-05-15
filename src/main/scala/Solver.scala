package pl.ayeo.sudoku

object Solver {
  val n = 9
  val s = Math.sqrt(n).toInt

  private def possibleDigits(board: Domain.Board, r: Int, c: Int): Seq[Int] = {
    def cells(i: Int) = Seq(board(r)(i), board(i)(c), board(s * (r / s) + i / s)(s * (c / s) + i % s))
    val used = board.indices.flatMap(cells)
    (1 to 9).diff(used)
  }

  def solve(board: Domain.Board, cell: Int = 0): Option[Domain.Board] = (cell % n, cell / n) match {
    case (0, 9) => Some(board) //cell=81 board is solved
    case (r, c) if board(r)(c) > 0 => solve(board, cell + 1) //cell is already filled, go to next
    case (r, c) =>
      possibleDigits(board, r, c)
        .flatMap(n => solve(board.updated(r, board(r).updated(c, n)))) //find next solution for each digit
        .headOption //return first non-empty element (the ultimate solution) otherwise None
  }

  def print(board: Domain.Board): Unit = println(board.map(_.mkString(" ")).mkString("\n"))
}
