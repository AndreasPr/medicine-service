package andreasgroup.medicineservice.services.inventory;

import andreasgroup.medicineservice.services.inventory.model.MedicineInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created on 28/Nov/2020 to microservices-medicine-production
 */
@Slf4j
@RequiredArgsConstructor
@Profile("local-discovery")
@Service
public class MedicineInventoryServiceFeign implements MedicineInventoryService{

    private final InventoryServiceFeignClient inventoryServiceFeignClient;

    @Override
    public Integer getOnhandInventory(UUID medicineId) {
        log.debug("Calling Inventory Service for medicine with ID: " + medicineId);

        ResponseEntity<List<MedicineInventoryDto>> responseEntity = inventoryServiceFeignClient.getOnhandInventory(medicineId);

        Integer onHand = Objects.requireNonNull(responseEntity.getBody())
                .stream()
                .mapToInt(MedicineInventoryDto::getQuantityOnHand)
                .sum();

        log.debug("Medicine with Id: " + medicineId + " and On hand is: " + onHand);

        return onHand;
    }

}
