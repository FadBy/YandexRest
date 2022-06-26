package org.safin.yandex.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ShopUnitController {

    private ShopUnitService shopUnitService;

    @Autowired
    public void setShopUnitService(ShopUnitService shopUnitService) {
        this.shopUnitService = shopUnitService;
    }

    @PostMapping("/imports")
    private ResponseEntity<Object> postShopUnit(@RequestBody ShopUnitImportRequest importRequest) {
        boolean worked = shopUnitService.addUnitFromRequest(importRequest);
        if (!worked) {
            return new ResponseEntity<>(new Error(400, "Validation Failed"), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("", HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<Object> deleteShopUnit(@PathVariable String id) {
        if (!ShopUnitService.uuidPattern.matcher(id).matches()) {
            return new ResponseEntity<>(new Error(400, "Validation Failed"), HttpStatus.BAD_REQUEST);
        }
        if (shopUnitService.deleteShopUnit(id)) {
            return new ResponseEntity<>("", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Error(404, "Item not found"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/nodes/{id}")
    private ResponseEntity<Object> getNodes(@PathVariable String id) {
        if (!shopUnitService.isValidUUID(id)) {
            return new ResponseEntity<>(new Error(400, "Validation Failed"), HttpStatus.BAD_REQUEST);
        }
        Optional<ShopUnit> optUnit = shopUnitService.getNode(id);
        if (optUnit.isEmpty()) {
            return new ResponseEntity<>(new Error(404, "Item not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optUnit.get(), HttpStatus.OK);
    }
}
