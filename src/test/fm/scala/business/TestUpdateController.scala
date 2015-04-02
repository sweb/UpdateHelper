package fm.scala.business

import org.junit.Test
import org.junit.Assert._
import fm.scala.lib.MixedStatementsFile
import fm.scala.lib.statement.SQLStatement
import fm.scala.lib.statement.InsertStatement
import fm.scala.common.TestEnvironment

@Test
class TestUpdateController {
  
      implicit val env = TestEnvironment()
  
  @Test
  def createMixedStatementsFilesWithInsertIntoData = {
    
    
    val uh = UpdateController()
    
    val inputLine1 = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES ('Cardinal','Tom B. Erichsen','Skagen 21','Stavanger','4006','Norway');"
    val inputLine2a = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) "
    val inputLine2b = "VALUES ('Cardinal','Eric Tombsen','Skagen 23','Stavanger','4006','Norway');"
    val inputLine3 = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES ('Cardinal','Benne Bericsen','Skagen 25','Stavanger','4006','Norway');"

    val inputIterator = List(inputLine1, inputLine2a, inputLine2b, inputLine3).toIterator
    
    val completefilePath = ""
      
    val input = List(("", inputIterator))
      
    val resultInput1 = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES " + "('Cardinal','Tom B. Erichsen','Skagen 21','Stavanger','4006','Norway');" 
    val resultInput2 = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) \nVALUES ('Cardinal','Eric Tombsen','Skagen 23','Stavanger','4006','Norway');"
    val resultInput3 = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES ('Cardinal','Benne Bericsen','Skagen 25','Stavanger','4006','Norway');"
    
    val statement1 = InsertStatement(resultInput1)
    val statement2 = InsertStatement(resultInput2)
    val statement3 = InsertStatement(resultInput3)
    
    val expected = List(MixedStatementsFile(completefilePath, List(statement1, statement2, statement3), Map.empty) )
    
    val result = uh.createMixedStatementsFiles(input)
    
    assertEquals("Creation of mixed statements files with standard data did not work", expected, result)
  }
  
  @Test
  def createMixedStatementsFilesWithMixedData = {
    
    val uh = UpdateController()
    
    val inputLine1 = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES ('Cardinal','Tom B. Erichsen','Skagen 21','Stavanger','4006','Norway');"
    val inputLine2a = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) "
    val inputLine2b = "VALUES ('Cardinal','Eric Tombsen','Skagen 23','Stavanger','4006','Norway');"
    val inputLine3 = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES ('Cardinal','Benne Bericsen','Skagen 25','Stavanger','4006','Norway');"

    val inputIterator = List(inputLine1, inputLine2a, inputLine2b, inputLine3).toIterator
    
    val completefilePath = ""
      
    val input = List(("", inputIterator))
      
    val resultInput1 = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES " + "('Cardinal','Tom B. Erichsen','Skagen 21','Stavanger','4006','Norway');" 
    val resultInput2 = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) \nVALUES ('Cardinal','Eric Tombsen','Skagen 23','Stavanger','4006','Norway');"
    val resultInput3 = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES ('Cardinal','Benne Bericsen','Skagen 25','Stavanger','4006','Norway');"
    
    val statement1 = InsertStatement(resultInput1)
    val statement2 = InsertStatement(resultInput2)
    val statement3 = InsertStatement(resultInput3)
    
    val expected = List(MixedStatementsFile(completefilePath, List(statement1, statement2, statement3), Map.empty))
    
    val result = uh.createMixedStatementsFiles(input)
    
    assertEquals("Creation of mixed statements files with standard data did not work", expected, result)
  }
}