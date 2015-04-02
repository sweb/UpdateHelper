package fm.scala.lib.statement

import fm.scala.common.Environment


case class SQLStatementParser(iteratorOverStatements: Iterator[String])(implicit env: Environment)  {
  def parseStatements: List[SQLStatement] = {
    val listOfIdentifiedStatements = idenitfyStatements
    listOfIdentifiedStatements.map( x => tryToFindCorrectStatement(x._1, x._2))
  }
  
  def idenitfyStatements: List[(String, Option[ConstructedStatementElement.Value])] = {
    def identifyStatementIter(lineIterator: Iterator[String], precedingStateList: Option[ParsingStateList], accu: List[(String, Option[ConstructedStatementElement.Value])], lastLine: String): List[(String, Option[ConstructedStatementElement.Value])] = {
      if (!lineIterator.hasNext) {
        accu.reverse
      } else {
        val currentLine = lineIterator.next
        val splitter = StatementElementSplitter(currentLine)
        val parsingStateList = ParsingStateList(splitter.elements, precedingStateList)

        val newCurrentLine = List(lastLine, currentLine).filterNot(_ == "").mkString("\n")

        if (parsingStateList.isComplete) {
          identifyStatementIter(lineIterator, None, (newCurrentLine, parsingStateList.parsedConstruct) :: accu, "")
        } else {
          identifyStatementIter(lineIterator, Some(parsingStateList), accu, newCurrentLine)
        }
      }
    }
    identifyStatementIter(iteratorOverStatements, None, Nil, "")
  }
  
  private def tryToFindCorrectStatement(statementText: String, constructOption: Option[ConstructedStatementElement.Value]): SQLStatement = {
    constructOption match {
      case None => UnknownStatement(statementText)
      case Some(construct) => createStatement(statementText, construct)
    }
  }
  
  private def createStatement(statementText: String, construct: ConstructedStatementElement.Value): SQLStatement = {
    import ConstructedStatementElement._
    construct match {
      case CREATE_FUNCTION => CreateFunctionStatement(statementText)
      case CREATE_PROCEDURE => CreateProcedureStatement(statementText)
      case CREATE_TABLE => CreateTableStatement(statementText)
      case CREATE_VIEW => CreateViewStatement(statementText)
      case CREATE_INDEX => CreateIndexStatement(statementText)
      case CREATE_SEQUENCE => CreateSequenceStatement(statementText)
      case CREATE_SYNONYM => GenericStatement(statementText)
      case CREATE_TRIGGER => CreateTriggerStatement(statementText)
      case DROP_PROCEDURE => DropProcedureStatement(statementText)
      case DROP_FUNCTION => DropFunctionStatement(statementText)
      case DROP_TABLE => DropTableStatement(statementText)
      case DROP_INDEX => GenericDropStatement(statementText)
      case DROP_SEQUENCE => GenericDropStatement(statementText)
      case DROP_SYNONYM => GenericDropStatement(statementText)
      case DROP_TRIGGER => GenericDropStatement(statementText)
      case ALTER_TABLE => AlterTableStatement(statementText)
      case ALTER_SEQUENCE => GenericStatement(statementText)
      case ALTER_SESSION => GenericStatement(statementText)
      case ALTER_TRIGGER => AlterTriggerStatement(statementText)
      case ALTER_USER => GenericStatement(statementText)
      case ALTER_VIEW => GenericStatement(statementText)
      case DROP_VIEW => DropViewStatement(statementText)
      case INSERT_INTO => InsertStatement(statementText)
      case UPDATE_SET => UpdateStatement(statementText)
      case TRUNCATE_TABLE => GenericStatement(statementText)
      case DELETE_FROM => DeleteStatement(statementText)
      case COMMENT_ON => GenericStatement(statementText)
      case COMMIT => GenericStatement(statementText)
      case ROLLBACK => GenericStatement(statementText)
      case EXECUTE => GenericStatement(statementText)
      case DECLARE => GenericStatement(statementText)
      case SPECIAL_CALLS => GenericStatement(statementText)
      case _ => UnknownStatement(statementText)
    }
    
  }

}