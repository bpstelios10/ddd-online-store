package org.stelios.courses.ddd.branches.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class OnlineBranch implements IBranch {

    private final int id;
    private final String description;
    private final String owner;
    private final String city;

    public OnlineBranch(int id, String description, String owner, String city) {
        this.id = id;
        this.description = description;
        this.owner = owner;
        this.city = city;
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
}
