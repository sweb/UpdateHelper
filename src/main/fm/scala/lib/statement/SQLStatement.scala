package fm.scala.lib.statement

import fm.scala.common.Environment

trait SQLStatement {
  
  def schemaName: Option[String] = None
  def objectName: String = ""
  def statement: String
  
  protected def header: String
  protected def footer: String
  
  protected def rules: List[String => String]
  
  override def toString = {
    List(schemaName + " - " + objectName + ":", statement, "-"*80).mkString("\n")
  }
  
  def enhance: String = {
    
    val enhancedStatement = modifyStatementByRules(statement, rules)

    List(header, enhancedStatement, footer).filterNot(_=="").mkString("\n")
  }
  
  private def modifyStatementByRules(statement: String, rules: List[String => String]): String = {
    if (rules isEmpty)
      statement
    else
      modifyStatementByRules(rules.head(statement), rules.tail)
  }

  protected def replaceSlashWithSemicolon(input: String): String = {
    input.replaceAll("/[\\s]*$", ";")
  }
  protected def addHighCommaInFrontOfSemicolon(input: String): String = {
    input.replaceAll(";[\\s]*$", "';")
  }

  protected def doubleHighComma(input: String): String = {
    input.replaceAll("'", "''")
  }

}

case class InsertStatement(val statement: String) extends SQLStatement {
  
  protected val header: String = List(" "*2 + "BEGIN", " "*4 + "execute immediate '").mkString("\n")
  
  protected val footer: String = List(" "*4 + "COMMIT;",
    " "*2 + "EXCEPTION",
    " "*4 + "WHEN dup_val_on_index",
    " "*4 + "THEN dbms_output.PUT_LINE('Data is already present in the table " + objectName + "! '||replace(sqlerrm,'ORA-'));",
    " "*2 + "END;").mkString("\n")

  val rules: List[String => String] =
      List(doubleHighComma, replaceSlashWithSemicolon, addHighCommaInFrontOfSemicolon)

}

case class DropTableStatement(val statement: String) extends SQLStatement {
  
  protected val header: String = List("DECLARE",
    " " * 2 + "TAB_DOES_NOT_EXIST   EXCEPTION;",
    " " * 2 + "PRAGMA EXCEPTION_INIT (TAB_DOES_NOT_EXIST, -00942);",
    " " * 2 + "lc_sql VARCHAR2(5000);	",
    "BEGIN",
    " " * 2 + "lc_sql := ' ").mkString("\n")
  
  protected val footer: String = List(" " * 2 + "execute immediate lc_sql;",
      "EXCEPTION",
      " " * 2 + "WHEN TAB_DOES_NOT_EXIST THEN",
      " " * 4 + "dbms_output.put_line(lc_sql);",
      " " * 4 + "DBMS_OUTPUT.put_line('Table does not exist! '||REPLACE(SQLERRM,'ORA-'));",
      "END;",
      "/").mkString("\n")

  val rules: List[String => String] =
      List(replaceSlashWithSemicolon, addHighCommaInFrontOfSemicolon)
}
//TODO: If column already exists
case class AlterTableStatement(val statement: String) extends SQLStatement {
  protected val header: String = List("DECLARE",
    " " * 2 + "TAB_DOES_NOT_EXIST   EXCEPTION;",
    " " * 2 + "PRAGMA EXCEPTION_INIT (TAB_DOES_NOT_EXIST, -00942);",
    " " * 2 + "lc_sql VARCHAR2(5000);	",
    "BEGIN",
    " " * 2 + "lc_sql := ' ").mkString("\n")
  
  protected val footer: String = List(" " * 2 + "execute immediate lc_sql;",
      "EXCEPTION",
      " " * 2 + "WHEN TAB_DOES_NOT_EXIST THEN",
      " " * 4 + "dbms_output.put_line(lc_sql);",
      " " * 4 + "DBMS_OUTPUT.put_line('Table does not exist! '||REPLACE(SQLERRM,'ORA-'));",
      "END;",
      "/").mkString("\n")

  val rules: List[String => String] =
      List(replaceSlashWithSemicolon, addHighCommaInFrontOfSemicolon)

}

case class CreateViewStatement(val statement: String) extends SQLStatement {
  protected val header = List("DECLARE",
	"ln_vorhanden NUMBER(10);",
	"BEGIN",
	"SELECT COUNT (1)",
	"INTO ln_vorhanden",
	"FROM all_objects",
	"WHERE object_type ='VIEW'",
	lookForSchema("AND   owner = upper( '","')"),
	"AND object_name=upper('"+ objectName +"');" + "\n\n" +
	" "*2 + "IF ln_vorhanden =1 THEN",
	" "*4 + "EXECUTE IMMEDIATE ('DROP VIEW  " + lookForSchema("", ".") + objectName +"') ;",
	" "*2 + "END IF;",
	"END;",
	"/",
	"-"*80,
	"PROMPT Create View " + objectName,
	"-"*80).filterNot(_=="").mkString("\n")
	
  protected val footer: String = "-" * 80
  
  val rules = Nil
  
  def lookForSchema(before: String, after: String) = {
    schemaName match {
      case Some(x) => before + x + after
      case None => ""
    }
  }
	
}

