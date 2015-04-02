package fm.scala.lib.statement

import org.junit.Test
import org.junit.Assert._

@Test
class TestStatementElementSplitter {
  
  @Test
  def splitSingleCreateCommand = {
    val inputString = "CREATE"
    val splitter = StatementElementSplitter(inputString)
      
    val expected = List("CREATE")
    val result = splitter.elements

    assertEquals(expected, result)
  }
  
  @Test
  def splitCreateStatement = {
    val inputString = "CREATE OR REPLACE TABLE CUSTOMERS;"
    val splitter = StatementElementSplitter(inputString)
      
    val expected = List("CREATE", "OR", "REPLACE", "TABLE" ,"CUSTOMERS", ";")
    val result = splitter.elements

    assertEquals(expected, result)
  }
  
  @Test
  def splitCreateStatementWithSplitSemicolon = {
    val inputString = "CREATE OR REPLACE TABLE CUSTOMERS ;"
    val splitter = StatementElementSplitter(inputString)
      
    val expected = List("CREATE", "OR", "REPLACE", "TABLE" ,"CUSTOMERS", ";")
    val result = splitter.elements

    assertEquals(expected, result)
  }
  
  @Test
  def splitCreateStatementWithLowerCases = {
    val inputString = "Create OR REPLACE table CUSTOMERS;"
    val splitter = StatementElementSplitter(inputString)
      
    val expected = List("Create", "OR", "REPLACE", "table" ,"CUSTOMERS", ";")
    val result = splitter.elements

    assertEquals(expected, result)
  }
  
  @Test
  def splitSimpleCall = {
    val inputString = "@@DEPLOY/SETUP.SQL PHO INSTALL 1050000 \"${project.name} ${project.version} (${buildNumber})\""
    val splitter = StatementElementSplitter(inputString)
      
    val expected = List("@@DEPLOY/SETUP.SQL", "PHO", "INSTALL", "1050000", "\"${project.name}", "${project.version}", "(${buildNumber})\"")
    val result = splitter.elements

    assertEquals(expected, result)
  }
  
  @Test
  def splitWheneverStatement = {
    val inputString = "WHENEVER OSERROR EXIT"
    val splitter = StatementElementSplitter(inputString)
      
    val expected = List("WHENEVER", "OSERROR", "EXIT")
    val result = splitter.elements

    assertEquals(expected, result)
  }
  
  @Test
  def splitPromptStatement = {
    val inputString = "PROMPT *****************************"
    val splitter = StatementElementSplitter(inputString)
      
    val expected = List("PROMPT", "*****************************")
    val result = splitter.elements

    assertEquals(expected, result)
  }
  
  @Test
  def splitDefineStatement = {
    val inputString = "DEFINE ADM=&CENTRAL_MODUL_ID."
    val splitter = StatementElementSplitter(inputString)
      
    val expected = List("DEFINE", "ADM=&CENTRAL_MODUL_ID.")
    val result = splitter.elements

    assertEquals(expected, result)
  }


}