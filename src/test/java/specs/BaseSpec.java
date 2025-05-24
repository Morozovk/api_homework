package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class BaseSpec {
    public static RequestSpecification requestBaseSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .contentType(JSON);

    public static ResponseSpecification baseResponseSpec(int statusCode) {
        return new ResponseSpecBuilder()
                .log(STATUS)
                .log(BODY)
                .expectStatusCode(statusCode)
                .build();
    }
}
