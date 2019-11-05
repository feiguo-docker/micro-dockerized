package com.nttdata.demo.control;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.nttdata.demo.entity.Contact;

@Transactional
@RequestScoped
public class ContactCrudController {

  @PersistenceContext(name = "addressbookPU")
  private EntityManager entityManager;

  public Contact create(final Contact contact) {
    entityManager.persist(contact);
    return contact;
  }

  public Contact read(final Long id) {
    final Contact contact = entityManager.find(Contact.class, id);
    if (contact == null) {
      new EntityNotFoundException("Contact not found");
    }
    return contact;
  }

  public Contact update(final Long id, final Contact contact) {
    contact.setId(id);
    return entityManager.merge(contact);
  }

  public void delete(final Long id) {
    final Contact contact = read(id);
    entityManager.remove(contact);
  }
}
