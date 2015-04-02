package fm.scala.test

//import org.junit.Test
//import org.junit.Assert._
//import fm.scala.common.TestEnvironment
//import fm.scala.common.ConfigurationComponentImpl.XMLConfiguration
//
//class TestConstants{
//  
//  @Test def testRetrieveConstantFromXML = {
//    
//    val testConfiguration = <Configuration><inputPath>testinput</inputPath></Configuration>
//    val testConfiguration2 = <Configuration></Configuration>
//      
//    val configuration = new XMLConfiguration
//    
//    val inputPath = configuration.retrieveConstantFromXML("inputPath", "input", testConfiguration)
//    val inputPath2 = configuration.retrieveConstantFromXML("inputPath", "input", testConfiguration2)
//    
//    
//    val result = "testinput"
//    val result2 = "input"
//    
//    assertEquals("XML retrieval did not work",result, inputPath)
//    assertEquals("Standard replacement did not work",result2, inputPath2)
//  }
//  
//  @Test def testGetVersionString = {
//    val versionString = configuration.getVersionString("1000000")
//    val result = "1.0.0"
//
//    assertEquals("Build of version string did not work", result, versionString)
//  }
//
//}