case class CreateTableStatement(val statement: String) extends SQLStatement {
  protected val header: String = List("DECLARE",
      " " * 2 + "TAB_EXISTS   EXCEPTION;",
      " " * 2 + "PRAGMA EXCEPTION_INIT (TAB_EXISTS, -00955);",
      " " * 2 + "CONS_EXISTS   EXCEPTION;",
      " " * 2 + "PRAGMA EXCEPTION_INIT (CONS_EXISTS, -02264);",
      " " * 2 + "PK_EXISTS   EXCEPTION;",
      " " * 2 + "PRAGMA EXCEPTION_INIT (PK_EXISTS, -02260);",
      " " * 2 + "FK_EXISTS   EXCEPTION;",
      " " * 2 + "PRAGMA EXCEPTION_INIT (FK_EXISTS, -02275);",
      " " * 2 + "TRIGGER_EXISTS   EXCEPTION;",
      " " * 2 + "PRAGMA EXCEPTION_INIT (TRIGGER_EXISTS, -04081);",
      " " * 2 + "lc_sql VARCHAR2(5000);	",
      "BEGIN",
      " " * 2 + "lc_sql := '").mkString("\n")
  
  protected val footer: String = List(" " * 2 + "execute immediate lc_sql;",
      "EXCEPTION",
      " " * 2 + "WHEN TAB_EXISTS OR CONS_EXISTS OR PK_EXISTS OR FK_EXISTS OR TRIGGER_EXISTS THEN",
      " " * 4 + "dbms_output.put_line(lc_sql);",
      " " * 4 + "DBMS_OUTPUT.put_line('Object already exists! '||REPLACE(SQLERRM,'ORA-'));",
      "END;").mkString("\n")
    
  val rules: List[String => String] =
    List(doubleHighComma, replaceSlashWithSemicolon, addHighCommaInFrontOfSemicolon)
}

case class UpdateStatement(val statement: String) extends SQLStatement {
  
  protected val header: String = List(" "*4 + "BEGIN", " "*6 + "execute immediate '").mkString("\n")
  
  protected val footer: String = List(" "*6 + "COMMIT;",
    " "*4 + "EXCEPTION",
    " "*6 + "WHEN dup_val_on_index",
    " "*6 + "THEN dbms_output.PUT_LINE('Data is already present in the table + " + objectName + " +! '||replace(sqlerrm,'ORA-'));",
    " "*4 + "END;").mkString("\n")
    
  val rules: List[String => String] =
    List(doubleHighComma, replaceSlashWithSemicolon, addHighCommaInFrontOfSemicolon)

}

case class UnknownStatement(val statement: String)(implicit env: Environment) extends SQLStatement {
    protected val header = if (env.configuration.printWarnings) "--WARNING: Statement was not parsed" else ""
    protected val footer = ""

    env.logger.printDebugLine("-"*80 + "Statement was not parsed:\n" + "-"*80 + statement + "\n" + "-"*80)

    val rules = Nil
  }

case class DropViewStatement(val statement: String) extends SQLStatement {
  
  protected val header = ""
  protected val footer = ""
  
  val rules = Nil
}

case class CreateProcedureStatement(val statement: String) extends SQLStatement {
  protected val header = ""
  protected val footer = ""
  
  val rules = Nil

}

case class DropProcedureStatement(val statement: String)(implicit env: Environment) extends SQLStatement {
  protected val header = if (env.configuration.printWarnings) "--WARNING: Usage of frw_tools.drop_objekt is recommended!" else ""
  protected val footer = ""

  env.logger.printDebugLine("-"*80 + "Statement:\n" + "-"*80 + statement + "\n" + "-"*80 + "Usage of frw_tools.drop_objekt is recommended!")

  val rules = Nil
}

case class CreateFunctionStatement(val statement: String) extends SQLStatement {
  protected val header = ""
  protected val footer = ""
  
  val rules = Nil
}

case class DropFunctionStatement(val statement: String)(implicit env: Environment) extends SQLStatement {
  protected val header = if (env.configuration.printWarnings) "--WARNING: Usage of frw_tools.drop_objekt is recommended!" else ""
  protected val footer = ""

  env.logger.printDebugLine("-"*80 + "Statement:\n" + "-"*80 + statement + "\n" + "-"*80 + "Usage of frw_tools.drop_objekt is recommended!")

  val rules = Nil
}

case class AlterTriggerStatement(val statement: String) extends SQLStatement {
  protected val header = ""
  protected val footer = ""
  
  val rules = Nil
}

case class CreateIndexStatement(val statement: String) extends SQLStatement {
  protected val header = ""
  protected val footer = ""
  
  val rules = Nil
}

case class CreateSequenceStatement(val statement: String) extends SQLStatement {
  protected val header = ""
  protected val footer = ""
  
  val rules = Nil
}

case class CreateTriggerStatement(val statement: String) extends SQLStatement {
  protected val header = ""
  protected val footer = ""
  
  val rules = Nil
}

case class DeleteStatement(val statement: String) extends SQLStatement {
  protected val header = ""
  protected val footer = ""
  
  val rules = Nil
}

case class GenericStatement(val statement: String) extends SQLStatement {
  protected val header = ""
  protected val footer = ""
  
  val rules = Nil
}

case class GenericDropStatement(val statement: String)(implicit env: Environment) extends SQLStatement {
  protected val header = if (env.configuration.printWarnings) "--WARNING: Usage of frw_tools.drop_objekt is recommended!" else ""
  protected val footer = ""

  env.logger.printDebugLine("-"*80 + "Statement:\n" + "-"*80 + statement + "\n" + "-"*80 + "Usage of frw_tools.drop_objekt is recommended!")

  val rules = Nil
}