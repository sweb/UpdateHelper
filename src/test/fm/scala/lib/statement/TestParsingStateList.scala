package fm.scala.lib.statement

import org.junit.Test
import org.junit.Assert._

class TestParsingStateList {
  
  @Test
  def identifyCreateCommand = {
    val listOfWords = List("CREATE")
    val stateList = ParsingStateList(listOfWords)
    
    val expected = List(StatementElement.CREATE)
    val result = stateList.elements

    assertEquals(expected, result)
  }
  
  @Test
  def identifyMultipleCommands = {
    import StatementElement._
    val listOfWords = List("CREATE", "OR", "REPLACE", "TABLE" ,"CUSTOMERS", ";")
    val stateList = ParsingStateList(listOfWords)
    
    val expected = List(CREATE, TABLE, SEMICOLON)
    val expected2 = Some(ConstructedStatementElement.CREATE_TABLE)
    val result = stateList.elements
    val result2 = stateList.parsedConstruct

    assertEquals("Identification failed",expected, result)
    assertEquals("Parsing failed",expected2, result2)
  }
  
  @Test
  def identifyMultipleCommandsWithLowerCases = {
    import StatementElement._
    val listOfWords = List("Create", "OR", "REPLACE", "table" ,"CUSTOMERS", ";")
    val stateList = ParsingStateList(listOfWords)
    
    val expected = List(CREATE, TABLE, SEMICOLON)
    val expected2 = Some(ConstructedStatementElement.CREATE_TABLE)
    val result = stateList.elements
    val result2 = stateList.parsedConstruct

    assertEquals("Identification failed",expected, result)
    assertEquals("Parsing failed",expected2, result2)
  }
  
  @Test
  def identifySimpleStatement = {
    import StatementElement._
    val listOfWords = List("Create", "OR", "REPLACE", "table" ,"CUSTOMERS", ";")
    val stateList = ParsingStateList(listOfWords)
    
    val expected = true
    val result = stateList.isComplete
    
    val expectedConstruct = Some(ConstructedStatementElement.CREATE_TABLE)
    val resultConstruct = stateList.parsedConstruct

    assertEquals("Identification failed", expected, result)
    assertEquals("Parsing failed", expectedConstruct, resultConstruct)
  }
  
  @Test
  def identifySimpleCall = {
    import StatementElement._
    val listOfWords = List("@@DEPLOY/SETUP.SQL", "PHO", "INSTALL", "1050000", "\"${project.name}", "${project.version}", "(${buildNumber})\"")
    val stateList = ParsingStateList(listOfWords)
    
    val expected = true
    val result = stateList.isComplete
    
    val expectedConstruct = Some(ConstructedStatementElement.SPECIAL_CALLS)
    val resultConstruct = stateList.parsedConstruct

    assertEquals("Identification failed", expected, result)
    assertEquals("Parsing failed", expectedConstruct, resultConstruct)
  }
  
  @Test
  def identifyWheneverStatement = {
    import StatementElement._
    val listOfWords = List("WHENEVER", "OSERROR", "EXIT")
    val stateList = ParsingStateList(listOfWords)
    
    val expected = true
    val result = stateList.isComplete
    
    val expectedConstruct = Some(ConstructedStatementElement.SPECIAL_CALLS)
    val resultConstruct = stateList.parsedConstruct

    assertEquals("Identification failed", expected, result)
    assertEquals("Parsing failed", expectedConstruct, resultConstruct)
  }
  
  @Test
  def identifyPromptStatement = {
    import StatementElement._
    val listOfWords = List("PROMPT", "*****************************")
    val stateList = ParsingStateList(listOfWords)
    
    val expected = true
    val result = stateList.isComplete

    val expectedConstruct = Some(ConstructedStatementElement.SPECIAL_CALLS)
    val resultConstruct = stateList.parsedConstruct

    assertEquals("Identification failed", expected, result)
    assertEquals("Parsing failed", expectedConstruct, resultConstruct)
  }
  
  @Test
  def identifyDefineStatement = {
    import StatementElement._
    val listOfWords = List("DEFINE", "ADM=&CENTRAL_MODUL_ID.")
    val stateList = ParsingStateList(listOfWords)
    
    val expected = true
    val result = stateList.isComplete

    val expectedConstruct = Some(ConstructedStatementElement.SPECIAL_CALLS)
    val resultConstruct = stateList.parsedConstruct

    assertEquals("Identification failed", expected, result)
    assertEquals("Parsing failed", expectedConstruct, resultConstruct)
  }
  
