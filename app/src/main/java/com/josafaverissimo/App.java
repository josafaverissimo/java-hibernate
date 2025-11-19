package com.josafaverissimo;

import com.josafaverissimo.models.Book;
import com.josafaverissimo.models.Book_;
import com.josafaverissimo.models.Publisher;
import com.josafaverissimo.models.Publisher_;
import com.josafaverissimo.queries.BookQueries;
import com.josafaverissimo.queries.BookQueries_;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernatePersistenceConfiguration;
import org.hibernate.query.criteria.CriteriaDefinition;

public class App {
  public static void main(String[] args) {
    SessionFactory sessionFactory =
        new HibernatePersistenceConfiguration("Bookshop", App.class)
            .managedClass(BookQueries.class)
            .createEntityManagerFactory();

    sessionFactory.inTransaction(
        session -> {
          String titlePattern = "epi%";
          String namePattern = "_ub";

          var builder = session.getCriteriaBuilder();

          CriteriaQuery<Book> query = builder.createQuery(Book.class);
          Root<Book> book = query.from(Book.class);
          Predicate where = builder.conjunction();

          if (titlePattern != null) {
            where = builder.and(where, builder.like(book.get(Book_.title), titlePattern));
          }

          if (namePattern != null) {
            Join<Book, Publisher> publisher = book.join(Book_.publisher);

            where = builder.and(where, builder.like(publisher.get(Publisher_.name), namePattern));
          }

          var criteriaQuery =
              query.select(book).where(where).orderBy(builder.asc(book.get(Book_.title)));

          var result = session.createSelectionQuery(criteriaQuery).getResultList();

          // System.out.println(String.format("got %d results", result.size()));
        });

    sessionFactory.inTransaction(
        session -> {
          String titlePattern = "epi%";
          String namePattern = "_ub";

          CriteriaDefinition<Book> query =
              new CriteriaDefinition<>(sessionFactory, Book.class) {
                {
                  Root<Book> book = from(Book.class);

                  select(book);

                  if (titlePattern != null) {
                    restrict(like(book.get(Book_.title), titlePattern));
                  }

                  if (namePattern != null) {
                    Join<Book, Publisher> publisher = book.join(Book_.publisher);
                    restrict(like(publisher.get(Publisher_.name), namePattern));
                  }

                  orderBy(asc(book.get(Book_.title)));
                }
              };

          var result = session.createSelectionQuery(query).getResultList();

          var a =
              session
                  .createQuery(BookQueries_._BooksByTitle_)
                  .setParameter("titlePattern", "%ett%")
                  .getResultList();

          System.out.println(String.format("got %d items", a.size()));
        });
  }
}
