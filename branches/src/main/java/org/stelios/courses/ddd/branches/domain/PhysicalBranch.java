package org.stelios.courses.ddd.branches.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class PhysicalBranch implements IBranch {

    private final int id;
    private final String description;
    private final String owner;
    private final String city;
    private final String address;

    public PhysicalBranch(int id, String description, String owner, String city, String address) {
        this.id = id;
        this.description = description;
        this.owner = owner;
        this.city = city;
        this.address = address;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }
}
