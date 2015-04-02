package fm.scala.lib
import fm.scala.common.Constants
import fm.scala.common.Environment

trait SQLFile {
  
  def env: Environment
  
  /**
   * Creates a short generation text string, including a description, the 
   * version number and a timestamp.
   */
  def createGenerationInfo(description: String): String = {
    List("-" * 80, 
        "-- " + description, 
        "-- " + env.configuration.fileGenerationString, 
        "-" * 80).filter(_ != "-- ").mkString("\n")
  }
  
  def getOutputFolder: String
  def fileNameWithPath: Map[String, String]
  /**
   * Creates a header for the output file. Needs to be implemented by extending 
   * classes.
   */
  def createHeader: String
  /**
   * Creates the body for the output file. Needs to be implemented by extending 
   * classes.
   */
  def createBody: String
  /**
   * Creates a footer for the output file. Needs to be implemented by extending 
   * classes.
   */
  def createFooter: String = "-" * 80
  
  /**
   * Returns header, body and footer of the respective file
   */
  def createFileTextFragments =
    (createHeader, createBody, createFooter)

}