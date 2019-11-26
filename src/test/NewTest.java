import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class NewTest {

    private static final String HOST = "http://localhost:4782/";
    private static final String DEFAULT_ADMIN_USERNAME = System.getenv("APPLICATION_DEFAULT_ADMIN_NAME");
    private static final String DEFAULT_ADMIN_PASSWORD = System.getenv("APPLICATION_DEFAULT_ADMIN_PASS");
    private final String TEST_TOKEN = login().body().path("token");

    private Response login() {
        return given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(Map.of("username", DEFAULT_ADMIN_USERNAME, "password", DEFAULT_ADMIN_PASSWORD))
        .when()
             .post(HOST + "login");
    }

    @Test
    public void LoginTest() {
        login().then()
            .assertThat()
                .statusCode(HttpServletResponse.SC_OK)
            .and()
                .contentType(ContentType.JSON)
            .and()
                .body("username", equalTo(DEFAULT_ADMIN_USERNAME))
                .body("token", notNullValue())
            .and();
    }

    @Test
    public void AddTest() {

        Map<String, String> patient = new HashMap<>();
        patient.put("date", "2000-10-25");
        patient.put("team","team");
        patient.put("code", "code");
        patient.put("result","result");
        patient.put("sex", "MALE");
        patient.put("age", "19");
        patient.put("fullName", "Full Name");
        patient.put("address", "address");
        patient.put("regularPatient", "false");
        patient.put("homeless", "true");
        patient.put("data", "data");

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(patient)
        .when()
            .post(HOST + "api/Add")
        .then().assertThat()
            .statusCode(HttpServletResponse.SC_OK)
        .and()
            .contentType(ContentType.JSON)
        .and();


    }

}
