package fm.scala.lib.statement

case class StatementElementSplitter(val input: String, priorElementSplitter: Option[StatementElementSplitter] = None) {
  val elements: List[String] = input.split(Array(' ', '\n')).toList.map(x => splitSemicolonFromWord(x)).flatten
  
  private def splitSemicolonFromWord(word: String): List[String] = {
    if (word.nonEmpty && (word.last == ';' || word.last == '/') && word.size > 1) {
      val splitWord = word.splitAt(word.size - 1)
      List(splitWord._1, splitWord._2)
    } else {
      List(word)
    }
  }

}