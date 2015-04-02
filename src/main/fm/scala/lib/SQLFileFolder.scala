package fm.scala.lib

import java.io.File
import fm.scala.common.Environment
import fm.scala.common.RegExStatement._

case class SQLFileFolder(absoluteFolderPath: String, files: List[(String, Boolean, Iterator[String])])(implicit val env: Environment) extends SQLFile {
  
  def getFileIterators: List[(String, Iterator[String])] = files.filter(_._2).map(x => (x._1, x._3))
  
  def createBody: String = {
    
    (files.filter(_._2).map(x => "@@" + retrieveFolderName(x._1)._1) ++ 
        files.filterNot(_._2).map(x => "@@" + retrieveFolderName(x._1)._1 + "\\" + retrieveFolderName(x._1)._2 + ".SQL")).mkString("\n")
  }
  
  def createHeader: String = createGenerationInfo("") 
  
  def fileNameWithPath: Map[String,String] = {
    
    val (wholeRelativePath, folderName) = retrieveFolderName(absoluteFolderPath)
    
    Map("output" -> (env.configuration.exportPath + "\\" + wholeRelativePath + "\\" + folderName + ".SQL"),
        "outputFolder" -> (env.configuration.exportPath + "\\" + wholeRelativePath)
        )
  }
  
  def getOutputFolder: String = fileNameWithPath("outputFolder")
  
  private def retrieveFolderName(absoluteFolderPath: String): (String, String) = {
    val fileNameRegEx = "(?i)((?<=" + env.configuration.inputPath + "\\\\)(.*\\\\)*(.*))$"
    
    val regex = new scala.util.matching.Regex(fileNameRegEx, "wholeRelativePath", "relativePath", "folderName")
    val regexResult = regex.findFirstMatchIn(absoluteFolderPath)
    
    val wholeRelativePath = regexResult.getOrElse(sys.error("Could not parse filename " + absoluteFolderPath)).group("wholeRelativePath")
    val folderName = regexResult.getOrElse(sys.error("Could not parse filename " + absoluteFolderPath)).group("folderName")
    
    (wholeRelativePath, folderName)
  }

  private def retrieveFilename(filepath: String): (String, String, String) = {
    
    val fileNameRegEx = "(?i)((?<=" + env.configuration.inputPath + "\\\\)(.*)\\\\(.*\\.sql|tab)\\s*)$"
    
    val regex = new scala.util.matching.Regex(fileNameRegEx, "wholeRelativePath", "relativePath", "filename")
    val regexResult = regex.findFirstMatchIn(filepath)

    (regexResult.getOrElse(sys.error("Could not parse filename " + filepath)).group("wholeRelativePath"),
      regexResult.getOrElse(sys.error("Could not parse filename " + filepath)).group("relativePath"),
      regexResult.getOrElse(sys.error("Could not parse filename " + filepath)).group("filename"))
  }

}