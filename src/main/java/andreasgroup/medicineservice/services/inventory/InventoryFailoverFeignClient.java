package andreasgroup.medicineservice.services.inventory;

import andreasgroup.medicineservice.services.inventory.model.MedicineInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created on 28/Nov/2020 to microservices-medicine-production
 */
@FeignClient(name = "medicine-inventory-failover")
public interface InventoryFailoverFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/medicine-inventory-failover")
    ResponseEntity<List<MedicineInventoryDto>> getOnhandInventory();
}
