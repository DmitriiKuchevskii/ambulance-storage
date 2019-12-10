import com.ambulance.domain.Sex;
import com.ambulance.web.AmbulanceApi;
import com.ambulance.web.requests.AuthenticationRequest;
import com.ambulance.web.requests.NewPatientRequest;
import com.ambulance.web.requests.NewUserRequest;
import com.ambulance.web.requests.PatientIdRequest;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

interface IApiMethods {
    Response login(AuthenticationRequest authenticationRequest);

    Response addPatient(NewPatientRequest newPatientRequest);

    Response getPatient(PatientIdRequest patientIdRequest);

    Response getAllPatients();

    Response adminGetPatient(PatientIdRequest patientIdRequest);

    Response adminGetAllPatients();

    Response adminRemovePatient(PatientIdRequest patientIdRequest);

    Response adminAddNewUser(NewUserRequest newUserRequest);
}

@Component
class ApiMethods implements IApiMethods
{
    private static final String HOST = "http://localhost:4782";

    private final Header authHeader =  new Header("JWT",
            login(new AuthenticationRequest(
                     Optional.ofNullable(System.getenv("APPLICATION_DEFAULT_ADMIN_NAME")).orElse("")
                   , Optional.ofNullable(System.getenv("APPLICATION_DEFAULT_ADMIN_PASS")).orElse("")
                )).body().path("token"));

    private static RequestSpecification call(Object reqBody) {
        return given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(reqBody)
            .when();
    }

    private String userApi(String api) {
        return HOST + AmbulanceApi.API_USER_ROOT_REQUEST_MAP +  api;
    }

    private String adminApi(String api) {
        return HOST + AmbulanceApi.API_ADMIN_ROOT_REQUEST_MAP + api;
    }

    private RequestSpecification authCall(Object reqBody) {
        return call(reqBody).header(authHeader);
    }

    @Override
    public Response login(AuthenticationRequest authenticationRequest) {
        return call(authenticationRequest)
                .post(HOST + AmbulanceApi.API_LOGIN_ROOT_REQUEST_MAP + AmbulanceApi.API_LOGIN);
    }

    @Override
    public Response addPatient(NewPatientRequest newPatientRequest) {
        return authCall(newPatientRequest).post(userApi(AmbulanceApi.API_ADD_PATIENT));
    }

    @Override
    public Response getPatient(PatientIdRequest patientIdRequest) {
        return authCall(patientIdRequest).post(userApi(AmbulanceApi.API_GET_PATIENT));
    }

    @Override
    public Response getAllPatients() {
        return authCall(null).get(userApi(AmbulanceApi.API_GET_ALL_PATIENTS));
    }

    @Override
    public Response adminGetPatient(PatientIdRequest patientIdRequest) {
        return authCall(patientIdRequest).get(adminApi(AmbulanceApi.API_GET_PATIENT));
    }

    @Override
    public Response adminGetAllPatients() {
        return authCall(null).get(adminApi(AmbulanceApi.API_GET_ALL_PATIENTS));
    }

    @Override
    public Response adminRemovePatient(PatientIdRequest patientIdRequest) {
        return authCall(patientIdRequest).get(adminApi(AmbulanceApi.API_REMOVE_PATIENT));
    }

    @Override
    public Response adminAddNewUser(NewUserRequest newUserRequest) {
        return authCall(newUserRequest).get(adminApi(AmbulanceApi.API_ADD_NEW_USER));
    }
}

@Configuration
class TestBeansConfig {
    @Bean
    public IApiMethods apiMethods() {
        return new ApiMethods();
    }
}

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestBeansConfig.class)
@NoArgsConstructor
public class NewTest {

    @Autowired
    private IApiMethods api;

    private Response AddPatient() {
        NewPatientRequest newPatientRequest = NewPatientRequest.builder()
                .fullName("Full Name")
                .address("address")
                .date(new Date())
                .sex(Sex.MALE)
                .age(19L)
                .team("team")
                .code("code")
                .result("result")
                .regularPatient(false)
                .homeless(true)
                .data("data")
                .build();

        return api.addPatient(newPatientRequest);
    }

    private Response GetPatient(Long id) {
        return api.getPatient(new PatientIdRequest(id));
    }

    @Test
    public void AddTest() {
        AddPatient()
            .then().assertThat()
                .statusCode(HttpServletResponse.SC_OK)
            .and()
                .contentType(ContentType.JSON)
            .and()
                .body(matchesJsonSchema("{\"id\":\"0\",\"op\":\"0\"}"));
    }

    @Test
    public void GetPatientTest() {
        Long id = ((Integer)AddPatient().then().extract().path("id")).longValue();
        GetPatient(id).then()
            .assertThat()
                 .statusCode(HttpServletResponse.SC_OK)
            .and()
                .contentType(ContentType.JSON)
            .and()
                .body(matchesJsonSchema(""));
    }
}
