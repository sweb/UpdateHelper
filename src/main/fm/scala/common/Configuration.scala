package fm.scala.common

/**
 * This trait defines configurations. Configurations are used to provide
 * general configuration parameters for the whole application. Configuration 
 * can for example may be provided by the XML configuration file.
 */
trait Configuration {
    def inputPath: String

    def exportPath: String

    def updateVersion: String

    def updateVersionString: String

    def updateModule: String

    def updateDescription: String

    def printWarnings: Boolean

    def timestampFormat: String

    def timestamp = new java.text.SimpleDateFormat(timestampFormat).format(new java.util.Date())

    def updateRoot: String

    def schemataStoredIn: String

    def fileGenerationString: String
    
    def isDebuggingEnabled: Boolean
  }