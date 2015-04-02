package fm.scala.business

import org.junit.Test
import org.junit.Assert._
import fm.scala.common.TestEnvironment

class TestFileHandler {
  
  implicit val env = TestEnvironment()
  
  val fileHandler = FileHandler()
  
  //TODO enable
  @Test def testGetFiles = {
    
    val res = fileHandler.getFiles("testfiles").map(_.toString)
    val expected = List("testfiles\\File8.sql",
      "testfiles\\folder1\\File1.sql",
      "testfiles\\folder1\\File2.sql",
      "testfiles\\folder1\\File3.sql",
      "testfiles\\folder2\\File4.sql",
      "testfiles\\folder2\\File5.sql",
      "testfiles\\folder3\\File6.sql",
      "testfiles\\folder3\\File7.sql",
      "testfiles\\input\\folder1\\File1.sql",
      "testfiles\\input\\folder1\\File2.sql",
      "testfiles\\input\\folder1\\File3.sql")
    assertEquals("File listing did not work", expected, res)
  }
  
  @Test
  def getSingleFile = {
    val result = fileHandler.getSingleFile("test_input/testfile1.sql").toString
    val expected = "test_input\\testfile1.sql"
    assertEquals(expected, result)
  }
  
//  @Test def testGetFilesOnUnix = {
//    
//    val res = fileHandler.getFiles("testfiles").map(_.toString)
//    val expected = List("testfiles/File8.sql",
//      "testfiles/folder1/File1.sql",
//      "testfiles/folder1/File2.sql",
//      "testfiles/folder1/File3.sql",
//      "testfiles/folder2/File4.sql",
//      "testfiles/folder2/File5.sql",
//      "testfiles/folder3/File6.sql",
//      "testfiles/folder3/File7.sql",
//      "testfiles/input/folder1/File1.sql",
//      "testfiles/input/folder1/File2.sql",
//      "testfiles/input/folder1/File3.sql")
//    assertEquals("File listing on unix-system did not work", expected, res)
//  }
  
//  TODO: enable  
  @Test def getFolders = {
    val actual = fileHandler.getFolders().map(_.toString).toSet
    val expected = Set("test_input", 
        "test_input\\testFolder1", 
        "test_input\\testFolder2", 
        "test_input\\testFolder1\\testFolder11", 
        "test_input\\testFolder1\\testFolder12", 
        "test_input\\testFolder2\\testFolder21")
    
    assertEquals(expected, actual)
  }
  
//    @Test def getFoldersOnUnix = {
//    val actual = fileHandler.getFolders().map(_.toString).toSet
//    val expected = Set("test_input", 
//        "test_input/testFolder1", 
//        "test_input/testFolder2", 
//        "test_input/testFolder1/testFolder11", 
//        "test_input/testFolder1/testFolder12", 
//        "test_input/testFolder2/testFolder21")
//    
//    assertEquals(expected, actual)
//  }
//  TODO: Enable
  @Test def getIteratorsOfFolders = {
    val res = fileHandler.getIteratorsOfFolders.head
    val actual = (res._1, res._2.map(x => (x._1, x._2)))
    val expected = ("test_input", List(("test_input\\testfile1.sql", true), ("test_input\\testFolder1", false), ("test_input\\testFolder2", false)))
    
    assertEquals(expected, actual)
  }
  
//  @Test def getIteratorsOfFoldersOnUnix = {
//    val res = fileHandler.getIteratorsOfFolders.head
//    val actual = (res._1, res._2.map(x => (x._1, x._2)))
//    val expected = ("test_input", List(("test_input/testfile1.sql", true), ("test_input/testFolder1", false), ("test_input/testFolder2", false)))
//    
//    assertEquals(expected, actual)
//  }
}