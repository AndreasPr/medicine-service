package andreasgroup.medicineservice.web.controller;

import andreasgroup.medicineservice.services.MedicineService;
import andreasgroup.production.model.MedicineDto;
import andreasgroup.production.model.MedicinePagedList;
import andreasgroup.production.model.MedicineStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created on 28/Nov/2020 to microservices-medicine-production
 */
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@RestController
public class MedicineController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final MedicineService medicineService;

    @GetMapping(produces = { "application/json" }, path = "medicine")
    public ResponseEntity<MedicinePagedList> listMedicines(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                       @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                       @RequestParam(value = "medicineName", required = false) String medicineName,
                                                       @RequestParam(value = "medicineStyle", required = false) MedicineStyleEnum medicineStyle,
                                                       @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand){

        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }

        if (pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        MedicinePagedList medicineList = medicineService.listMedicines(medicineName, medicineStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);

        return new ResponseEntity<>(medicineList, HttpStatus.OK);
    }

    @GetMapping("medicine/{medicineId}")
    public ResponseEntity<MedicineDto> getMedicineById(@PathVariable("medicineId") UUID medicineId,
                                                       @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand){
        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }

        return new ResponseEntity<>(medicineService.getById(medicineId, showInventoryOnHand), HttpStatus.OK);
    }

    @GetMapping("medicineUpc/{upc}")
    public ResponseEntity<MedicineDto> getMedicineByUpc(@PathVariable("upc") String upc){
        return new ResponseEntity<>(medicineService.getByUpc(upc), HttpStatus.OK);
    }

    @PostMapping(path = "medicine")
    public ResponseEntity saveNewMedicine(@RequestBody @Validated MedicineDto medicineDto){
        return new ResponseEntity<>(medicineService.saveNewMedicine(medicineDto), HttpStatus.CREATED);
    }

    @PutMapping("medicine/{medicineId}")
    public ResponseEntity updateMedicineById(@PathVariable("medicineId") UUID medicineId, @RequestBody @Validated MedicineDto medicineDto){
        return new ResponseEntity<>(medicineService.updateMedicine(medicineId, medicineDto), HttpStatus.NO_CONTENT);
    }
}
