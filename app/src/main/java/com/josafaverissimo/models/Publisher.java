package com.josafaverissimo.models;


import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "publishers")
public class Publisher {
  @Id
  public String id;

  @Basic(optional = false)
  public String name;


  @OneToMany(mappedBy = Book_.PUBLISHER)
  Set<Book> books;

  public Publisher() {
  }

  public Publisher(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }
}
