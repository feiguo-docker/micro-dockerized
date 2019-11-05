package com.nttdata.demo;

import java.util.HashSet;
import java.util.Set;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.nttdata.demo.boundary.ContactResource;
import com.nttdata.demo.boundary.StatusResource;

@ApplicationPath("/api")
public class AddressBookApplication extends Application{

  public Set<Class<?>> getClasses() {
    final HashSet<Class<?>> classes = new HashSet<>();
    classes.add(ContactResource.class);
    classes.add(StatusResource.class);
    return classes;
  }
}
