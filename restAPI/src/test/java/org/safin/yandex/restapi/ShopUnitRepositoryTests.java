package org.safin.yandex.restapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ShopUnitRepositoryTests {

    @Autowired
    private ShopUnitRepository underTest;


    private final ShopUnit testable = new ShopUnit(
            "3fa85f64-5717-4562-b3fc-2c963f66a333",
            "asdf",
            "2022-02-02T12:00:00.000Z",
            null,
            ShopUnitType.CATEGORY,
            200L,
            Set.of(
                    new ShopUnit(
                            "3fa85f64-5717-4562-b3fc-2c963f66a334",
                            "asdf1",
                            "2022-02-02T12:00:00.000Z",
                            "3fa85f64-5717-4562-b3fc-2c963f66a333",
                            ShopUnitType.OFFER,
                            100L,
                            Set.of(),
                            1
                    ),
                    new ShopUnit(
                            "3fa85f64-5717-4562-b3fc-2c963f66a335",
                            "asdf2",
                            "2022-02-02T12:00:00.000Z",
                            "3fa85f64-5717-4562-b3fc-2c963f66a333",
                            ShopUnitType.CATEGORY,
                            100L,
                            Set.of(
                                    new ShopUnit(
                                            "3fa85f64-5717-4562-b3fc-2c963f66a336",
                                            "asdf3",
                                            "2022-02-02T12:00:00.000Z",
                                            "3fa85f64-5717-4562-b3fc-2c963f66a335",
                                            ShopUnitType.OFFER,
                                            50L,
                                            Set.of()
                                    ),
                                    new ShopUnit(
                                            "3fa85f64-5717-4562-b3fc-2c963f66a337",
                                            "asdf4",
                                            "2022-02-02T12:00:00.000Z",
                                            "3fa85f64-5717-4562-b3fc-2c963f66a335",
                                            ShopUnitType.OFFER,
                                            50L,
                                            Set.of()
                                    )
                            )
                    )
            )
    );

    @Test
    void addUnitWithChildren1() {

        underTest.addUnitWithChildren(testable);

        boolean expected1 = underTest.existsById("3fa85f64-5717-4562-b3fc-2c963f66a333");
        boolean expected2 = underTest.existsById("3fa85f64-5717-4562-b3fc-2c963f66a334");
        boolean expected3 = underTest.existsById("3fa85f64-5717-4562-b3fc-2c963f66a335");
        boolean expected4 = underTest.existsById("3fa85f64-5717-4562-b3fc-2c963f66a336");
        boolean expected5 = underTest.existsById("3fa85f64-5717-4562-b3fc-2c963f66a337");

        assertThat(expected1 && expected2 && expected3 && expected4 && expected5).isTrue();
    }

    @Test
    void deleteUnitWithChildren1() {
        underTest.addUnitWithChildren(testable);

        underTest.deleteUnitWithChildren(underTest.findById("3fa85f64-5717-4562-b3fc-2c963f66a335").get());

        boolean expected1 = !underTest.existsById("3fa85f64-5717-4562-b3fc-2c963f66a335");
        boolean expected2 = !underTest.existsById("3fa85f64-5717-4562-b3fc-2c963f66a336");
        boolean expected3 = !underTest.existsById("3fa85f64-5717-4562-b3fc-2c963f66a337");

        assertThat(expected1 && expected2 && expected3).isTrue();
    }
}
