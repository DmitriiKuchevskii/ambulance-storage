import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBody;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class NewTest {

    private static final String HOST = "http://localhost:4782/";
    private static final String DEFAULT_ADMIN_USERNAME = System.getenv("APPLICATION_DEFAULT_ADMIN_NAME");
    private static final String DEFAULT_ADMIN_PASSWORD = System.getenv("APPLICATION_DEFAULT_ADMIN_PASS");

    @Test
    public void LoginTest()
    {
        final String LOGIN_HOST = HOST + "login";
            given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(Map.of("username", DEFAULT_ADMIN_USERNAME, "password", DEFAULT_ADMIN_PASSWORD))
            .when()
                .post(LOGIN_HOST)
            .then()
                .assertThat()
                        .statusCode(HttpServletResponse.SC_OK)
                    .and()
                        .contentType(ContentType.JSON)
                    .and()
                        .body("username", equalTo(DEFAULT_ADMIN_USERNAME))
                    .and();
    }

}
