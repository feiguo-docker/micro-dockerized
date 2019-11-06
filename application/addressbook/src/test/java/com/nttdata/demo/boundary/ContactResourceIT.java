package com.nttdata.demo.boundary;

import java.util.UUID;

import com.nttdata.demo.entity.Contact;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.BeforeClass;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;


public class ContactResourceIT {

  @BeforeClass
  public static void setup() {
    RestAssured.baseURI = "http://" + getHost();
    RestAssured.port = getPort();
    RestAssured.basePath = "/addressbook/api";
    RestAssured.replaceFiltersWith(ResponseLoggingFilter.responseLogger(), new RequestLoggingFilter());
  }

  @Test
  public void create_and_read() {

    final Response createResponse = createContact();

    final String location = createResponse.getHeader("location");
    final Response readResponse = readContact(location);

    readResponse.then().statusCode(OK.getStatusCode());
  }

  @Test
  public void create_and_delete() {

    final Response createResponse = createContact();
    final ResponseBody body = createResponse.getBody();
    final String print = body.print();

    final String location = createResponse.getHeader("location");

    given().delete(location).then().statusCode(OK.getStatusCode());
  }

  private Response readContact(String location) {
    return given().
        contentType(ContentType.JSON).
        when().
        get(location);
  }

  private Response createContact() {
    final Contact contact = buildPerson();
    final Response createResponse = createContact(contact);
    createResponse.then().statusCode(CREATED.getStatusCode());
    return createResponse;
  }

  private Response createContact(Contact contact) {
    return given().
        body(contact).
        contentType(ContentType.JSON).
        when().
        post("/contacts");
  }


  private Contact buildPerson() {
    final Contact contact = new Contact();
    final UUID uuid = UUID.randomUUID();
    contact.setFirstName("Max-" + uuid);
    contact.setLastName("Mustermann-" + uuid);
    return contact;
  }

  private static String getHost() {
    final String host = System.getenv("APPLICATION_HOST");
    if (host == null) {
      return "localhost";
    }
    return host;
  }

  private static Integer getPort() {
    final String port = System.getenv("APPLICATION_PORT");
    if (port == null) {
      return 8080;
    }
    return Integer.valueOf(port);
  }

}
