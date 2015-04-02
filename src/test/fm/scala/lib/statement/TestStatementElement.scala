package fm.scala.lib.statement

import org.junit.Test
import org.junit.Assert._

@Test
class TestStatementElement {
  @Test
  def checkForCreateCommand = {
    
    val expected = "CREATE"
    val result = StatementElement.CREATE.toString  

    assertEquals(expected, result)
  }
  
  @Test
  def checkForSemicolon = {
    
    val expected = ";"
    val result = StatementElement.SEMICOLON.toString  

    assertEquals(expected, result)
  }

}