package org.safin.yandex.restapi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ShopUnitImportRequestValidatorTest {

    private ShopUnitImportRequestValidator underTest;
    @Autowired
    private ShopUnitRepository repository;

    private final ShopUnitImportValidator importValidator = new ShopUnitImportValidator();

    private final ShopUnit parent1 = new ShopUnit(
            "3fa85f64-5717-4562-b3fc-2c963f66a333",
            "asdf",
            "2022-02-02T12:00:00.000Z",
            null,
            ShopUnitType.CATEGORY,
            200L,
            new HashSet<>(),
            3
    );

    private final ShopUnit parent2 = new ShopUnit(
            "3fa85f64-5717-4562-b3fc-2c963f66a335",
            "asdf2",
            "2022-02-02T12:00:00.000Z",
            "3fa85f64-5717-4562-b3fc-2c963f66a333",
            ShopUnitType.CATEGORY,
            100L,
            new HashSet<>(),
            2
    );

    private final ShopUnit child1 = new ShopUnit(
            "3fa85f64-5717-4562-b3fc-2c963f66a334",
            "asdf1",
            "2022-02-02T12:00:00.000Z",
            "3fa85f64-5717-4562-b3fc-2c963f66a333",
            ShopUnitType.OFFER,
            100L,
            new HashSet<>(),
            1
    );

    private final ShopUnit child2 = new ShopUnit(
            "3fa85f64-5717-4562-b3fc-2c963f66a336",
            "asdf3",
            "2022-02-02T12:00:00.000Z",
            "3fa85f64-5717-4562-b3fc-2c963f66a335",
            ShopUnitType.OFFER,
            50L,
            new HashSet<>(),
            1
    );

    private final ShopUnit child3 = new ShopUnit(
            "3fa85f64-5717-4562-b3fc-2c963f66a337",
            "asdf4",
            "2022-02-02T12:00:00.000Z",
            "3fa85f64-5717-4562-b3fc-2c963f66a335",
            ShopUnitType.OFFER,
            50L,
            new HashSet<>(),
            1

    );

    @BeforeEach
    void setUp() {
        underTest = new ShopUnitImportRequestValidator(importValidator, repository);
        parent1.addChild(parent2);
        parent1.addChild(child1);
        parent2.addChild(child2);
        parent2.addChild(child3);
        repository.saveAll(List.of(child3, child2, child1, parent2, parent1));
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void validateCorrectRequest() {
        ShopUnitImportRequest request = new ShopUnitImportRequest(
                List.of(
                        new ShopUnitImport(
                                "d515e43f-f3f6-4471-bb77-6b455017a2d2",
                                "asdf1",
                                null,
                                ShopUnitType.OFFER,
                                123L
                        ),
                        new ShopUnitImport(
                                "d515e43f-f3f6-4471-bb77-6b455017a2d3",
                                "asdf2",
                                null,
                                ShopUnitType.OFFER,
                                123L
                        )
                ),
                "2022-02-01T12:00:00.000Z"
        );

        assertThat(underTest.isValid(request)).isTrue();
    }

    @Test
    void validateIncorrectList() {
        ShopUnitImportRequest request = new ShopUnitImportRequest(
                List.of(
                        new ShopUnitImport(
                                "d515e43f-f3f6-4471-b",
                                "asdf1",
                                null,
                                ShopUnitType.OFFER,
                                123L
                        ),
                        new ShopUnitImport(
                                "d515e43f-f3f6-4471-bb77-6b455017a2d3",
                                "asdf2",
                                null,
                                ShopUnitType.OFFER,
                                123L
                        )
                ),
                "2022-02-01T12:00:00.000Z"
        );

        assertThat(underTest.isValid(request)).isFalse();
    }

    @Test
    void validateWrongDateFormat() {
        ShopUnitImportRequest request = new ShopUnitImportRequest(
                List.of(
                        new ShopUnitImport(
                                "d515e43f-f3f6-4471-bb77-6b455017a2d2",
                                "asdf1",
                                null,
                                ShopUnitType.OFFER,
                                123L
                        ),
                        new ShopUnitImport(
                                "d515e43f-f3f6-4471-bb77-6b455017a2d3",
                                "asdf2",
                                null,
                                ShopUnitType.OFFER,
                                123L
                        )
                ),
                "2022-02-01 12:00:00.000Z"
        );

        assertThat(underTest.isValid(request)).isFalse();
    }

    @Test
    void validateChildOfOffer() {
        ShopUnitImportRequest request = new ShopUnitImportRequest(
                List.of(
                new ShopUnitImport(
                        "d515e43f-f3f6-4471-bb77-6b455017a2d3",
                        "asdf2",
                        "3fa85f64-5717-4562-b3fc-2c963f66a336",
                        ShopUnitType.OFFER,
                        123L
                )),
                "2022-02-01T12:00:00.000Z"
        );

        assertThat(underTest.isValid(request)).isFalse();
    }

    @Test
    void validateListWithTwoSameIds() {
        ShopUnitImportRequest request = new ShopUnitImportRequest(
                List.of(
                        new ShopUnitImport(
                                "d515e43f-f3f6-4471-bb77-6b455017a2d3",
                                "asdf2",
                                null,
                                ShopUnitType.OFFER,
                                123L
                        ),
                        new ShopUnitImport(
                                "d515e43f-f3f6-4471-bb77-6b455017a2d3",
                                "asdf3",
                                null,
                                ShopUnitType.OFFER,
                                123L
                        ),
                        new ShopUnitImport(
                                "d515e43f-f3f6-4471-bb77-6b455017a2d2",
                                "asdf4",
                                null,
                                ShopUnitType.OFFER,
                                123L
                        )),
                "2022-02-01T12:00:00.000Z"
        );

        assertThat(underTest.isValid(request)).isFalse();
    }

    @Test
    void validateChildWithParentWhichDoesntExist() {
        ShopUnitImportRequest request = new ShopUnitImportRequest(
                List.of(
                        new ShopUnitImport(
                                "d515e43f-f3f6-4471-bb77-6b455017a2d3",
                                "asdf2",
                                "d515e43f-f3f6-4471-bb77-6b455017a7c0",
                                ShopUnitType.OFFER,
                                123L
                        )),
                "2022-02-01T12:00:00.000Z"
        );

        assertThat(underTest.isValid(request)).isFalse();
    }

    @Test
    void validateCategoryWhichTurningIntoOffer() {
        ShopUnitImport parent2ToOffer = new ShopUnitImport(
                parent2.getId(),
                parent2.getName(),
                parent2.getParentId(),
                ShopUnitType.OFFER,
                100L
        );
        ShopUnitImportRequest request = new ShopUnitImportRequest(List.of(parent2ToOffer), "2022-02-01T12:00:00.000Z");

        assertThat(underTest.isValid(request)).isFalse();
    }
}