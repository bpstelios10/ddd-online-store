package org.stelios.courses.ddd.products.domain;

public class Souvenirs implements IProduct {

    private final String id;
    private final String title;
    private final String description;

    public Souvenirs(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
