package com.innvo.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Personpersonmbr.
 */
@Entity
@Table(name = "personpersonmbr")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "personpersonmbr")
public class Personpersonmbr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 255)
    @Column(name = "jhi_comment", length = 255)
    private String comment;

    @ManyToOne(optional = false)
    @NotNull
    private Person parentmbr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public Personpersonmbr comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Person getParentmbr() {
        return parentmbr;
    }

    public Personpersonmbr parentmbr(Person person) {
        this.parentmbr = person;
        return this;
    }

    public void setParentmbr(Person person) {
        this.parentmbr = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Personpersonmbr personpersonmbr = (Personpersonmbr) o;
        if (personpersonmbr.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personpersonmbr.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Personpersonmbr{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
