import com.ambulance.domain.Sex;
import com.ambulance.domain.hibernate.entities.Patient;
import com.ambulance.web.AmbulanceApi;
import com.ambulance.web.requests.AuthenticationRequest;
import com.ambulance.web.requests.NewPatientRequest;
import com.ambulance.web.requests.NewUserRequest;
import com.ambulance.web.requests.PatientIdRequest;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

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

class ApiMethods implements IApiMethods
{
    private static final String HOST = "http://localhost:4782/api/";
    private static final String DEFAULT_ADMIN_USERNAME = System.getenv("APPLICATION_DEFAULT_ADMIN_NAME");
    private static final String DEFAULT_ADMIN_PASSWORD = System.getenv("APPLICATION_DEFAULT_ADMIN_PASS");
    private final Header AUTH_HEADER = new Header("JWT",
            login(new AuthenticationRequest(DEFAULT_ADMIN_USERNAME, DEFAULT_ADMIN_PASSWORD)).body().path("token"));

    private static RequestSpecification call(Object reqBody) {
        return given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(reqBody)
            .when();
    }

    private RequestSpecification authCall(Object reqBody) {
        return call(reqBody).header(AUTH_HEADER);
    }

    @Override
    public Response login(AuthenticationRequest authenticationRequest) {
        return call(Map.of("username", authenticationRequest.getUsername(),
                           "password", authenticationRequest.getPassword()))
                .post(HOST + AmbulanceApi.API_LOGIN);
    }

    @Override
    public Response addPatient(NewPatientRequest newPatientRequest) {
        Patient patient = Patient.builder()
                .fullName(newPatientRequest.getFullName())
                .address(newPatientRequest.getAddress())
                .age(newPatientRequest.getAge())
                .code(newPatientRequest.getCode())
                .data(newPatientRequest.getData())
                .homeless(newPatientRequest.getHomeless())
                .regularPatient(newPatientRequest.getRegularPatient())
                .result(newPatientRequest.getResult())
                .sex(newPatientRequest.getSex())
                .date(newPatientRequest.getDate())
                .team(newPatientRequest.getTeam())
                .build();
        return authCall(patient).post(HOST + AmbulanceApi.API_ADD_PATIENT);
    }

    @Override
    public Response getPatient(PatientIdRequest patientIdRequest) {
        return null;
    }

    @Override
    public Response getAllPatients() {
        return null;
    }

    @Override
    public Response adminGetPatient(PatientIdRequest patientIdRequest) {
        return null;
    }

    @Override
    public Response adminGetAllPatients() {
        return null;
    }

    @Override
    public Response adminRemovePatient(PatientIdRequest patientIdRequest) {
        return null;
    }

    @Override
    public Response adminAddNewUser(NewUserRequest newUserRequest) {
        return null;
    }

//    @Override
//    public Response get() {
//        return authCall("").get(apiUrl);
//    }
//
//    @Override
//    public Response delete(Object reqBody) {
//        return authCall(reqBody).delete(apiUrl);
//    }
}

public class NewTest {

    private final ApiMethods api = new ApiMethods();

    private Response add() {
        NewPatientRequest newPatientRequest = new NewPatientRequest(
             new Date()
            ,"team"
            , "code"
            ,"result"
            , Sex.MALE
            , 19L
            , "Full Name"
            , "address"
            , false
            , true
            , "data"
        );

        return api.addPatient(newPatientRequest);
    }

    @Test
    public void AddTest() {
        add()
            .then().assertThat()
                .statusCode(HttpServletResponse.SC_OK)
            .and()
                .contentType(ContentType.JSON)
            .and()
                .body(matchesJsonSchema("{\"id\":\"0\",\"op\":\"0\"}"));
    }
}
