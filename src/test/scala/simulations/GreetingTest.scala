package simulations

import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class GreetingTest extends Simulation {

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

  before(println("---- Start ----"))
  setUp(
    scn.inject(
      /*run only once*/
      atOnceUsers(1)

      /*run only once in xx sec*/
      //constantUsersPerSec(1).during(5.seconds),

      /*run as concurrent in xx sec*/
      //constantConcurrentUsers(1).during(5.seconds)

      /*run as concurrent and hold for a while (with ramp up)*/
      //rampUsersPerSec(1).to(50).during(1.minutes),
      //constantUsersPerSec(50).during(5.minutes),
      //rampUsersPerSec(50).to(1).during(1.minutes)
    )
  ).protocols(httpProtocol)
  after(println("---- Done ----"))

}