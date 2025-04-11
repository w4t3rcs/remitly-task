package io.w4t3rcs.task;

import com.redis.testcontainers.RedisContainer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("default")
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SwiftCodeTestDataControllerTests {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15");
    @Container
    @ServiceConnection
    static GenericContainer<?> redisContainer = new RedisContainer("redis:7.4.2");
    @LocalServerPort
    private Integer port;

    @BeforeEach
    void prepare() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    @Order(1)
    void testPostBranchSwiftCodeEndpoint() {
        final String requestBody = """
                {
                    "address": "Test address",
                    "bankName": "Test bank",
                    "countryISO2": "PL",
                    "countryName": "Poland",
                    "isHeadquarter": "false",
                    "swiftCode": "IIZKLV22CAM"
                }
                """;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/v1/swift-codes")
                .then()
                .statusCode(Matchers.is(200))
                .body("message", Matchers.is("Swift code is successfully created - IIZKLV22CAM"));
    }

    @Test
    @Order(2)
    void testPostHeadquarterSwiftCodeEndpoint() {
        final String requestBody = """
                {
                    "address": "Test address",
                    "bankName": "Test bank",
                    "countryISO2": "PL",
                    "countryName": "Poland",
                    "isHeadquarter": "true",
                    "swiftCode": "IIZKLV22XXX"
                }
                """;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/v1/swift-codes")
                .then()
                .statusCode(Matchers.is(200))
                .body("message", Matchers.is("Swift code is successfully created - IIZKLV22XXX"));
    }

    @Test
    @Order(3)
    void testGetHeadquarterSwiftCodeEndpoint() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/swift-codes/IIZKLV22XXX")
                .then()
                .statusCode(Matchers.is(200))
                .body("address", Matchers.is("Test address"))
                .body("bankName", Matchers.is("Test bank"))
                .body("countryISO2", Matchers.is("PL"))
                .body("countryName", Matchers.is("POLAND"))
                .body("isHeadquarter", Matchers.is(true))
                .body("branches", Matchers.hasSize(1))
                .body("swiftCode", Matchers.is("IIZKLV22XXX"));
    }

    @Test
    @Order(4)
    void testGetBranchSwiftCodeEndpoint() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/swift-codes/IIZKLV22CAM")
                .then()
                .statusCode(Matchers.is(200))
                .body("address", Matchers.is("Test address"))
                .body("bankName", Matchers.is("Test bank"))
                .body("countryISO2", Matchers.is("PL"))
                .body("countryName", Matchers.is("POLAND"))
                .body("isHeadquarter", Matchers.is(false))
                .body("swiftCode", Matchers.is("IIZKLV22CAM"));
    }

    @Test
    @Order(5)
    void testGetSwiftCodesByCountryIso2Endpoint() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/swift-codes/country/PL")
                .then()
                .statusCode(Matchers.is(200))
                .body("countryISO2", Matchers.is("PL"))
                .body("countryName", Matchers.is("POLAND"))
                .body("swiftCodes", Matchers.hasSize(2));
    }

    @Test
    @Order(6)
    void testDeleteHeadquarterEndpoint() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/v1/swift-codes/IIZKLV22XXX")
                .then()
                .statusCode(Matchers.is(200))
                .body("message", Matchers.is("Swift code is successfully deleted - IIZKLV22XXX"));
    }

    @Test
    @Order(7)
    void testDeleteBranchEndpoint() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/v1/swift-codes/IIZKLV22CAM")
                .then()
                .statusCode(Matchers.is(200))
                .body("message", Matchers.is("Swift code is successfully deleted - IIZKLV22CAM"));
    }
}
