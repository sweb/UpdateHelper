package fm.scala.lib.statement
import org.junit.Test
import org.junit.Assert._
import fm.scala.common.TestEnvironment

class TestSQLStatementParser {
  
  implicit val env = TestEnvironment()
  
  @Test
  def parseSingleInsertStatement = {
    val statement1a = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES "
    val statement1b = "('Cardinal','Tom B. Erichsen','Skagen 21','Stavanger','4006','Norway');"

    val input = List(statement1a, statement1b).toIterator
    
    val expected = List(InsertStatement(statement1a + "\n" + statement1b))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals("Parsing of single insert statement did not work", expected, result)
  }
  
  @Test
  def parseSingleInsertStatementWithLineBreak = {
    val statement1a = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES \n"
    val statement1b = "('Cardinal','Tom B. Erichsen','Skagen 21','Stavanger','4006','Norway');"

    val input = List(statement1a, statement1b).toIterator

    val expected = List(InsertStatement(statement1a + "\n" + statement1b))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals("Parsing of single insert statement did not work", expected, result)
  }
  
  @Test
  def parseMultipleInsertStatements = {
    val statement1 = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES ('Cardinal','Tom B. Erichsen','Skagen 21','Stavanger','4006','Norway');"
    val statement2 = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES \n('Cardinal','Eric Tombsen','Skagen 23','Stavanger','4006','Norway');"
    val statement3 = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES ('Cardinal','Benne Bericsen','Skagen 25','Stavanger','4006','Norway');"

    val input = List(statement1, statement2, statement3).toIterator

    val expected = List(InsertStatement(statement1), 
        InsertStatement(statement2), 
        InsertStatement(statement3))
    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals("Parsing of multiple insert statements did not work", expected, result)

  }
  
