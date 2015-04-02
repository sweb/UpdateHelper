package fm.scala.lib

import org.junit.Test
import org.junit.Assert._
import fm.scala.lib.statement.InsertStatement
import java.io.File
import fm.scala.common.TestEnvironment

@Test
class TestMixedStatementsFile {
      implicit val env = TestEnvironment()
  
  @Test
  def createMixedStatementsFile = {
    // val msf = MixedStatementsFile(statements)
  }
  
  @Test
  def createOutputFilenameWithRelativePath = {
    
    val msf = MixedStatementsFile("D:\\testpath\\test_input\\TEST_SCHEMA\\TEST_FOLDER\\FILE.SQL", Nil, Map.empty)
    
    val result = msf.fileNameWithPath("output")
    
    val expected = "result\\TEST_SCHEMA\\TEST_FOLDER\\FILE.SQL"
      
    assertEquals("Creation of of output filename with relative path did not work", expected, result)
  }
  
  @Test
  def createOutputFilenameWithShortPath = {
    
    val msf = MixedStatementsFile("test_input\\FILE.SQL", Nil, Map.empty)
    
    val result = msf.fileNameWithPath("output")
    
    val expected = "result\\FILE.SQL"
      
    assertEquals("Creation of of output filename with short path did not work", expected, result)
  }
  
  @Test
  def getOutputFolderWithShortPath = {
    
    val msf = MixedStatementsFile("test_input\\FILE.SQL", Nil, Map.empty)
    
    val result = msf.getOutputFolder
    
    val expected = "result"
      
    assertEquals("Get output folder with short path did not work", expected, result)
  }
  
  @Test
  def createOutputFilenameWithRealFile = {
    
    val file = new File("testfiles\\test_input\\folder1\\File1.sql")
    
    val msf = MixedStatementsFile(file.toString(), Nil, Map.empty)
    
    val result = msf.fileNameWithPath("output")
    
    val expected = "result\\folder1\\File1.sql"
      
    assertEquals("Creation of of output filename with real file did not work", expected, result)
  }
  
  @Test
  def getDescriptionDispiteNoTags = {
    val file = new File("testfiles\\test_input\\folder1\\File1.sql")
    
    val msf = MixedStatementsFile(file.toString(), Nil, Map.empty)
    
    val result = msf.description
    
    val expected = ""
      
    assertEquals("Return of description with no tags did not work", expected, result)
  }
  
  @Test
  def getDescriptionDispiteDescriptionTag = {
    val file = new File("testfiles\\test_input\\folder1\\File1.sql")
    
        val tags =Map(("descr"->"Inserts PDI-Property values"), ("expl"->"test1 testalot"), ("wasd"->"Inserts PDI-Property values"))
    
    val msf = MixedStatementsFile(file.toString(), Nil, tags)
    
    val result = msf.description
    
    val expected = "Inserts PDI-Property values"
      
    assertEquals("Return of description with tag did not work", expected, result)
  }

}