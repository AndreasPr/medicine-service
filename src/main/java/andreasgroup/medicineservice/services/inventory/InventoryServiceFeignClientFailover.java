package andreasgroup.medicineservice.services.inventory;

import andreasgroup.medicineservice.services.inventory.model.MedicineInventoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Created on 28/Nov/2020 to microservices-medicine-production
 */
@RequiredArgsConstructor
@Component
public class InventoryServiceFeignClientFailover implements InventoryServiceFeignClient {

    private final InventoryFailoverFeignClient failoverFeignClient;

    @Override
    public ResponseEntity<List<MedicineInventoryDto>> getOnhandInventory(UUID medicineId) {
        return failoverFeignClient.getOnhandInventory();
    }
}
