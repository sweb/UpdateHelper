package fm.scala.lib.statement

object StatementElement extends Enumeration  {
  type StatementElement = String
  
  val CREATE, DROP, DELETE, ALTER, TABLE, VIEW, PROCEDURE, FUNCTION, SEQUENCE, SESSION, TRIGGER, USER, INDEX, SYNONYM, TRUNCATE, TRUNC, FROM = Value
  
  val SEMICOLON = Value(";")
  
  val SLASH = Value("/")
  
  val SQL_CALL = Value("^\\s*@@.*")
  
  val SQL_COMMENT = Value("^\\s*/\\*.*")
  
  val COMMENT, ON = Value
  
  val COMMIT, ROLLBACK, EXEC, EXECUTE = Value
  
  val WHENEVER, PROMPT, DEFINE, DEF = Value
  
  val DECLARE, BEGIN, END = Value
  
  val INSERT, INTO = Value
  val UPDATE, SET = Value

}

object ConstructedStatementElement extends Enumeration  {
  type ConstructedStatementElement = String
  // SQL-Calls, Whenever, Prompt, Define are special calls
  val CREATE_FUNCTION, CREATE_PROCEDURE, CREATE_TABLE, CREATE_VIEW, CREATE_INDEX, CREATE_SEQUENCE, CREATE_SYNONYM, CREATE_TRIGGER = Value
  val DROP_PROCEDURE,DROP_FUNCTION, DROP_TABLE, DROP_VIEW, DROP_INDEX, DROP_SEQUENCE, DROP_SYNONYM, DROP_TRIGGER = Value
  val ALTER_TABLE, ALTER_SEQUENCE, ALTER_SESSION, ALTER_TRIGGER, ALTER_USER, ALTER_VIEW = Value
  
  val TRUNCATE_TABLE = Value
  
  val DELETE_FROM, COMMIT, ROLLBACK, EXECUTE = Value
  
  val INSERT_INTO, UPDATE_SET = Value
  
  val DECLARE, BEGIN, END = Value
  
  val COMMENT_ON = Value
  
  val SEMICOLON, SLASH, SPECIAL_CALLS = Value

}