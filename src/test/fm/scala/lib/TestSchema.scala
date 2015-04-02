//package fm.scala.lib
//
//import org.junit.Test
//import org.junit.Assert._
//import fm.scala.common.TestEnvironment
//
//class TestSchema {
//      implicit val env = TestEnvironment()
//  
//  @Test def testGetFiletypesInSchema() {
//    val file1 = fm.scala.lib.DataFile("","","", Nil)
//    val file2 = fm.scala.lib.DataFile("","","", Nil)
//    val file3 = fm.scala.lib.ViewFile("","","", Nil)
//    val file4 = fm.scala.lib.TableFile("","","", Nil)
//    
//    val schema1 = Schema("", "", List(file1, file2, file3, file4))
//    val schema2 = Schema("", "", List(file1))
//    val schema3 = Schema("", "", List(file1, file2))
//    val schema4 = Schema("", "", List(file1, file3))
//    val schema5 = Schema("", "", List(file1, file4))
//    val schema6 = Schema("", "", List(file3))
//    val schema7 = Schema("", "", List(file3, file1))
//    val schema8 = Schema("", "", List(file4, file3))
//    val schema9 = Schema("", "", List(file4))
//    val schema10 = Schema("", "", List(file3, file4))
//    
//    val result1 = List("DATA", "TABLE", "VIEW")
//    val result2 = List("DATA")
//    val result3 = List("TABLE")
//    val result4 = List("VIEW")
//    val result5 = List("DATA", "VIEW")
//    val result6 = List("DATA", "TABLE")
//    val result7 = List("VIEW", "TABLE")
//    
//    assertEquals("Testschema  1 does not list correct data types",result1.sorted, schema1.getFiletypesInSchema.sorted)
//    assertEquals("Testschema  2 does not list correct data types",result2.sorted, schema2.getFiletypesInSchema.sorted)
//    assertEquals("Testschema  3 does not list correct data types",result2.sorted, schema3.getFiletypesInSchema.sorted)
//    assertEquals("Testschema  4 does not list correct data types",result5.sorted, schema4.getFiletypesInSchema.sorted)
//    assertEquals("Testschema  5 does not list correct data types",result6.sorted, schema5.getFiletypesInSchema.sorted)
//    assertEquals("Testschema  6 does not list correct data types",result4.sorted, schema6.getFiletypesInSchema.sorted)
//    assertEquals("Testschema  7 does not list correct data types",result5.sorted, schema7.getFiletypesInSchema.sorted)
//    assertEquals("Testschema  8 does not list correct data types",result7.sorted, schema8.getFiletypesInSchema.sorted)
//    assertEquals("Testschema  9 does not list correct data types",result3.sorted, schema9.getFiletypesInSchema.sorted)
//    assertEquals("Testschema 10 does not list correct data types",result7.sorted, schema10.getFiletypesInSchema.sorted)
//    
//  }
//
//  @Test def testCreateHeader() {
//    val schema1 = Schema("", "", Nil)
//    val schema2 = Schema("", "test description", Nil)
//
//    val result1 = "-" * 80 + "\n" +
//      "-- " + env.configuration.fileGenerationString + "\n" +
//      "-" * 80
//
//    val result2 = "-" * 80 + "\n" +
//      "-- " + "test description" + "\n" +
//      "-- " + env.configuration.fileGenerationString + "\n" +
//      "-" * 80
//    // TODO: In SQLFile verschieben
//    assertEquals("Header creation without description not correct", result1, schema1.createHeader)
//    assertEquals("Header creation with description not correct", result2, schema2.createHeader)
//  }
//
//  @Test def createBody = {
//    val file1 = fm.scala.lib.DataFile("", "", "", Nil)
//    val file2 = fm.scala.lib.DataFile("", "", "", Nil)
//    val file3 = fm.scala.lib.ViewFile("", "", "", Nil)
//    val file4 = fm.scala.lib.TableFile("", "", "", Nil)
//
//    val schema1 = Schema("TEST", "", List(file1, file2, file3, file4))
//
//    val result1 = "@@" + env.configuration.updateRoot + "\\" + "TEST" + "\\" + "TABLE" + "\\" + "TABLE" + ".sql" + "\n" +
//      "@@" + configuration.updateRoot + "\\" + "TEST" + "\\" + "VIEW" + "\\" + "VIEW" + ".sql" + "\n" +
//      "@@" + configuration.updateRoot + "\\" + "TEST" + "\\" + "DATA" + "\\" + "DATA" + ".sql"
//
//    assertEquals("Body creation not correct", result1, schema1.createBody)
//  }
//  
//  @Test def testCreateSchemaBody() = {
//    val file1 = fm.scala.lib.DataFile("","","", Nil)
//    val file2 = fm.scala.lib.DataFile("","","", Nil)
//    val file3 = fm.scala.lib.ViewFile("","","", Nil)
//    val file4 = fm.scala.lib.TableFile("","","", Nil)
//    
//    val schema1 = Schema("SCHEMA_TEST", "", List(file1, file2, file3, file4))
//    val schema2 = Schema("SCHEMA_TEST", "", List(file1))
//    val schema3 = Schema("SCHEMA_TEST", "", List(file3))
//    val schema4 = Schema("SCHEMA_TEST", "", List(file4))
//    val schema5 = Schema("SCHEMA_TEST", "", List(file1, file3))
//    val schema6 = Schema("SCHEMA_TEST", "", List(file1, file4))
//    val schema7 = Schema("SCHEMA_TEST", "", List(file3, file4))
//    
//    val result1 = "@@" + env.configuration.updateRoot + "\\" + "SCHEMA_TEST" + "\\" + "TABLE\\" +
//    "TABLE.sql\n@@" + env.configuration.updateRoot + "\\" + "SCHEMA_TEST" + "\\" +  "VIEW\\" +
//    "VIEW.sql\n@@" + env.configuration.updateRoot + "\\" + "SCHEMA_TEST" + "\\" +  "DATA\\" +"DATA.sql"
//    val result2 = "@@" + env.configuration.updateRoot + "\\" + "SCHEMA_TEST" + "\\" +  "DATA\\" +"DATA.sql"
//    val result3 = "@@" + env.configuration.updateRoot + "\\" + "SCHEMA_TEST" + "\\" +  "VIEW\\" +"VIEW.sql"
//    val result4 = "@@" + env.configuration.updateRoot + "\\" + "SCHEMA_TEST" + "\\" +  "TABLE\\" +"TABLE.sql"
//    val result5 = "@@" + env.configuration.updateRoot + "\\" + "SCHEMA_TEST" + "\\" +  "VIEW\\" +
//    "VIEW.sql\n@@" + env.configuration.updateRoot + "\\" + "SCHEMA_TEST" + "\\" +  "DATA\\" +"DATA.sql"
//    val result6 = "@@" + env.configuration.updateRoot + "\\" + "SCHEMA_TEST" + "\\" +  "TABLE\\" +
//    "TABLE.sql\n@@" + env.configuration.updateRoot + "\\" + "SCHEMA_TEST" + "\\" +  "DATA\\" +"DATA.sql"
//    val result7 = "@@" + env.configuration.updateRoot + "\\" + "SCHEMA_TEST" + "\\" +  "TABLE\\" +
//    "TABLE.sql\n@@" + env.configuration.updateRoot + "\\" + "SCHEMA_TEST" + "\\" +  "VIEW\\" +"VIEW.sql"
//    
//    assertEquals("Testschema 1 does not create the correct schema body",result1, schema1.createBody)
//    assertEquals("Testschema 2 does not create the correct schema body",result2, schema2.createBody)
//    assertEquals("Testschema 3 does not create the correct schema body",result3, schema3.createBody)
//    assertEquals("Testschema 4 does not create the correct schema body",result4, schema4.createBody)
//    assertEquals("Testschema 5 does not create the correct schema body",result5, schema5.createBody)
//    assertEquals("Testschema 6 does not create the correct schema body",result6, schema6.createBody)
//    assertEquals("Testschema 7 does not create the correct schema body",result7, schema7.createBody)
//  }
//  
//  @Test def testCreateFooter() = {
//    val schema = Schema("", "", Nil)
//    
//    val result = "-" * 80
//      
//      assertEquals("Footer creation not correct",result, schema.createFooter)
//  }
//  
//  @Test def testCreateFiletypeCall() = {
//    val schema = Schema("SCHEMA_TEST", "", Nil)
//    
//    val result1 = ""
//    val result2 = "@@" + env.configuration.updateRoot + "\\" + "SCHEMA_TEST" + "\\" + "Test" + "\\" + "Test.sql"
//    
//    assertEquals("Filetype-call for empty type is not correct",result1, schema.createFiletypeCall(""))
//    assertEquals("Filetype-call for empty type is not correct",result2, schema.createFiletypeCall("Test"))
//  }
//  
//  @Test def testGroupFilesByFileType = {
//    val file1 = fm.scala.lib.DataFile("Data1","","", Nil)
//    val file2 = fm.scala.lib.DataFile("Data2","","", Nil)
//    val file3 = fm.scala.lib.ViewFile("View1","","", Nil)
//    val file4 = fm.scala.lib.TableFile("Table1","","", Nil)
//    
//    val schema1 = Schema("SCHEMA_TEST", "", List(file1, file2, file3, file4))
//    val schema2 = Schema("SCHEMA_TEST", "", List(file1))
//    val schema3 = Schema("SCHEMA_TEST", "", List(file3))
//    val schema4 = Schema("SCHEMA_TEST", "", List(file4))
//    val schema5 = Schema("SCHEMA_TEST", "", List(file1, file3))
//    val schema6 = Schema("SCHEMA_TEST", "", List(file1, file4))
//    val schema7 = Schema("SCHEMA_TEST", "", List(file3, file4))
//    
//    val result1 = Map("DATA" -> List(file1, file2), "VIEW" -> List(file3), "TABLE" -> List(file4))
//    val result2 = Map("DATA" -> List(file1))
//    val result3 = Map("VIEW" -> List(file3))
//    val result4 = Map("TABLE" -> List(file4))
//    val result5 = Map("DATA" -> List(file1), "VIEW" -> List(file3))
//    val result6 = Map("DATA" -> List(file1), "TABLE" -> List(file4))
//    val result7 = Map("VIEW" -> List(file3), "TABLE" -> List(file4))
//    
//    assertEquals("Filetype separation not correct",result1, schema1.groupFilesByFileType)
//    assertEquals("Filetype separation not correct",result2, schema2.groupFilesByFileType)
//    assertEquals("Filetype separation not correct",result3, schema3.groupFilesByFileType)
//    assertEquals("Filetype separation not correct",result4, schema4.groupFilesByFileType)
//    assertEquals("Filetype separation not correct",result5, schema5.groupFilesByFileType)
//    assertEquals("Filetype separation not correct",result6, schema6.groupFilesByFileType)
//    assertEquals("Filetype separation not correct",result7, schema7.groupFilesByFileType) 
//  }
//  
//  @Test def getFiletypesInSchemaTest = {
//    val file1 = fm.scala.lib.DataFile("","Data1","", Nil)
//    val file2 = fm.scala.lib.DataFile("","Data2","", Nil)
//    val file3 = fm.scala.lib.ViewFile("","View1","", Nil)
//    val file4 = fm.scala.lib.TableFile("","Table1","", Nil)
//    
//    val schema1 = Schema("SCHEMA_TEST", "", List(file1, file2, file3, file4))
//    val schema2 = Schema("SCHEMA_TEST", "", List(file1, file2, file4))
//    
//    val result1 = List("DATA", "VIEW", "TABLE").sorted
//    val result2 = List("DATA", "TABLE").sorted
//    
//    assertEquals("Filetype identification with 4 files not correct",result1, schema1.getFiletypesInSchema.sorted)
//    assertEquals("Filetype identification with 3 files not correct",result2, schema2.getFiletypesInSchema.sorted)
//  }
//  
//  @Test def createTypeCallFilesTest = {
//    import fm.scala.lib.TypeCallFile
//    val file1 = fm.scala.lib.DataFile("","Data1","", Nil)
//    val file2 = fm.scala.lib.DataFile("","Data2","", Nil)
//    val file3 = fm.scala.lib.ViewFile("","View1","", Nil)
//    val file4 = fm.scala.lib.TableFile("","Table1","", Nil)
//    
//    val schema1 = Schema("", "", List(file1, file2, file3, file4))
//    val tcf1 = TypeCallFile("", "DATA", List(file1, file2))
//    val tcf2 = TypeCallFile("", "VIEW", List(file3))
//    val tcf3 = TypeCallFile("", "TABLE", List(file4))
//    
//    val result1 = List(tcf2,tcf3, tcf1)
//    
//    assertEquals("Filetype call files not correct",result1, schema1.createTypeCallFiles)
//  }
//  
//  @Test def getOutputFolderTest = {
//    val schema1 = Schema("TEST", "",Nil)
//    val result1 = env.configuration.schemataStoredIn + "\\" + "TEST"
//    
//    assertEquals("Output folder not correct",result1, schema1.getOutputFolder)
//  }
//  
//  @Test def fileNameWithPathTest = {
//    val schema1 = Schema("TEST", "",Nil)
//    val result1 = Map("output" -> (env.configuration.schemataStoredIn + "\\" + "TEST" + "\\" + "TEST" + ".sql"))
//    
//    assertEquals("Filename with path not correct",result1, schema1.fileNameWithPath)
//  }
//
//}