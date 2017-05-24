package com.innvo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Size(max = 100)
    @Column(name = "namefirst", length = 100)
    private String namefirst;

    @OneToMany(mappedBy = "parentmbr")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Personpersonmbr> parents = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Person name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamefirst() {
        return namefirst;
    }

    public Person namefirst(String namefirst) {
        this.namefirst = namefirst;
        return this;
    }

    public void setNamefirst(String namefirst) {
        this.namefirst = namefirst;
    }

    public Set<Personpersonmbr> getParents() {
        return parents;
    }

    public Person parents(Set<Personpersonmbr> personpersonmbrs) {
        this.parents = personpersonmbrs;
        return this;
    }

    public Person addParent(Personpersonmbr personpersonmbr) {
        this.parents.add(personpersonmbr);
        personpersonmbr.setParentmbr(this);
        return this;
    }

    public Person removeParent(Personpersonmbr personpersonmbr) {
        this.parents.remove(personpersonmbr);
        personpersonmbr.setParentmbr(null);
        return this;
    }

    public void setParents(Set<Personpersonmbr> personpersonmbrs) {
        this.parents = personpersonmbrs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        if (person.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), person.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", namefirst='" + getNamefirst() + "'" +
            "}";
    }
}
