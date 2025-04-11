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

@ActiveProfiles("batch")
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SwiftCodeCsvDataControllerTests {
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
    void testGetHeadquarterSwiftCodeEndpoint() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/swift-codes/AIZKLV22XXX")
                .then()
                .statusCode(Matchers.is(200))
                .body("address", Matchers.is("MIHAILA TALA STREET 1  RIGA, RIGA, LV-1045"))
                .body("bankName", Matchers.is("ABLV BANK, AS IN LIQUIDATION"))
                .body("countryISO2", Matchers.is("LV"))
                .body("countryName", Matchers.is("LATVIA"))
                .body("isHeadquarter", Matchers.is(true))
                .body("branches", Matchers.hasSize(1))
                .body("swiftCode", Matchers.is("AIZKLV22XXX"));
    }

    @Test
    @Order(2)
    void testGetHeadquarterWithoutBranchesSwiftCodeEndpoint() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/swift-codes/THRIBGS2XXX")
                .then()
                .statusCode(Matchers.is(200))
                .body("address", Matchers.is("OKOLOVRASTEN PAT 251G MM BUSINESS CENTER, FLOOR 4, MLADOST 4 SOFIA, SOFIA, 1715"))
                .body("bankName", Matchers.is("THRACIAN INVEST INC."))
                .body("countryISO2", Matchers.is("BG"))
                .body("countryName", Matchers.is("BULGARIA"))
                .body("isHeadquarter", Matchers.is(true))
                .body("swiftCode", Matchers.is("THRIBGS2XXX"));
    }

    @Test
    @Order(3)
    void testGetBranchSwiftCodeEndpoint() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/swift-codes/AIZKLV22CLN")
                .then()
                .statusCode(Matchers.is(200))
                .body("address", Matchers.is("ELIZABETES STREET 23  RIGA, RIGA, LV-1010"))
                .body("bankName", Matchers.is("ABLV BANK, AS IN LIQUIDATION"))
                .body("countryISO2", Matchers.is("LV"))
                .body("countryName", Matchers.is("LATVIA"))
                .body("isHeadquarter", Matchers.is(false))
                .body("swiftCode", Matchers.is("AIZKLV22CLN"));
    }

    @Test
    @Order(5)
    void testGetSwiftCodesByCountryIso2Endpoint() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/v1/swift-codes/country/BG")
                .then()
                .statusCode(Matchers.is(200))
                .body("countryISO2", Matchers.is("BG"))
                .body("countryName", Matchers.is("BULGARIA"))
                .body("swiftCodes", Matchers.hasSize(133));
    }
}
