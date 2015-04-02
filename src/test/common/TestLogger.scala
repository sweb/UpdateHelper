package common

import org.junit.Test
import org.junit.Assert._
import fm.scala.common.LoggingManager
import fm.scala.common.ConsoleLogger

@Test
class TestLogger {
  @Test def useInitialConsoleLogger = {
    val logMan = LoggingManager(true)
    
    val expected = List(ConsoleLogger(true))
    
    val res = logMan.logger
    
    assertEquals(expected, res)
  }
  
  @Test def addAdditionalConsoleLogger = {
    val logMan = LoggingManager(true)
    logMan.addConsoleLogger
    
    val expected = List(ConsoleLogger(true))
    
    val res = logMan.logger
    
    assertEquals(expected, res)
  }

}