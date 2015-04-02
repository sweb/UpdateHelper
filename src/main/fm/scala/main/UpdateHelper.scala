package fm.scala.main

import fm.scala.common.Constants
import fm.scala.business.UpdateController
import fm.scala.common.ApplicationEnvironment
import fm.scala.common.ApplicationConfigurer

object UpdateHelper extends App {
  println("-" * 80)
  println(Constants.APPLICATION_NAME)
  println("Version " + Constants.VERSION)
  println("Last update: " + Constants.LAST_MODIFICATION)
  println("In case of bugs or missing features, please contact florian.mueller")
  println("-" * 80)
//  println("Current configuration:")
//  println(Constants.toString)
  println("-" * 80)
  
  val parsedArguments = parseArguments(args)
  
  implicit val env = ApplicationEnvironment(getConfigIfAvailable(parsedArguments))
  
  val updateController = UpdateController()
  println("-" * 80)
  
  val fileParameter = parsedArguments.get("file")
  
  fileParameter match {
    case None => updateController.showResults
    case Some(x) => updateController.replaceSingleFile(x)
  }
  
  println("-" * 80)
  println("Update creation completed!")
  
  def getConfigIfAvailable(parsedArgs: Map[String, String]): String = {
    parsedArgs.get("config").getOrElse(Constants.STANDARD_CONFIGURATION_FILE)
  }
  
  def parseArguments(args: Array[String]): Map[String, String] = {
    ApplicationConfigurer(args).parseArgs
  }

}