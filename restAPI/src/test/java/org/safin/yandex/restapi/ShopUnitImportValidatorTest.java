package org.safin.yandex.restapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ShopUnitImportValidatorTest {


    private ShopUnitImportValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ShopUnitImportValidator();
    }

    @Test
    void validateCorrectUnit() {
        ShopUnitImport shopUnit = new ShopUnitImport(
                "d515e43f-f3f6-4471-bb77-6b455017a2d2",
                "asdf",
                "d515e43f-f3f6-4471-bb77-6b455017a2d3",
                ShopUnitType.OFFER,
                123L
        );
        assertThat(validator.isValid(shopUnit)).isTrue();

    }

    @Test
    void validateIncorrectFormatId() {
        ShopUnitImport shopUnit = new ShopUnitImport(
                "d515e43f-f3f6-4471-bb77-6b455017a2d",
                "asdf",
                "d515e43f-f3f6-4471-bb77-6b455017a2d3",
                ShopUnitType.OFFER,
                123L
        );
        assertThat(validator.isValid(shopUnit)).isFalse();
    }

    @Test
    void validateNullName() {
        ShopUnitImport shopUnit = new ShopUnitImport(
                "d515e43f-f3f6-4471-bb77-6b455017a2d2",
                null,
                "d515e43f-f3f6-4471-bb77-6b455017a2d3",
                ShopUnitType.OFFER,
                123L
        );
        assertThat(validator.isValid(shopUnit)).isFalse();
    }

    @Test
    void validateIncorrectFormatParentId() {
        ShopUnitImport shopUnit = new ShopUnitImport(
                "d515e43f-f3f6-4471-bb77-6b455017a2d2",
                "asdf",
                "d515e43f-f3f6-4471-bb77-6b455017a2d",
                ShopUnitType.OFFER,
                123L
        );
        assertThat(validator.isValid(shopUnit)).isFalse();
    }

    @Test
    void validateNotNullPriceWithCategory() {
        ShopUnitImport shopUnit = new ShopUnitImport(
                "d515e43f-f3f6-4471-bb77-6b455017a2d2",
                "asdf",
                "d515e43f-f3f6-4471-bb77-6b455017a2d",
                ShopUnitType.CATEGORY,
                123L
        );
        assertThat(validator.isValid(shopUnit)).isFalse();
    }

    @Test
    void validateNullPriceWithOffer() {
        ShopUnitImport shopUnit = new ShopUnitImport(
                "d515e43f-f3f6-4471-bb77-6b455017a2d2",
                "asdf",
                "d515e43f-f3f6-4471-bb77-6b455017a2d",
                ShopUnitType.OFFER,
                null
        );
        assertThat(validator.isValid(shopUnit)).isFalse();
    }

    @Test
    void validateNegativePrice() {
        ShopUnitImport shopUnit = new ShopUnitImport(
                "d515e43f-f3f6-4471-bb77-6b455017a2d2",
                "asdf",
                "d515e43f-f3f6-4471-bb77-6b455017a2d",
                ShopUnitType.OFFER,
                -123L
        );
        assertThat(validator.isValid(shopUnit)).isFalse();
    }
}