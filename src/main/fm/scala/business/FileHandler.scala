package fm.scala.business

import fm.scala.lib._
import fm.scala.common._
import fm.scala.common.RegExStatement._
import java.io._
import scala.annotation.tailrec
/**
 * This class is used to read input files from the file system and write result
 * files to the file system. It is possible to import all folders and files of
 * a defined folder, as well as importing only a given file. The result files
 * need to be defined as SQLFile. 
 */
case class FileHandler(implicit env: Environment) {

  /**
   * Recursively searches all files in the input folder
   */
  def getFiles(startFolder: String = env.configuration.inputPath): List[File] = {
    @tailrec
    def tailRecursiveListFiles(accu: Array[File],
                               currentFiles: Array[File]): Array[File] = {
      if (currentFiles.isEmpty)
        accu
      else
        tailRecursiveListFiles(accu ++ currentFiles.filter(_.isFile()),
          currentFiles.filter(_.isDirectory()).flatMap(x => x.listFiles()))
    }

    val file = new File(startFolder)
    tailRecursiveListFiles(Array.empty[File], Array(file)).toList
  }

  def getIteratorsOfFiles: List[(String, Iterator[String])] =
    getFiles().map(x => (x.toString, scala.io.Source.fromFile(x.toString).getLines()))

  def getFolders(startFolder: String = env.configuration.inputPath): List[File] = {
    @tailrec
    def tailRecursiveListFiles(accu: Array[File],
                               currentFiles: Array[File]): Array[File] = {
      if (currentFiles.isEmpty)
        accu
      else
        tailRecursiveListFiles(accu ++ currentFiles.filter(_.isDirectory()),
          currentFiles.filter(_.isDirectory()).flatMap(x => x.listFiles()))
    }

    val file = new File(startFolder)
    tailRecursiveListFiles(Array.empty[File], Array(file)).toList//.filterNot(_ == file)
  }
  
  def getSingleFile(fileName: String): File = {
    new File(fileName)
  }
  
  def getIteratorsOfFolders: List[(String, List[(String, Boolean, Iterator[String])])] = {
    getFolders().map(x => (x.toString, x.listFiles().map(y => (y.toString(), y.isFile(), if (y.isFile()) scala.io.Source.fromFile(y.toString).getLines() else Iterator.empty)).toList))
  }
    
  def createResultFile(sqlFile: SQLFile): Unit = {
    val dir = new File(sqlFile.getOutputFolder)
    val file = new File(sqlFile.fileNameWithPath("output"))
    dir.mkdirs

    val (header, body, footer) = sqlFile.createFileTextFragments

    printToFile(file, false)(p => { p.println(header) })
    printToFile(file, true)(p => { p.println(body) })
    printToFile(file, true)(p => { p.println(footer) })
  }
  
  def replaceInputFileWithResultFile(sqlFile: SQLFile, fileName: String): Unit = {
    val file = new File(fileName)
    
    val (header, body, footer) = sqlFile.createFileTextFragments

    printToFile(file, false)(p => { p.println(header) })
    printToFile(file, true)(p => { p.println(body) })
    printToFile(file, true)(p => { p.println(footer) })
  }

  def printToFile(f: java.io.File, isAppending: Boolean)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(new BufferedWriter(new FileWriter(f, isAppending)))
    try { op(p) } finally { p.close }
  }

}