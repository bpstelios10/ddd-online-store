package org.stelios.courses.ddd.products.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SouvenirFactoryTest {

    @Test
    void create_returnsCorrectSouvenir() {
        Souvenir expected = new Souvenir("id1", "greek air", "bottle filled with greek air");

        Souvenir actual = SouvenirFactory.create("id1", "greek air", "bottle filled with greek air");

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void create_returnsSouvenirOfNulls() {
        Souvenir expected = new Souvenir(null, null, null);

        Souvenir actual = SouvenirFactory.create(null, null, null);

        assertThat(expected).isEqualTo(actual);
    }
}
