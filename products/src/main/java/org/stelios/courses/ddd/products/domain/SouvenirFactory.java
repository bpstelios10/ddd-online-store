package org.stelios.courses.ddd.products.domain;

public class SouvenirFactory {

    public static Souvenir create(String id, String title, String description) {
        return new Souvenir(id, title, description);
    }
}
