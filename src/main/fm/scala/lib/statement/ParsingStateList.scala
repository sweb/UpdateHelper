package fm.scala.lib.statement



case class ParsingStateList(val inputs: List[String], val precedingStateList: Option[ParsingStateList] = None) {

  val elements: List[StatementElement.Value] = {
    val precedingElements = precedingStateList match {
        case None => Nil
        case Some(x) => x.elements
      }
     precedingElements ++ identifyElements(inputs)
  }
  
  val constructs = identifyConstructs
  
  private def identifyElements(inputs: List[String]): List[StatementElement.Value] =
    inputs.filterNot(_ == "").map(identifyElement(_)).flatten
  
  private def identifyElement(input: String): Option[StatementElement.Value] = {
    assert (input != "")
    StatementElement.values.find(x => input.matches("(?s)(?i)[\\s]*" + x.toString))
  }
    
  def isComplete: Boolean = (isSimpleStatement && !isInBlockStatement) || isDefiniteEnd
  
  def parsedConstruct: Option[ConstructedStatementElement.Value] = {
    if (isComplete) selectDominantConstruct else None
  }
  
  private def selectDominantConstruct: Option[ConstructedStatementElement.Value] = {
    import ConstructedStatementElement._
    Some(ConstructedStatementElement.values.map(x => constructs.find(y => y == x)).flatten.head)
  }
  
  private def isInBlockStatement: Boolean = {
    import ConstructedStatementElement._
    constructs.contains(DECLARE) || constructs.contains(CREATE_FUNCTION)  || 
    constructs.contains(CREATE_PROCEDURE) || constructs.contains(BEGIN)
  }
    
  private def isDefiniteEnd: Boolean =
    elements.contains(StatementElement.SLASH)
  
  private def isSimpleStatement: Boolean =
    isContainingSemicolon || isSimpleCall || 
    isWheneverStatement || isPromptStatement || isDefineStatement

  private def isContainingSemicolon: Boolean =
    elements.contains(StatementElement.SEMICOLON)  
    
  private def isSimpleCall: Boolean = 
    elements.contains(StatementElement.SQL_CALL)
    
  private def isWheneverStatement: Boolean = 
    elements.contains(StatementElement.WHENEVER)
    
  private def isPromptStatement: Boolean = 
    elements.contains(StatementElement.PROMPT)
    
  private def isDefineStatement: Boolean = 
    elements.contains(StatementElement.DEF) || elements.contains(StatementElement.DEFINE) 
    
  private def identifyConstructs: List[ConstructedStatementElement.Value] = {
    val originalElements = elements
    
    val singleElementConstructs = originalElements.map(x => identifyConstruct(List(x))).flatten
    
    val doubleElementConstructs = {
      if (elements.size > 1) {
        val shiftedElements = elements.tail
        combineTwoLists(shiftedElements, originalElements).map(x => identifyConstruct(x)).flatten
      } else {
        Nil
      }
    }
    
    
    singleElementConstructs ++ doubleElementConstructs
  }
  
  private def identifyConstruct(basicElements: List[StatementElement.Value]): Option[ConstructedStatementElement.Value] = {
    basicElements match {
      case List(StatementElement.SEMICOLON) => Some(ConstructedStatementElement.SEMICOLON)
      case List(StatementElement.SLASH) => Some(ConstructedStatementElement.SLASH)
      case List(StatementElement.DEF) => Some(ConstructedStatementElement.SPECIAL_CALLS)
      case List(StatementElement.DEFINE) => Some(ConstructedStatementElement.SPECIAL_CALLS)
      case List(StatementElement.PROMPT) => Some(ConstructedStatementElement.SPECIAL_CALLS)
      case List(StatementElement.SQL_CALL) => Some(ConstructedStatementElement.SPECIAL_CALLS)
      case List(StatementElement.WHENEVER) => Some(ConstructedStatementElement.SPECIAL_CALLS)
      case List(StatementElement.DECLARE) => Some(ConstructedStatementElement.DECLARE)
      case List(StatementElement.BEGIN) => Some(ConstructedStatementElement.BEGIN)
      case List(StatementElement.COMMIT) => Some(ConstructedStatementElement.COMMIT)
      case List(StatementElement.ROLLBACK) => Some(ConstructedStatementElement.ROLLBACK)
      case List(StatementElement.EXEC) => Some(ConstructedStatementElement.EXECUTE)
      case List(StatementElement.EXECUTE) => Some(ConstructedStatementElement.EXECUTE)
      case List(StatementElement.SQL_COMMENT) => Some(ConstructedStatementElement.COMMIT)
      case List(StatementElement.CREATE, StatementElement.FUNCTION) => Some(ConstructedStatementElement.CREATE_FUNCTION)
      case List(StatementElement.CREATE, StatementElement.PROCEDURE) => Some(ConstructedStatementElement.CREATE_PROCEDURE)
      case List(StatementElement.CREATE, StatementElement.TABLE) => Some(ConstructedStatementElement.CREATE_TABLE)
      case List(StatementElement.CREATE, StatementElement.VIEW) => Some(ConstructedStatementElement.CREATE_VIEW)
      case List(StatementElement.CREATE, StatementElement.INDEX) => Some(ConstructedStatementElement.CREATE_INDEX)
      case List(StatementElement.CREATE, StatementElement.SEQUENCE) => Some(ConstructedStatementElement.CREATE_SEQUENCE)
      case List(StatementElement.CREATE, StatementElement.SYNONYM) => Some(ConstructedStatementElement.CREATE_SYNONYM)
      case List(StatementElement.CREATE, StatementElement.TRIGGER) => Some(ConstructedStatementElement.CREATE_TRIGGER)
      case List(StatementElement.DROP, StatementElement.PROCEDURE) => Some(ConstructedStatementElement.DROP_PROCEDURE)
      case List(StatementElement.DROP, StatementElement.FUNCTION) => Some(ConstructedStatementElement.DROP_FUNCTION)
      case List(StatementElement.DROP, StatementElement.TABLE) => Some(ConstructedStatementElement.DROP_TABLE)
      case List(StatementElement.DROP, StatementElement.VIEW) => Some(ConstructedStatementElement.DROP_VIEW)
      case List(StatementElement.DROP, StatementElement.INDEX) => Some(ConstructedStatementElement.DROP_INDEX)
      case List(StatementElement.DROP, StatementElement.SEQUENCE) => Some(ConstructedStatementElement.DROP_SEQUENCE)
      case List(StatementElement.DROP, StatementElement.SYNONYM) => Some(ConstructedStatementElement.DROP_SYNONYM)
      case List(StatementElement.DROP, StatementElement.TRIGGER) => Some(ConstructedStatementElement.DROP_TRIGGER)
      case List(StatementElement.ALTER, StatementElement.TABLE) => Some(ConstructedStatementElement.ALTER_TABLE)
      case List(StatementElement.ALTER, StatementElement.SEQUENCE) => Some(ConstructedStatementElement.ALTER_SEQUENCE)
      case List(StatementElement.ALTER, StatementElement.SESSION) => Some(ConstructedStatementElement.ALTER_SESSION)
      case List(StatementElement.ALTER, StatementElement.TRIGGER) => Some(ConstructedStatementElement.ALTER_TRIGGER)
      case List(StatementElement.ALTER, StatementElement.USER) => Some(ConstructedStatementElement.ALTER_USER)
      case List(StatementElement.ALTER, StatementElement.VIEW) => Some(ConstructedStatementElement.ALTER_VIEW)
      case List(StatementElement.INSERT, StatementElement.INTO) => Some(ConstructedStatementElement.INSERT_INTO)
      case List(StatementElement.UPDATE, StatementElement.SET) => Some(ConstructedStatementElement.UPDATE_SET)
      case List(StatementElement.TRUNCATE, StatementElement.TABLE) => Some(ConstructedStatementElement.TRUNCATE_TABLE)
      case List(StatementElement.TRUNC, StatementElement.TABLE) => Some(ConstructedStatementElement.TRUNCATE_TABLE)
      case List(StatementElement.DELETE, StatementElement.FROM) => Some(ConstructedStatementElement.DELETE_FROM)
      case List(StatementElement.COMMENT, StatementElement.ON) => Some(ConstructedStatementElement.COMMENT_ON)
      case _ => None
    }
  }
  
  private def combineTwoLists(a: List[StatementElement.Value], b: List[StatementElement.Value]): List[List[StatementElement.Value]] = {
    assert(a.size <= b.size)
    
    if (a isEmpty) {
      Nil
    } else {
      List(b.head, a.head) :: combineTwoLists(a.tail, b.tail)
    }
  }
}