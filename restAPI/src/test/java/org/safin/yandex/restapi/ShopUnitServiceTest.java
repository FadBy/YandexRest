package org.safin.yandex.restapi;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ShopUnitServiceTest {

    private ShopUnitService underTest;

    @Autowired
    private ShopUnitRepository repository;

    private ShopUnitImportRequestValidator validator;

    private final ShopUnit parent1 = new ShopUnit(
            "3fa85f64-5717-4562-b3fc-2c963f66a333",
            "asdf",
            "2022-02-02T12:00:00.000Z",
            null,
            ShopUnitType.CATEGORY,
            null,
            new HashSet<>()
    );

    private final ShopUnit parent2 = new ShopUnit(
            "3fa85f64-5717-4562-b3fc-2c963f66a335",
            "asdf2",
            "2022-03-02T12:00:00.000Z",
            "3fa85f64-5717-4562-b3fc-2c963f66a333",
            ShopUnitType.CATEGORY,
            null,
            new HashSet<>()
    );

    private final ShopUnit child1 = new ShopUnit(
            "3fa85f64-5717-4562-b3fc-2c963f66a334",
            "asdf1",
            "2022-04-02T12:00:00.000Z",
            "3fa85f64-5717-4562-b3fc-2c963f66a333",
            ShopUnitType.OFFER,
            100L,
            new HashSet<>(),
            1
    );

    private final ShopUnit child2 = new ShopUnit(
            "3fa85f64-5717-4562-b3fc-2c963f66a336",
            "asdf3",
            "2022-05-02T12:00:00.000Z",
            "3fa85f64-5717-4562-b3fc-2c963f66a335",
            ShopUnitType.OFFER,
            50L,
            new HashSet<>(),
            1
    );

    private final ShopUnit child3 = new ShopUnit(
            "3fa85f64-5717-4562-b3fc-2c963f66a337",
            "asdf4",
            "2022-06-02T12:00:00.000Z",
            "3fa85f64-5717-4562-b3fc-2c963f66a335",
            ShopUnitType.OFFER,
            50L,
            new HashSet<>(),
            1
    );

    @BeforeEach
    void setUp() {
        parent2.addChild(child2);
        parent2.addChild(child3);
        parent1.addChild(parent2);
        parent1.addChild(child1);
        repository.addUnitWithChildren(parent1);
        validator = new ShopUnitImportRequestValidator(new ShopUnitImportValidator(), repository);
        underTest = new ShopUnitService(repository, validator);
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void deleteOffer() {
        underTest.deleteShopUnit(child3.getId());
        assertThat(repository.existsById(child3.getId())).isFalse();
        ShopUnit updatedParent2 = repository.findById(parent2.getId()).get();
        assertThat(updatedParent2.getCost()).isEqualTo(parent2.getCost() - child3.getCost());
        assertThat(updatedParent2.getOffersCount()).isEqualTo(parent2.getOffersCount() - 1);
    }

    @Test
    void deleteCategory() {
        underTest.deleteShopUnit(parent2.getId());
        assertThat(repository.existsById(child3.getId())).isFalse();
        assertThat(repository.existsById(child2.getId())).isFalse();
        assertThat(repository.existsById(parent2.getId())).isFalse();
        ShopUnit updatedParent1 = repository.findById(parent1.getId()).get();
        assertThat(updatedParent1.getCost()).isEqualTo(parent1.getCost() - parent2.getCost());
        assertThat(updatedParent1.getOffersCount()).isEqualTo(parent1.getOffersCount() - 2);
    }

    @Test
    void deleteRootCategory() {
        underTest.deleteShopUnit(parent1.getId());
        assertThat(repository.findAll().size()).isEqualTo(0);
    }

    @Test
    void addOneOffer() {
        ShopUnitImport newChild = new ShopUnitImport(
                "3fa85f64-5717-4562-b3fc-2c963f66a259",
                "asdf",
                parent2.getId(),
                ShopUnitType.OFFER,
                123L
        );
        ShopUnitImportRequest request = new ShopUnitImportRequest(List.of(
                newChild
        ), "2022-07-02T12:00:00.000Z");
        String parent2Id = parent2.getId();
        String parent1Id = parent1.getId();
        underTest.addUnitFromRequest(request);

        assertThat(repository.existsById(newChild.getId())).isTrue();
        assertThat(repository.findById(parent1Id).get().getDate()).isEqualTo(request.updateDate());
        assertThat(repository.findById(parent2Id).get().getDate()).isEqualTo(request.updateDate());
        assertThat(repository.findById(parent1Id).get().getCost()).isEqualTo(123L + parent1.getCost());
        assertThat(repository.findById(parent2Id).get().getCost()).isEqualTo(123L + parent2.getCost());
        assertThat(repository.findById(parent1Id).get().getOffersCount()).isEqualTo(1 + parent1.getOffersCount());
        assertThat(repository.findById(parent2Id).get().getOffersCount()).isEqualTo(1 + parent2.getOffersCount());
    }

    @Test
    void addOneCategory() {
        ShopUnitImportRequest request = new ShopUnitImportRequest(List.of(
                new ShopUnitImport(
                        "3fa85f64-5717-4562-b3fc-2c963f66a357",
                        "asdf",
                        "3fa85f64-5717-4562-b3fc-2c963f66a335",
                        ShopUnitType.CATEGORY,
                        null
                )
        ), "2022-07-02T12:00:00.000Z");
        String parent2Id = "3fa85f64-5717-4562-b3fc-2c963f66a335";
        String parent1Id = "3fa85f64-5717-4562-b3fc-2c963f66a333";
        underTest.addUnitFromRequest(request);

        assertThat(repository.existsById("3fa85f64-5717-4562-b3fc-2c963f66a357")).isTrue();
        assertThat(repository.findById(parent1Id).get().getDate()).isEqualTo(request.updateDate());
        assertThat(repository.findById(parent2Id).get().getDate()).isEqualTo(request.updateDate());
        assertThat(repository.findById(parent1Id).get().getCost()).isEqualTo(parent1.getCost());
        assertThat(repository.findById(parent2Id).get().getCost()).isEqualTo(parent2.getCost());
        assertThat(repository.findById(parent1Id).get().getOffersCount()).isEqualTo(parent1.getOffersCount());
        assertThat(repository.findById(parent2Id).get().getOffersCount()).isEqualTo(parent2.getOffersCount());
    }

    @Test
    void addCategoryWithTwoChildren() {
        ShopUnitImport mainParent = new ShopUnitImport(
                        "3fa85f64-5717-4562-b3fc-2c963f66a357",
                        "asdf",
                        "3fa85f64-5717-4562-b3fc-2c963f66a335",
                        ShopUnitType.CATEGORY,
                        null
        );
        ShopUnitImport firstChild = new ShopUnitImport(
                "3fa85f64-5717-4562-b3fc-2c963f66a312",
                "asdf",
                "3fa85f64-5717-4562-b3fc-2c963f66a357",
                ShopUnitType.OFFER,
                100L
        );
        ShopUnitImport secondChild = new ShopUnitImport(
                "3fa85f64-5717-4562-b3fc-2c963f66a313",
                "asdf",
                "3fa85f64-5717-4562-b3fc-2c963f66a357",
                ShopUnitType.OFFER,
                50L
        );
        ShopUnitImportRequest request = new ShopUnitImportRequest(List.of(
                mainParent,
                firstChild,
                secondChild
        ), "2022-07-02T12:00:00.000Z");
        String parent2Id = "3fa85f64-5717-4562-b3fc-2c963f66a335";
        String parent1Id = "3fa85f64-5717-4562-b3fc-2c963f66a333";
        underTest.addUnitFromRequest(request);

        assertThat(repository.existsById(mainParent.getId())).isTrue();
        assertThat(repository.existsById(firstChild.getId())).isTrue();
        assertThat(repository.existsById(secondChild.getId())).isTrue();
        assertThat(repository.findById(parent1Id).get().getDate()).isEqualTo(request.updateDate());
        assertThat(repository.findById(parent2Id).get().getDate()).isEqualTo(request.updateDate());
        assertThat(repository.findById(parent1Id).get().getCost()).isEqualTo(firstChild.getPrice() + secondChild.getPrice() + parent1.getCost());
        assertThat(repository.findById(parent2Id).get().getCost()).isEqualTo(firstChild.getPrice() + secondChild.getPrice() + parent2.getCost());
        assertThat(repository.findById(parent1Id).get().getOffersCount()).isEqualTo(2 + parent1.getOffersCount());
        assertThat(repository.findById(parent2Id).get().getOffersCount()).isEqualTo(2 + parent2.getOffersCount());
        assertThat(repository.findById(mainParent.getId()).get().getOffersCount()).isEqualTo(2);
        assertThat(repository.findById(mainParent.getId()).get().getCost()).isEqualTo(150);
    }

    @Test
    void updateCategoryAndOffer() {
        ShopUnitImport parent2Update = new ShopUnitImport(
                parent2.getId(),
                parent2.getName() + "asdf",
                parent2.getParentId(),
                parent2.getType(),
                null
        );
        ShopUnitImport newChild = new ShopUnitImport(
                "3fa85f64-5717-4562-b3fc-2c963f66a313",
                "asdf",
                parent2.getId(),
                ShopUnitType.OFFER,
                101L
        );
        ShopUnitImport child1Update = new ShopUnitImport(
                child1.getId(),
                child1.getName() + "ahaha",
                parent2.getId(),
                ShopUnitType.OFFER,
                child1.getPrice()
        );
        ShopUnitImportRequest request = new ShopUnitImportRequest(
                List.of(parent2Update, newChild, child1Update),
                "2022-07-02T12:00:00.000Z"
        );

        underTest.addUnitFromRequest(request);

        assertThat(repository.findById(parent2.getId()).get().getName()).isEqualTo(parent2Update.getName());
        assertThat(repository.existsById(newChild.getId())).isTrue();
        assertThat(repository.findById(child1.getId()).get().getName()).isEqualTo(child1Update.getName());
        assertThat(repository.findById(parent1.getId()).get().getCost()).isEqualTo(parent1.getCost() + 101);
        assertThat(repository.findById(parent1.getId()).get().getOffersCount()).isEqualTo(4);
        assertThat(repository.findById(parent1.getId()).get().getDate()).isEqualTo(request.updateDate());
        assertThat(repository.findById(parent2.getId()).get().getCost()).isEqualTo(parent2.getCost() + newChild.getPrice() + child1Update.getPrice());
        assertThat(repository.findById(parent2.getId()).get().getOffersCount()).isEqualTo(parent2.getOffersCount() + 2);

    }



    @Test
    void getNode() {
    }

    @Test
    void isValidUUID() {

    }
}