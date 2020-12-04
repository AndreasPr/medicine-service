package andreasgroup.medicineservice.services.inventory;

import andreasgroup.medicineservice.configuration.FeignClientConfiguration;
import andreasgroup.medicineservice.services.inventory.model.MedicineInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

/**
 * Created on 28/Nov/2020 to microservices-medicine-production
 */
@FeignClient(name = "medicine-inventory-service", fallback = InventoryServiceFeignClientFailover.class, configuration = FeignClientConfiguration.class)
public interface InventoryServiceFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = MedicineInventoryServiceRestTemplateImpl.INVENTORY_PATH)
    ResponseEntity<List<MedicineInventoryDto>> getOnhandInventory(@PathVariable UUID medicineId);
}