  @Test
  def identifyDefStatement = {
    import StatementElement._
    val listOfWords = List("DEF", "ADM=&CENTRAL_MODUL_ID.")
    val stateList = ParsingStateList(listOfWords)
    
    val expected = true
    val result = stateList.isComplete

    val expectedConstruct = Some(ConstructedStatementElement.SPECIAL_CALLS)
    val resultConstruct = stateList.parsedConstruct

    assertEquals("Identification failed", expected, result)
    assertEquals("Parsing failed", expectedConstruct, resultConstruct)
  }
  
  @Test
  def identifyAnonymousBlockStatement = {
    import StatementElement._
    
    val input = List("DECLARE",
      "BEGIN",
      "NULL;",
      "END;",
      "/")
    
    val listOfWords1 = List("DECLARE")
    val stateList1 = ParsingStateList(listOfWords1)
    
    val listOfWords2 = List("BEGIN")
    val stateList2 = ParsingStateList(listOfWords2, Some(stateList1))
    
    val listOfWords3 = List("NULL", ";")
    val stateList3 = ParsingStateList(listOfWords3, Some(stateList2))
    
    val listOfWords4 = List("END", ";")
    val stateList4 = ParsingStateList(listOfWords4, Some(stateList3))
    
    val listOfWords5 = List("/")
    val stateList5 = ParsingStateList(listOfWords5, Some(stateList4))
    
    val expected = (false, false, false, false, true)
    val result = (stateList1.isComplete, stateList2.isComplete, stateList3.isComplete, stateList4.isComplete, stateList5.isComplete) 

    val expectedConstruct = (None, None, None, None, Some(ConstructedStatementElement.DECLARE))
    val resultConstruct = (stateList1.parsedConstruct, stateList2.parsedConstruct, stateList3.parsedConstruct, stateList4.parsedConstruct, stateList5.parsedConstruct)

    assertEquals("Identification failed", expected, result)
    assertEquals("Parsing failed", expectedConstruct, resultConstruct)
  }
  
  @Test
  def identifyDropProcedureStatement = {
    import StatementElement._
    val listOfWords = List("DROP", "PROCEDURE", "kerner.transfer", ";")
    val stateList = ParsingStateList(listOfWords)
    
    val expected = true
    val result = stateList.isComplete

    val expectedConstruct = Some(ConstructedStatementElement.DROP_PROCEDURE)
    val resultConstruct = stateList.parsedConstruct

    assertEquals("Identification failed", expected, result)
    assertEquals("Parsing failed", expectedConstruct, resultConstruct)
  }
  
  @Test
  def identifyDropFunctionStatement = {
    import StatementElement._
    val listOfWords = List("DROP", "FUNCTION", "PAY_SALARY", ";")
    val stateList = ParsingStateList(listOfWords)
    
    val expected = true
    val result = stateList.isComplete

    val expectedConstruct = Some(ConstructedStatementElement.DROP_FUNCTION)
    val resultConstruct = stateList.parsedConstruct

    assertEquals("Identification failed", expected, result)
    assertEquals("Parsing failed", expectedConstruct, resultConstruct)
  }
  
