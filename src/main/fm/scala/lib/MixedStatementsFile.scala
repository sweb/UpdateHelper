package fm.scala.lib

import fm.scala.lib.statement.SQLStatement
import fm.scala.common.Constants
import fm.scala.common.Environment
import fm.scala.common.RegExStatement._

case class MixedStatementsFile(completefilePath: String, statements: List[SQLStatement], tags: Map[String, String])(implicit val env: Environment) extends SQLFile{
  def createBody: String = (statements map (_.enhance)).mkString("\n") 
  
  val description = tags.get("descr").getOrElse("")
      
  def createHeader = createHeader(description)
  
  def createHeader(description: String): String = {
    List(createGenerationInfo(description),
    "WHENEVER SQLERROR EXIT 1;",
    "WHENEVER OSERROR EXIT 2;").mkString("\n")
  }
  def fileNameWithPath: Map[String,String] = {
    
    val fileNameRegEx = "(?i)((?<=" + env.configuration.inputPath + "\\\\)((.*)\\\\)*(.*\\.sql|tab)\\s*)$"
    
    val regex = new scala.util.matching.Regex(fileNameRegEx, "wholeRelativePath", "relativePath1", "relativePath2", "filename")
    val regexResult = regex.findFirstMatchIn(completefilePath)
    
    Map("output" -> (env.configuration.exportPath + "\\" + regexResult.getOrElse(sys.error("Could not parse filename " + completefilePath)).group("wholeRelativePath")),
        "outputFolder" -> (env.configuration.exportPath + nvl(regexResult.getOrElse(sys.error("Could not parse filename " + completefilePath)).group("relativePath2")))
        )
  }
  def getOutputFolder: String = fileNameWithPath("outputFolder")
  
  
  private def nvl(string: String) =
    if (string == null) "" else "\\" + string
}