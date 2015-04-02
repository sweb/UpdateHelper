package fm.scala.common

/**
 * This objects stores general constants for the whole application.
 */
object Constants {
  import RegExStatement._
  import FolderName._
  
  val APPLICATION_NAME = "UpdateHelper"
  val VERSION = "0.2.5"
  val LAST_MODIFICATION = "18.02.2014"
  val STANDARD_CONFIGURATION_FILE = "configuration.xml"

  /**
   * Defines call-order of file types
   */
  val filetypeOrder: List[FolderName] =
    List(TABLE_FOLDER,
      PROCEDURE_FOLDER,
      FUNCTION_FOLDER,
      VIEW_FOLDER,
      DATA_FOLDER,
      EXEC_FOLDER)
  
  //---------------------------------------------------------------------------
  
  //---------------------------------------------------------------------------
  // Misc constants
  //---------------------------------------------------------------------------
  val newline = System.getProperty("line.separator")
  //---------------------------------------------------------------------------

}

object RegExStatement extends Enumeration {
  type RegExStatement = String
//  val IDENTIFY_CREATE_TABLE = "[ ]*(?i)create table(?s).*"
//  val IDENTIFY_ALTER_TABLE = "[ ]*(?i)alter table(?s).*"
//  val IDENTIFY_TABLE_COMMENT = "[ ]*(?i)comment on (table|column)(?s).*"
//  val IDENTIFY_DROP_TABLE = "[ ]*(?i)drop table(?s).*"
//    
//  val IDENTIFY_CREATE_PROCEDURE = ""
//  val IDENTIFY_EXEC_PROCEDURE = "[ ]*(?i)exec(?s).*;[\\s]*"

  val PARSE_UPDATE_VERSION = "([0-9])([0-9][0-9])([0-9][0-9])"

  val PARSE_FULL_FILE_NAME = ".*\\\\(.*)\\\\(.*)\\\\(.*)(?=\\.(?i)sql)"
    
  val FILE_NAME = "(?i)((?<=input\\\\)(.*)\\\\(.*\\.sql|tab)\\s*)$"
    
	//val INSERT_STATEMENT = "(?i)insert into[\\s](\\w*\\.)?(\\w*)[\\s]*\\(.*\\)[\\s]*values[\\s]*\\(.*\\).*$"
//    val INSERT_STATEMENT = "(?i)(?s)^\\s*insert into\\s*(\\w*\\.)?(\\w*)[\\s].*$"
//	val UPDATE_STATEMENT = "(?i)(?s)update[\\s](\\w*\\.)?(\\w*)[\\s]*set[\\s]*.*$"
//	val CREATE_TABLE_STATEMENT = "(?i)(?s)create[\\s]*table[\\s]*(\\w*\\.)?(\\w*).*$"
//	val DROP_TABLE_STATEMENT = "(?i)(?s)drop[\\s]*table[\\s]*(\\w*\\.)?(\\w*).*$"
//	val ALTER_TABLE_STATEMENT = "(?i)(?s)alter[\\s]*table[\\s]*(\\w*\\.)?(\\w*).*$"
//	val CREATE_VIEW_STATEMENT = "(?i)(?s)create[\\s]*(?:or[\\s]*replace[\\s]*)?(?:[\\s]*force[\\s]*)?view[\\s]*(\"{0,1}\\w*\"{0,1}\\.)?\"{0,1}(\\w*)\"{0,1}.*$"
//	val DROP_VIEW_STATEMENT = "(?i)(?s)drop[\\s]*view[\\s]*(\\w*\\.)?(\\w*).*$"
//	val CREATE_PROCEDURE_STATEMENT = "(?i)(?s)create[\\s]*(?:or[\\s]*replace[\\s]*)?procedure[\\s]*(\\w*\\.)?(\\w*).*"
//	val DROP_PROCEDURE_STATEMENT = "(?i)(?s)drop\\s*procedure\\s*(\\w*\\.)?(\\w*).*"
//	val CREATE_FUNCTION_STATEMENT = "(?i)(?s)create[\\s]*(?:or[\\s]*replace[\\s]*)?function[\\s]*(\\w*\\.)?(\\w*).*"
//	val DROP_FUNCTION_STATEMENT = "(?i)(?s)drop\\s*function\\s*(\\w*\\.)?(\\w*).*"
	
//	val DESCRIPTION_TAG = "(?i)(?s)^\\s*--\\s*@descr:.*"
//	  
//	val ENDING_WITH_SEMICOLON = "(?s).*;[\\s]*$"
//    val ENDING_WITH_SLASH = "(?s).*/[\\s]*$"
//    val BEGIN_BLOCK = "[\\s]*(?i)begin[\\s]*"
//    val BEGIN_PROCEDURE = ".*[\\s]*(?i)procedure[\\s]*.*"
//      val BEGIN_FUNCTION = ".*[\\s]*(?i)function[\\s]*.*"
//    val BEGIN_IF = "[\\s]*(?i)if[\\s].*"
//    val BEGIN_FOR = "[\\s]*(?i)for[\\s].*"
//    val END_BLOCK = "[\\s]*(?i)end[\\s]*.*;[\\s]*$"
}

object FolderName extends Enumeration {
  type FolderName = String
  val DATA_FOLDER = "DATA"
  val VIEW_FOLDER = "VIEW"
  val TABLE_FOLDER = "TABLE"
  val PROCEDURE_FOLDER = "PROCEDURE"
  val FUNCTION_FOLDER = "FUNCTION"
  val EXEC_FOLDER = "EXEC"
}