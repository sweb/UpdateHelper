package fm.scala.business

import fm.scala.common.Constants
import fm.scala.lib.MixedStatementsFile
import fm.scala.common.Environment
import fm.scala.lib.SQLFileFolder
import fm.scala.lib.statement.SQLStatementParser

/**
 * This class is the main controller of the UpdateHelper. It provides means to
 * import files, pass the files to the parser and transform the results into
 * SQL-file objects. The SQL files may then be stored with defined wrappers in
 * the file system.
 */
case class UpdateController(implicit env: Environment) {
  type FilenameAndIterator = (String, Iterator[String])
  type FileInformation = (String, Boolean, Iterator[String])

  private val fileHandler = FileHandler()
  
  private val folderIterators = fileHandler.getIteratorsOfFolders
  
  private val folders = createFolders(folderIterators)

  def createMixedStatementsFiles(
    listOfFilenamesWithIterators: List[FilenameAndIterator]): List[MixedStatementsFile] = {
    listOfFilenamesWithIterators.map{
        nameAndIterator => val (name, iterator) = nameAndIterator
        createMixedStatementFile(name, iterator)}
  }
  
  private def createFolders(folders: List[(String, List[FileInformation])]): List[SQLFileFolder] = {
    folders.map(folder => SQLFileFolder(folder._1, folder._2))
  }
  
  def showResults = {
    writeFilesToFileSystem
    writeFolderFilesToSystem
  }
  
  def replaceSingleFile(filename: String) = {
    val file = fileHandler.getSingleFile(filename)
    val fullFilename = file.toString
    val mixedStatementFile = createMixedStatementFile(fullFilename, scala.io.Source.fromFile(fullFilename).getLines)
    
    fileHandler.replaceInputFileWithResultFile(mixedStatementFile, filename)
  }
  
  private def createMixedStatementFile(fileName: String, fileIterator: Iterator[String]): MixedStatementsFile = {   
    val statementParser = SQLStatementParser(fileIterator)
    val listOfParsedStatements = statementParser.parseStatements
    
    MixedStatementsFile(fileName, listOfParsedStatements, Map.empty)
  }
  
  private def writeFilesToFileSystem = {

    val mixedStatementsFiles =
      folders.map(folder =>
        createMixedStatementsFiles(folder.files.filter(y => y._2).map(z => (z._1, z._3)))).flatten

    mixedStatementsFiles.foreach(file => fileHandler.createResultFile(file))
  }
  
  private def writeFolderFilesToSystem = {
    val foldersOnSecondLevel = folders.filterNot(_.absoluteFolderPath == env.configuration.inputPath)
    foldersOnSecondLevel.foreach(file => fileHandler.createResultFile(file))
  }
}