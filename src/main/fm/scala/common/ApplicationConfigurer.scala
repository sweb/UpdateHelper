package fm.scala.common

/**
 * This class is used to identify configuration parameters in the 
 * applications's arguments, passed during the start of the application.
 * Parameters are passed using the notation 'key=value'. As a result, a map
 * containing all parameters is returned by calling the parseArgs function.
 */
case class ApplicationConfigurer(args: Array[String]) {
  def parseArgs: Map[String, String] = {
    args.map(x => parseParameterInArgument(x)).toMap
  }
  
  def parseParameterInArgument(argument: String): (String, String) = {
    val parameter = argument.split('=')
    if (parameter.size != 2)
      throw new Exception("Incorrect parameter format! Synthax: arg=value")
    (parameter.head, parameter.last)
  }
}