package com.josafaverissimo;

import com.josafaverissimo.models.Author;
import com.josafaverissimo.models.Book;
import com.josafaverissimo.models.Person;
import com.josafaverissimo.models.Publisher;

import java.util.concurrent.atomic.AtomicReference;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernatePersistenceConfiguration;

public class App {
  public static void main(String[] args) {
    SessionFactory sessionFactory =
        new HibernatePersistenceConfiguration("Bookshop", App.class).createEntityManagerFactory();

    sessionFactory.inTransaction(
        session -> {
          final var pu1 = new Publisher("1", "pub");
          final var pu2 = new Publisher("2", "sub");

          session.persist(pu1);
          session.persist(pu2);

          final var b1 = new Book("1123", "MyBook", pu1);
          final var b2 = new Book("1223", "BookBest", pu2);
          final var b3 = new Book("1323", "Better", pu1);

          session.persist(b1);
          session.persist(b2);
          session.persist(b3);

          final var p1 = new Person("1", "josafa");
          final var p2 = new Person("2", "verissimo");

          session.persist(p1);
          session.persist(p2);

          final var a1 = new Author("1", p1);
          final var a2 = new Author("2", p2);

          a1.getBooks().add(b1);
          a1.getBooks().add(b2);
          a1.getBooks().add(b3);
          a2.getBooks().add(b1);
          a2.getBooks().add(b2);
          a2.getBooks().add(b3);

          //   System.out.println(a);
          // System.out.println(b);

          session.persist(a1);
          session.persist(a2);
        });

    var book = sessionFactory.fromTransaction(
        session -> {
          var book1 = session.find(Book.class, "1123");

          Hibernate.initialize(book1.getAuthors());

          return book1;
        });

    sessionFactory.inTransaction(session -> {
      session.createMutationQuery("update Book b set b.title = :t where b.id = :id")
        .setParameter("t", "epiciic")
        .setParameter("id", "1223")
        .executeUpdate();

    });


    System.out.println(book.getAuthors().size());
  }
}