  @Test
  def identifyCreateFunctionStatement = {
    import StatementElement._
    
    val input = List("CREATE", "FUNCTION", "name_update(",
      "old_name", "in", "varchar2,", "new_name", "in", "out", "varchar2)",
      "is", "language", "java", "name",
      "'EMPTrigg.NameUpdate", "(java.lang.String,", "java.lang.String[])'", ";",
      "/")
    
    val listOfWords1 = List("CREATE", "FUNCTION", "name_update(")
    val stateList1 = ParsingStateList(listOfWords1)
    
    val listOfWords2 = List("old_name", "in", "varchar2,", "new_name", "in", "out", "varchar2)")
    val stateList2 = ParsingStateList(listOfWords2, Some(stateList1))
    
    val listOfWords3 = List("is", "language", "java", "name")
    val stateList3 = ParsingStateList(listOfWords3, Some(stateList2))
    
    val listOfWords4 = List("'EMPTrigg.NameUpdate", "(java.lang.String,", "java.lang.String[])'", ";")
    val stateList4 = ParsingStateList(listOfWords4, Some(stateList3))
    
    val listOfWords5 = List("/")
    val stateList5 = ParsingStateList(listOfWords5, Some(stateList4))
    
    val expected = (false, false, false, false, true)
    val result = (stateList1.isComplete, stateList2.isComplete, stateList3.isComplete, stateList4.isComplete, stateList5.isComplete) 

    val expectedConstruct = (None, None, None, None, Some(ConstructedStatementElement.CREATE_FUNCTION))
    val resultConstruct = (stateList1.parsedConstruct, stateList2.parsedConstruct, stateList3.parsedConstruct, stateList4.parsedConstruct, stateList5.parsedConstruct)

    assertEquals("Identification failed", expected, result)
    assertEquals("Parsing failed", expectedConstruct, resultConstruct)
  }
  
  @Test
  def identifyCreateProcedureStatement = {
    import StatementElement._
    
    val input = List("CREATE", "PROCEDURE", "name_update(",
      "old_name", "in", "varchar2,", "new_name", "in", "out", "varchar2)",
      "is", "language", "java", "name",
      "'EMPTrigg.NameUpdate", "(java.lang.String,", "java.lang.String[])'", ";",
      "/")
    
    val listOfWords1 = List("CREATE", "PROCEDURE", "name_update(")
    val stateList1 = ParsingStateList(listOfWords1)
    
    val listOfWords2 = List("old_name", "in", "varchar2,", "new_name", "in", "out", "varchar2)")
    val stateList2 = ParsingStateList(listOfWords2, Some(stateList1))
    
    val listOfWords3 = List("is", "language", "java", "name")
    val stateList3 = ParsingStateList(listOfWords3, Some(stateList2))
    
    val listOfWords4 = List("'EMPTrigg.NameUpdate", "(java.lang.String,", "java.lang.String[])'", ";")
    val stateList4 = ParsingStateList(listOfWords4, Some(stateList3))
    
    val listOfWords5 = List("/")
    val stateList5 = ParsingStateList(listOfWords5, Some(stateList4))
    
    val expected = (false, false, false, false, true)
    val result = (stateList1.isComplete, stateList2.isComplete, stateList3.isComplete, stateList4.isComplete, stateList5.isComplete) 

    val expectedConstruct = (None, None, None, None, Some(ConstructedStatementElement.CREATE_PROCEDURE))
    val resultConstruct = (stateList1.parsedConstruct, stateList2.parsedConstruct, stateList3.parsedConstruct, stateList4.parsedConstruct, stateList5.parsedConstruct)

    assertEquals("Identification failed", expected, result)
    assertEquals("Parsing failed:\nconstructs:\n" + stateList5.constructs, expectedConstruct, resultConstruct)
  }
  
  @Test
  def identifyCreateProcedureWithLineBreakStatement = {
    import StatementElement._
    
    val input = List("create or replace ",
"procedure updateBueMapping ",
      "/")
    
    val listOfWords1 = List("create", "or", "replace")
    val stateList1 = ParsingStateList(listOfWords1)
    
    val listOfWords2 = List("procedure", "updateBueMapping")
    val stateList2 = ParsingStateList(listOfWords2, Some(stateList1))
    
    val listOfWords3 = List("/")
    val stateList3 = ParsingStateList(listOfWords3, Some(stateList2))
    
    val expected = (false, false, true)
    val result = (stateList1.isComplete, stateList2.isComplete, stateList3.isComplete) 

    val expectedConstruct = (None, None, Some(ConstructedStatementElement.CREATE_PROCEDURE))
    val resultConstruct = (stateList1.parsedConstruct, stateList2.parsedConstruct, stateList3.parsedConstruct)

    assertEquals("Identification failed", expected, result)
    assertEquals("Parsing failed:\nconstructs:\n" + stateList3.constructs, expectedConstruct, resultConstruct)
  }
  
