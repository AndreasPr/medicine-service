package andreasgroup.medicineservice.services.inventory;

import java.util.UUID;

/**
 * Created on 28/Nov/2020 to microservices-medicine-production
 */
public interface MedicineInventoryService {
    Integer getOnhandInventory(UUID medicineId);
}
