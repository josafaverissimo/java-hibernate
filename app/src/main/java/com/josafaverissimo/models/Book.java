package com.josafaverissimo.models;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
  @Id
  private String id;

  public String title;

  @ManyToOne(fetch = FetchType.LAZY)
  private Publisher publisher;

  @ManyToMany(mappedBy = Author_.BOOKS)
  private Set<Author> authors = new HashSet<>();

  @Column(name = "created_at")
  private ZonedDateTime createdAt;

  public Book() {
  }

  public Book(String id, String title, Publisher publisher) {
    this.id = id;
    this.title = title;
    this.publisher = publisher;
    this.createdAt = ZonedDateTime.now();
  }

  public String getTitle() {
    return title;
  }

  public void setId(String id) {
    this.id = id;
  } 

  public String getId() {
    return id;
  }

  public Publisher getPublisher() {
    return publisher;
  }

  public Set<Author> getAuthors() {
    return authors;
  }
}
