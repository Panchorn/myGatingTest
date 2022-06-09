package simulations

import ch.qos.logback.classic.{Level}
import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import org.slf4j
import org.slf4j.LoggerFactory

import scala.concurrent.duration.DurationInt

class GreetingTest extends Simulation {

  val logger: slf4j.Logger = LoggerFactory.getLogger(classOf[GreetingTest])


  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")

  val scn: ScenarioBuilder = scenario("JSON")
    .exec(
      http("greeting api")
        .get("http://localhost:8080/hello")
        .check(status.in(200))
        .check(jsonPath("$.status").is("Hello from RESTEasy Reactive"))
    )

  before(logger.info("---- Start ----"))
  setUp(
    scn.inject(
      /*run only once*/
      //atOnceUsers(1)

      /*run only once in xx sec*/
      //constantUsersPerSec(1).during(5.seconds),

      /*run as concurrent in xx sec*/
      constantConcurrentUsers(1).during(5.seconds)

      /*run as concurrent and hold for a while (with ramp up)*/
      //rampUsersPerSec(1).to(50).during(1.minutes),
      //constantUsersPerSec(50).during(5.minutes),
      //rampUsersPerSec(50).to(1).during(1.minutes)
    )
  ).protocols(httpProtocol)
  after(logger.info("---- Done ----"))

}