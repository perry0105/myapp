package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Sku.
 */
@Entity
@Table(name = "sku")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sku implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "sku")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserSku> userSkus = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Sku name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserSku> getUserSkus() {
        return userSkus;
    }

    public Sku userSkus(Set<UserSku> userSkus) {
        this.userSkus = userSkus;
        return this;
    }

    public Sku addUserSku(UserSku userSku) {
        userSkus.add(userSku);
        userSku.setSku(this);
        return this;
    }

    public Sku removeUserSku(UserSku userSku) {
        userSkus.remove(userSku);
        userSku.setSku(null);
        return this;
    }

    public void setUserSkus(Set<UserSku> userSkus) {
        this.userSkus = userSkus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sku sku = (Sku) o;
        if (sku.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sku.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Sku{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
