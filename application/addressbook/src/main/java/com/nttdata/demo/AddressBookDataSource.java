package com.nttdata.demo;


import javax.annotation.sql.DataSourceDefinition;

@DataSourceDefinition(
    name = "java:global/jdbc/demo",
    className = "org.postgresql.ds.PGSimpleDataSource",
    databaseName = "${ENV=DB_NAME}",
    serverName = "${ENV=DB_HOST}",
    user = "${ENV=DB_USER}",
    password = "${ENV=DB_PASS}")
public class AddressBookDataSource {

}
