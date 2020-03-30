package org.might.instancecontroller.dba.entity;

import javax.persistence.*;

@Entity
@Table(name = "flavor")
public class Flavor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "reference", unique = true)
    private String reference;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Flavor)) {
            return false;
        }

        Flavor flavor = (Flavor) o;

        return getReference().equals(flavor.getReference());
    }

}
