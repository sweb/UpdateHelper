package fm.scala.lib

import org.junit.Test
import org.junit.Assert._
import fm.scala.common.TestEnvironment

@Test
class TestSQLFileFolder {
  
  implicit val env = TestEnvironment()
  
  val folderName = "test_input\\testFolder"
  val files: List[(String, Boolean, Iterator[String])] = List(("test_input\\testFolder\\testFile1.sql", true, List("wasd").toIterator))
  
  val ffForSingleFile = SQLFileFolder(folderName, files)
  
  @Test def getFileIteratorsForSingleFile = {
    val expected = List(("test_input\\testFolder\\testFile1.sql", List("wasd")))
    val result = ffForSingleFile.getFileIterators.map(x => (x._1, x._2.toList))

    assertEquals("File iterator creation did not work", expected, result)
  }
  
  @Test def createBodyForSingleFile = {
    val expected = List("@@testFolder\\testFile1.sql").mkString("\n")
    val result = ffForSingleFile.createBody

    assertEquals(expected, result)
  }
  
  @Test def fileNameWithPathForSingleFile = {
    val expected = Map("output" -> "result\\testFolder\\testFolder.SQL", "outputFolder" -> "result\\testFolder")
    val result = ffForSingleFile.fileNameWithPath

    assertEquals(expected, result)
  }
  
  @Test def fileNameWithExtensivePathForSingleFile = {
    val folderName = "test_input\\additional_folder\\testFolder"
    val ffForSingleFile = SQLFileFolder(folderName, files)
    
    val expected = Map("output" -> "result\\additional_folder\\testFolder\\testFolder.SQL", "outputFolder" -> "result\\additional_folder\\testFolder")
    val result = ffForSingleFile.fileNameWithPath

    assertEquals(expected, result)
  }
  
  @Test def getOutputFolder = {
    val expected = "result\\testFolder"
    val result = ffForSingleFile.getOutputFolder

    assertEquals(expected, result)
  }
  
  @Test def createBodyForMultipleFiles = {
    val files: List[(String, Boolean, Iterator[String])] = List(("test_input\\testFolder\\testFile1.sql", true, List("wasd").toIterator),
        ("test_input\\testFolder\\testFile2.sql", true, List("wasd").toIterator),
        ("test_input\\testFolder\\testFile3.sql", true, List("wasd").toIterator))
    val ffForSingleFile = SQLFileFolder(folderName, files)
    
    val expected = List("@@testFolder\\testFile1.sql", 
        "@@testFolder\\testFile2.sql", 
        "@@testFolder\\testFile3.sql").mkString("\n")
    val result = ffForSingleFile.createBody

    assertEquals(expected, result)
  }
  
  @Test def createBodyForMultipleFilesAndFolders = {
    val files: List[(String, Boolean, Iterator[String])] = List(("test_input\\testFolder\\testFile1.sql", true, List("wasd").toIterator),
        ("test_input\\testFolder\\testFile2.sql", true, List("wasd").toIterator),
        ("test_input\\testFolder\\testFile3.sql", true, List("wasd").toIterator),
        ("test_input\\testFolder\\testFolder", false, List("wasd").toIterator))
    val ffForSingleFile = SQLFileFolder(folderName, files)
    
    val expected = List("@@testFolder\\testFile1.sql", 
        "@@testFolder\\testFile2.sql", 
        "@@testFolder\\testFile3.sql", 
        "@@testFolder\\testFolder\\testFolder.SQL").mkString("\n")
    val result = ffForSingleFile.createBody

    assertEquals(expected, result)
  }

}