  @Test
  def parseSingleUpdateStatement = {

    val statement1 = "UPDATE Customers SET ContactName='Alfred Schmidt', City='Hamburg' WHERE CustomerName='Alfreds Futterkiste';"

    val input = List(statement1).toIterator
    val expected = List(UpdateStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals("Parsing of single update statement did not work", expected, result)
  }
  
  @Test
  def parseSingleCreateTableStatement = {
    val statement1 = List("CREATE TABLE Persons",
      "(",
      "PersonID int,",
      "LastName varchar(255),",
      "FirstName varchar(255),",
      "Address varchar(255),",
      "City varchar(255)",
      ");").mkString("\n")

    val input = List(statement1).toIterator
    val expected = List(CreateTableStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals("Parsing of single create table statement did not work", expected, result)
  }
  
  @Test
  def parseSingleDropTableStatement = {
    val statement1 = "DROP TABLE Persons;"

    val input = List(statement1).toIterator
    val expected = List(DropTableStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals("Parsing of single drop table statement did not work", expected, result)
  }
  
  @Test
  def parseSingleAddColumnToTableStatement = {
    val statement1 = "ALTER TABLE Persons \nADD DateOfBirth date;"

    val input = List(statement1).toIterator
    val expected = List(AlterTableStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals("Parsing of single alter table statement to add a column did not work", expected, result)
  }
  
  @Test
  def parseSingleCreateViewStatement = {
    val statement1 = List("CREATE VIEW Current_Product_List AS",
      "SELECT ProductID,ProductName",
      "FROM Products",
      "WHERE Discontinued=No;").mkString("\n")

    val input = List(statement1).toIterator
    val expected = List(CreateViewStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals("Parsing of single create view statement did not work", expected, result)
  }
  
  @Test
  def parseSingleCreateOrReplaceViewStatement = {
    val statement1 = List("CREATE OR REPLACE VIEW Current_Product_List AS",
      "SELECT ProductID,ProductName",
      "FROM Products",
      "WHERE Discontinued=No;").mkString("\n")

    val input = List(statement1).toIterator
    val expected = List(CreateViewStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals("Parsing of single create or replace view statement did not work", expected, result)
  }
  
  @Test
  def parseSingleCreateOrReplaceForceViewStatement = {
    val statement1 = List("CREATE OR REPLACE FORCE VIEW Current_Product_List AS",
      "SELECT ProductID,ProductName",
      "FROM Products",
      "WHERE Discontinued=No;").mkString("\n")

    val input = List(statement1).toIterator
    val expected = List(CreateViewStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals("Parsing of single create or replace view statement did not work", expected, result)
  }
  
  @Test
  def parseSingleDropViewStatement = {
    val statement1 = "DROP VIEW Current_Product_List;"

    val input = List(statement1).toIterator
    val expected = List(DropViewStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals("Parsing of single drop view statement did not work", expected, result)
  }
  
  @Test
  def parseSingleCreateProcedureStatement = {
    val statement1 = List("CREATE PROCEDURE spins IS",
      "BEGIN",
      "INSERT INTO tdept (deptno, deptname, mgrno, admrdept)",
      "VALUES ('A00', 'SPIFFY COMPUTER DIV.', '000010', 'A00');",
      "INSERT INTO tdept (deptno, deptname, mgrno, admrdept)",
      "VALUES ('B01', 'PLANNING            ', '000020', 'A00');",
      "END;",
      "/").mkString("\n")

    val input = List(statement1).toIterator
    val expected = List(CreateProcedureStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals("Parsing of single create procedure statement did not work", expected, result)
  }
  
  @Test
  def parseSingleCreateOrReplaceProcedureStatement = {
    val statement1 = List("CREATE OR REPLACE PROCEDURE spins IS",
      "BEGIN",
      "INSERT INTO tdept (deptno, deptname, mgrno, admrdept)",
      "VALUES ('A00', 'SPIFFY COMPUTER DIV.', '000010', 'A00');",
      "INSERT INTO tdept (deptno, deptname, mgrno, admrdept)",
      "VALUES ('B01', 'PLANNING            ', '000020', 'A00');",
      "END;",
      "/").mkString("\n")

    val input = List(statement1).toIterator
    val expected = List(CreateProcedureStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals("Parsing of single create or replace procedure statement did not work", expected, result)
  }
  
  @Test
  def parseSingleDropProcedureStatement = {
    val statement1 = "DROP PROCEDURE spins;"

    val input = List(statement1).toIterator
    val expected = List(DropProcedureStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals("Parsing of single drop procedure statement did not work", expected, result)
  }
  
  @Test
  def parseComplexCreateProcedureStatementWithSchema = {
    val statement1 = List("create or replace ",
"procedure updateBueMapping ",
"as",
"last_csv_timestamp DATE;",
"new_csv_timestamp DATE;",
"actual_mapping_date DATE;",
"begin",
"  -- Get date from the last CVS delivery from the configuration",
"  select NVL(TO_DATE(DTF_FNK.get_konfig('BUE', 'LAST_CSV_TIMESTAMP'), 'DD.MM.YY HH24:MI:SS'), SYSDATE) INTO last_csv_timestamp from dual;",
"  -- Get actual date from the CVS delivery",
"  select TO_DATE(max(gueltig_ab), 'DD.MM.YY HH24:MI:SS') INTO new_csv_timestamp from SBUEE1_SAP_MAPPING_SV;",
" ",
"  -- If the date are not identical",
"  IF (last_csv_timestamp <> new_csv_timestamp) THEN",
"   -- get the new validity date (now)",
"   SELECT SYSDATE into actual_mapping_date FROM DUAL;",
" ",
"    -- Set all the actual valid mapping to end",
"    UPDATE BUE_MAPPING ",
"       SET gueltig_bis = actual_mapping_date",
"     WHERE gueltig_bis is null ",
"        OR gueltig_bis = to_date('09.09.9999', 'DD.MM.YYYY');",
" ",
"    -- Insert the new mapping with the validity now    ",
"    INSERT INTO BUE_MAPPING(MAPPING_ID, KONTOART, KONTOTYP, KUNDENTYP, PRODUKTTYP, IFRS_KATEGORIE,",
"                            PRODUKTTYP_HI, KUNDENTYP_HI, FINANCIAL_CODE, BESCHREIBUNG, ",
"                            GUELTIG_AB, GUELTIG_BIS, BEZUGSZEITRAUM,",
"                            CUSTOM01, CUSTOM02, CUSTOM03, CUSTOM04, CUSTOM05)",
"    SELECT MAPPING_ID, KONTOART, KONTOTYP, KUNDENTYP, PRODUKTTYP, IFRS_KATEGORIE,",
"                            PRODUKTTYP_HI, KUNDENTYP_HI, FINANCIAL_CODE, BESCHREIBUNG, ",
"                            actual_mapping_date, null, BEZUGSZEITRAUM,",
"                            CUSTOM01, CUSTOM02, CUSTOM03, CUSTOM04, CUSTOM05",
"     FROM SBUEE1_SAP_MAPPING_SV;",
" ",
"  END IF;",
" ",
"  DTF_FNK.set_konfig ('BUE', 'LAST_CSV_TIMESTAMP', to_char(new_csv_timestamp, 'DD.MM.YYYY HH24:MI:SS'), 'Last Value exported cvs mapping file');",
" ",
"end updateBueMapping;"
,
      "/").mkString("\n")

    val input = List(statement1).toIterator
    val expected = List(CreateProcedureStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements
    

    assertEquals("Parsing of single create procedure statement did not work", expected, result)
  }
  
  @Test
  def parseSingleCreateFunctionStatement = {
    val statement1 = List("CREATE FUNCTION employer_details_func",
      "RETURN VARCHAR(20);",
      "IS",
      "emp_name VARCHAR(20);",
      "BEGIN",
      "SELECT first_name INTO emp_name",
      "FROM emp_tbl WHERE empID = '100';",
      "RETURN emp_name;",
      "END;",
      "/ ").mkString("\n")

    val input = List(statement1).toIterator
    val expected = List(CreateFunctionStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals("Parsing of single create function statement did not work", expected, result)
  }
  
  @Test
  def parseSingleCreateOrReplaceFunctionStatement = {
    val statement1 = List("CREATE OR REPLACE FUNCTION employer_details_func",
      "RETURN VARCHAR(20);",
      "IS",
      "emp_name VARCHAR(20);",
      "BEGIN",
      "SELECT first_name INTO emp_name",
      "FROM emp_tbl WHERE empID = '100';",
      "RETURN emp_name;",
      "END;",
      "/ ").mkString("\n")


    val input = List(statement1).toIterator
    val expected = List(CreateFunctionStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals("Parsing of single create or replace function statement did not work", expected, result)
  }
  
  @Test
  def parseSingleDropFunctionStatement = {
    val statement1 = "DROP FUNCTION spins;"

    val input = List(statement1).toIterator
    val expected = List(DropFunctionStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals("Parsing of single drop function statement did not work", expected, result)
  }
  
  @Test
  def parseSingleAlterSequenceStatement = {
    val statement1 = "ALTER SEQUENCE eseq MAXVALUE 1500;"

    val input = List(statement1).toIterator
    val expected = List(GenericStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
  
  @Test
  def parseSingleAlterSessionStatement = {
    val statement1 = "ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY MM DD HH24:MI:SS';"

    val input = List(statement1).toIterator
    val expected = List(GenericStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
  
  @Test
  def parseSingleAlterTriggerStatement = {
    val statement1 = "ALTER TRIGGER reorder DISABLE;"

    val input = List(statement1).toIterator
    val expected = List(AlterTriggerStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
  
  @Test
  def parseSingleAlterUserStatement = {
    val statement1 = "ALTER USER todd IDENTIFIED BY lion;"

    val input = List(statement1).toIterator
    val expected = List(GenericStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
  
  @Test
  def parseSingleAlterViewStatement = {
    val statement1 = "ALTER VIEW customer_view COMPILE;"

    val input = List(statement1).toIterator
    val expected = List(GenericStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
  
  @Test
  def parseSingleCreateIndexStatement = {
    val statement1 = "CREATE INDEX SAL_INDEX ON EMP(SAL);"

    val input = List(statement1).toIterator
    val expected = List(CreateIndexStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
  
  @Test
  def parseSingleCreateSequenceStatement = {
    val statement1 = "CREATE SEQUENCE ESEQ INCREMENT BY 10;"

    val input = List(statement1).toIterator
    val expected = List(CreateSequenceStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
  
  @Test
  def parseSingleCreateSynonymStatement = {
    val statement1 = "CREATE SYNONYM PROD FOR SCOTT.PRODUCT;"

    val input = List(statement1).toIterator
    val expected = List(GenericStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
  
  @Test
  def parseSingleCreateTriggerStatement = {
    val statement1 = "CREATE TRIGGER SAL_CHECK BEFORE UPDATE OF SAL ON EMP FOR EACH ROW \nEMP_SAL(NEW.SAL); "

    val input = List(statement1).toIterator
    val expected = List(CreateTriggerStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
  
  @Test
  def parseSingleDropIndexStatement = {
    val statement1 = "DROP INDEX PAY_IDX;"

    val input = List(statement1).toIterator
    val expected = List(GenericDropStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
  
  @Test
  def parseSingleDropSequenceStatement = {
    val statement1 = "DROP SEQUENCE ESEQ;"

    val input = List(statement1).toIterator
    val expected = List(GenericDropStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
  
  @Test
  def parseSingleDropSynonymStatement = {
    val statement1 = "DROP SYNONYM PROD;"

    val input = List(statement1).toIterator
    val expected = List(GenericDropStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
  
  @Test
  def parseSingleDropTriggerStatement = {
    val statement1 = "DROP TRIGGER ruth.reorder;"

    val input = List(statement1).toIterator
    val expected = List(GenericDropStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
  
  @Test
  def parseSingleTruncateStatement = {
    val statement1 = "TRUNCATE TABLE emp;"

    val input = List(statement1).toIterator
    val expected = List(GenericStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
  
  @Test
  def parseSingleTruncStatement = {
    val statement1 = "TRUNC TABLE emp;"

    val input = List(statement1).toIterator
    val expected = List(GenericStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
  
  @Test
  def parseSingleDeleteStatement = {
    val statement1 = "DELETE FROM PRICE WHERE MINPRICE < 2.4;"

    val input = List(statement1).toIterator
    val expected = List(DeleteStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }

  @Test
  def parseCommitStatement = {
    val statement1 = "COMMIT;"

    val input = List(statement1).toIterator
    val expected = List(GenericStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
  
  @Test
  def parseRollbackStatement = {
    val statement1 = "ROLLBACK;"

    val input = List(statement1).toIterator
    val expected = List(GenericStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
  
  @Test
  def parseExecStatement = {
    val statement1 = "exec mfrwn_gen_tables.create_table;"

    val input = List(statement1).toIterator
    val expected = List(GenericStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
  
  @Test
  def parseExecuteStatement = {
    val statement1 = "execute mfrwn_gen_tables.create_table;"

    val input = List(statement1).toIterator
    val expected = List(GenericStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
  
  @Test
  def parseAnonStatement = {
    val statement1 = List("DECLARE",
      "BEGIN",
      "NULL;",
      "END;",
      "/").mkString("\n")

    val input = List(statement1).toIterator
    val expected = List(GenericStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
  
  @Test
  def parsePromptStatement = {
    val statement1 = "PROMPT ***********************************************************"

    val input = List(statement1).toIterator
    val expected = List(GenericStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
  
  @Test
  def parseWheneverStatement = {
    val statement1 = "whenever oserror exit"

    val input = List(statement1).toIterator
    val expected = List(GenericStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
  
  @Test
  def parseMavenStatement = {
    val statement1 = "@@DEPLOY/SETUP.SQL PHO INSTALL 1050000 \"${project.name} ${project.version} (${buildNumber})\""

    val input = List(statement1).toIterator
    val expected = List(GenericStatement(statement1))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
  
  @Test
  def parseAfterCommentStatement = {
    val statement1 = List("/**************** Installiere OCS/CCS *************************/",
"DEF ADM=&CENTRAL_MODUL_ID.",
"DEF SCHEMA=ADM_BASE",
"DEF MODUL_ID=PHO",
"@@PHO/INSTALL/ADM/INSTALL.SQL")

    val input = statement1.toIterator
    val expected = List(GenericStatement("/**************** Installiere OCS/CCS *************************/"),
        GenericStatement("DEF ADM=&CENTRAL_MODUL_ID."),
        GenericStatement("DEF SCHEMA=ADM_BASE"),
        GenericStatement("DEF MODUL_ID=PHO"),
        GenericStatement("@@PHO/INSTALL/ADM/INSTALL.SQL"))

    val sqlSP = SQLStatementParser(input)
    val result = sqlSP.parseStatements

    assertEquals(expected, result)
  }
}