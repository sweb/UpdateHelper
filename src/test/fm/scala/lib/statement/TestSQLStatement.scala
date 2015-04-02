package fm.scala.lib.statement

import org.junit.Test
import org.junit.Assert._

@Test
class TestSQLStatement {

  @Test
  def enhanceInsertStatement = {
    
    val input = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES " + "('Cardinal','Tom B. Erichsen','Skagen 21','Stavanger','4006','Norway');" 
    
    val is = InsertStatement(input)
    
    val expected = List(" "*2 + "BEGIN", 
        " "*4 + "execute immediate '",
        "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES " + "(''Cardinal'',''Tom B. Erichsen'',''Skagen 21'',''Stavanger'',''4006'',''Norway'')';",
        " "*4 + "COMMIT;",
    " "*2 + "EXCEPTION",
    " "*4 + "WHEN dup_val_on_index",
    " "*4 + "THEN dbms_output.PUT_LINE('Data is already present in the table " + "" + "! '||replace(sqlerrm,'ORA-'));",
    " "*2 + "END;").mkString("\n")
    
    val result = is.enhance
    
    assertEquals("Enhancement of insert statement did not work", expected, result)
  }
  
  @Test
  def enhanceInsertStatementWithSlashAtTheEnding = {
    
    val input = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES " + "('Cardinal','Tom B. Erichsen','Skagen 21','Stavanger','4006','Norway')/" 
    
    val is = InsertStatement(input)
    
    val expected = List(" "*2 + "BEGIN", 
        " "*4 + "execute immediate '",
        "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES " + "(''Cardinal'',''Tom B. Erichsen'',''Skagen 21'',''Stavanger'',''4006'',''Norway'')';",
        " "*4 + "COMMIT;",
    " "*2 + "EXCEPTION",
    " "*4 + "WHEN dup_val_on_index",
    " "*4 + "THEN dbms_output.PUT_LINE('Data is already present in the table " + "" + "! '||replace(sqlerrm,'ORA-'));",
    " "*2 + "END;").mkString("\n")
    
    val result = is.enhance
    
    assertEquals("Enhancement of insert statement did not work", expected, result)
  }
  
  @Test
  def enhanceUpdateStatement = {
    
    val input = "UPDATE Customers SET ContactName='Alfred Schmidt', City='Hamburg' WHERE CustomerName='Alfreds Futterkiste';" 
    
    val is = UpdateStatement(input)
    
    val expected = List(" "*4 + "BEGIN", 
        " "*6 + "execute immediate '",
        "UPDATE Customers SET ContactName=''Alfred Schmidt'', City=''Hamburg'' WHERE CustomerName=''Alfreds Futterkiste''';",
        " "*6 + "COMMIT;",
     " "*4 + "EXCEPTION",
    " "*6 + "WHEN dup_val_on_index",
    " "*6 + "THEN dbms_output.PUT_LINE('Data is already present in the table + " + "" + " +! '||replace(sqlerrm,'ORA-'));",
    " "*4 + "END;").mkString("\n")
    
    val result = is.enhance
    
    assertEquals("Enhancement of update statement did not work", expected, result)
  }
}