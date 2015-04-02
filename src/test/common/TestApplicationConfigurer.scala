package common

import org.junit.Test
import org.junit.Assert._
import fm.scala.common.ApplicationConfigurer
import org.junit.rules.ExpectedException

class TestApplicationConfigurer {
  
  @Test def parseConfigParameter = {
    
    val configurer = ApplicationConfigurer(Array("config=test_config.xml"))
    val actual = configurer.parseArgs
    
    val expected = Map("config" -> "test_config.xml")
    
    assertEquals(expected, actual)
  }
  
  @Test def parseConfigAndFileNameParameter = {
    
    val configurer = ApplicationConfigurer(Array("config=test_config.xml", "file=test.sql"))
    val actual = configurer.parseArgs
    
    val expected = Map("config" -> "test_config.xml", "file" -> "test.sql")
    
    assertEquals(expected, actual)
  }
  
  @Test ( expected = classOf[ Exception] ) 
  def parseFalseArgument = {
    
    val configurer = ApplicationConfigurer(Array("config-test_config.xml"))
    val actual = configurer.parseArgs
  }
  
  @Test def parseNoParameter = {
    
    val configurer = ApplicationConfigurer(Array.empty)
    val actual = configurer.parseArgs
    
    val expected = Map.empty
    
    assertEquals(expected, actual)
  }

}