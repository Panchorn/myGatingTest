import ch.qos.logback.classic.{Level, Logger, LoggerContext}
import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder
import org.slf4j.LoggerFactory

object Engine extends App {

  LoggerFactory
    .getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME)
    .asInstanceOf[Logger]
    .setLevel(Level.INFO)

  val props = new GatlingPropertiesBuilder()
    .resourcesDirectory(IDEPathHelper.mavenResourcesDirectory.toString)
    .resultsDirectory(IDEPathHelper.resultsDirectory.toString)
    .binariesDirectory(IDEPathHelper.mavenBinariesDirectory.toString)

  Gatling.fromMap(props.build)
}
