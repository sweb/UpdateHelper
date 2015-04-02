package fm.scala.common

/**
 * This trait is used to print logging information in a specified format. It is
 * possible to print info lines, warning lines and debug lines, which need to 
 * be enabled by a parameter. 
 */
trait Logger {
    def isDebuggingEnabled: Boolean
    def printDebugLine(text: String): Unit
    def printInfo(text: String): Unit
    def printWarning(text: String): Unit
  }
/**
 * This class implements the trait Logger and is used to print logging 
 * information to the console.
 */
case class ConsoleLogger(val isDebuggingEnabled: Boolean) extends Logger {
  def printDebugLine(text: String) = {
    if (isDebuggingEnabled) println(text)
  }

  def printInfo(text: String) = {
    println(text)
  }

  def printWarning(text: String) = {
    println("WARN: " + text)
  }
}
/**
 * This class is used to manage the different loggers. It is possible to add
 * loggers and logging calls are executed for every added logger.
 */
case class LoggingManager(isDebuggingEnabled: Boolean) {
  var logger: List[Logger] = Nil
  addConsoleLogger
  
  def addConsoleLogger: Unit = {
    if (logger.contains(ConsoleLogger(isDebuggingEnabled))) {
      printWarning("No need for second console logger!")
    } else {
      logger = ConsoleLogger(isDebuggingEnabled) :: logger
    }
  }

  def printDebugLine(text: String) =
    logger.foreach(_.printDebugLine(text))

  def printInfo(text: String) =
    logger.foreach(_.printInfo(text))

  def printWarning(text: String) =
    logger.foreach(_.printWarning(text))
}