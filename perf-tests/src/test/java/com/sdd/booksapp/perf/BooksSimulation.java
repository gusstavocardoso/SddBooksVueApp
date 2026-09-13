package com.sdd.booksapp.perf;

import com.sdd.booksapp.perf.scenarios.BookScenarios;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class BooksSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/json")
            .userAgentHeader("Gatling/Performance Test");

    {
        setUp(
            BookScenarios.browsingScenario.injectOpen(
                rampUsers(100).during(10) // 100 users for browsing over 10s
            ),
            BookScenarios.crudScenario.injectOpen(
                rampUsers(50).during(15) // 50 users doing full CRUD over 15s
            )
        )
        .protocols(httpProtocol)
        // Global assertions to fail the build if performance degrades
        .assertions(
            global().responseTime().percentile3().lt(500), // 95th percentile under 500ms
            global().successfulRequests().percent().gt(99.0) // less than 1% failure
        );
    }
}