  @Test
  def identifyCreateFunctionConstruct = {
    import ConstructedStatementElement._
    val listOfWords = List("CREATE", "FUNCTION", "PAY_SALARY", ";")
    val stateList = ParsingStateList(listOfWords)
    
    val expected = List(CREATE_FUNCTION, SEMICOLON).toSet
    val result = stateList.constructs.toSet

    val expectedConstruct = None
    val resultConstruct = stateList.parsedConstruct

    assertEquals("Identification failed", expected, result)
    assertEquals("Parsing failed", expectedConstruct, resultConstruct)
  }
  
  @Test
  def identifyAnonymousStatement = {
    import StatementElement._
    
    val input = List("begin",
      "for emp_rec in (select * from emp) loop",
      "begin",
      "my_proc (emp_rec);",
      "exception",
      "when some_exception then",
      "log_error('Failed to process employee '||emp_rec.empno);",
      "end;",
      "end loop;",
      "end;",
      "/")
    
    val listOfWords1 = List("begin")
    val stateList1 = ParsingStateList(listOfWords1)
    
    val listOfWords2 = List("for","emp_rec","in","(select","*","from","emp)","loop")
    val stateList2 = ParsingStateList(listOfWords2, Some(stateList1))
    
    val listOfWords3 = List("begin")
    val stateList3 = ParsingStateList(listOfWords3, Some(stateList2))
    
    val listOfWords4 = List("my_proc","(emp_rec)",";")
    val stateList4 = ParsingStateList(listOfWords4, Some(stateList3))
    
    val listOfWords5 = List("exception")
    val stateList5 = ParsingStateList(listOfWords5, Some(stateList4))
    
    val listOfWords6 = List("when","some_exception","then")
    val stateList6 = ParsingStateList(listOfWords6, Some(stateList5))
    
    val listOfWords7 = List("log_error('Failed","to","process", "employee","'||emp_rec.empno)",";")
    val stateList7 = ParsingStateList(listOfWords7, Some(stateList6))
    
    val listOfWords8 = List("end",";")
    val stateList8 = ParsingStateList(listOfWords8, Some(stateList7))
    
    val listOfWords9 = List("end","loop",";")
    val stateList9 = ParsingStateList(listOfWords9, Some(stateList8))
    
    val listOfWords10 = List("end",";")
    val stateList10 = ParsingStateList(listOfWords10, Some(stateList9))
    
    val listOfWords11 = List("/")
    val stateList11 = ParsingStateList(listOfWords11, Some(stateList10))
    
    val expected = (false, false, false, false, false, false, false, false, false, false, true)
    val result = (stateList1.isComplete, stateList2.isComplete, stateList3.isComplete, stateList4.isComplete, stateList5.isComplete,
        stateList6.isComplete, stateList7.isComplete, stateList8.isComplete, stateList9.isComplete, stateList10.isComplete, stateList11.isComplete) 

    val expectedConstruct = (None, None, None, None, None, None, None, None, None, None, Some(ConstructedStatementElement.BEGIN))
    val resultConstruct = (stateList1.parsedConstruct, stateList2.parsedConstruct, stateList3.parsedConstruct, 
        stateList4.parsedConstruct, stateList5.parsedConstruct, stateList6.parsedConstruct, stateList7.parsedConstruct,
        stateList8.parsedConstruct, stateList9.parsedConstruct, stateList10.parsedConstruct, stateList11.parsedConstruct)

    assertEquals("Identification failed", expected, result)
    assertEquals("Parsing failed", expectedConstruct, resultConstruct)
  }
  
  @Test
  def parseCreateFunctionConstruct = {
    import ConstructedStatementElement._
    val listOfWords = List("CREATE", "FUNCTION", "PAY_SALARY", ";", "/")
    val stateList = ParsingStateList(listOfWords)
    
    val expected = Some(CREATE_FUNCTION)
    val result = stateList.parsedConstruct

    assertEquals(expected, result)
  }
  
  @Test
  def parseIncompleteStatement = {
    import ConstructedStatementElement._
    val listOfWords = List("begin")
    val stateList = ParsingStateList(listOfWords)
    
    val expected = None
    val result = stateList.parsedConstruct

    assertEquals(expected, result)
  }
  
  @Test
  def parseInsertIntoConstruct = {
    import ConstructedStatementElement._
    val listOfWords = List("INSERT", "INTO", "Customers", ";")
    val stateList = ParsingStateList(listOfWords)
    
    val expected = Some(INSERT_INTO)
    val result = stateList.parsedConstruct

    assertEquals(expected, result)
  }

}