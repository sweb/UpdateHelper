package fm.scala.common


/**
 * This trait groups utilities like loggers or configuration information and
 * may be passed to classes, which require these utilities.
 */
trait Environment {
  
  def configuration: Configuration
  def logger: LoggingManager

}

/**
 * This class implements the trait Environment and is used to provide utilities
 * for the actual application.
 */
case class ApplicationEnvironment(configurationFileName: String) extends Environment {
  val configuration = XMLConfiguration(configurationFileName)
  val logger = LoggingManager(configuration.isDebuggingEnabled)
}

/**
 * This class implements the trait Environment and is used to provide utilities
 * for unit tests.
 */
case class TestEnvironment extends Environment {
  val configuration = TestConfiguration()
  val logger = LoggingManager(configuration.isDebuggingEnabled)
}