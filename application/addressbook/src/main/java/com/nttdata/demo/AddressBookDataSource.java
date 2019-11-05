package com.nttdata.demo;


import javax.annotation.sql.DataSourceDefinition;

@DataSourceDefinition(
    name="java:global/jdbc/demo",
    className="org.postgresql.ds.PGSimpleDataSource",
    properties=
        { "url=jdbc:postgresql://${DB_HOST}:5432/${DB_NAME}",
          "user=${DB_USER}",
          "password=${DB_PASS}" }
)
public class AddressBookDataSource {

}
