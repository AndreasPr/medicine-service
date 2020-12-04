package andreasgroup.medicineservice.services;

import andreasgroup.production.model.MedicineDto;
import andreasgroup.production.model.MedicinePagedList;
import andreasgroup.production.model.MedicineStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

/**
 * Created on 28/Nov/2020 to microservices-medicine-production
 */
public interface MedicineService {

    MedicinePagedList listMedicines(String medicineName, MedicineStyleEnum medicineStyle, PageRequest pageRequest, Boolean showInventoryOnHand);

    MedicineDto getById(UUID medicineId, Boolean showInventoryOnHand);

    MedicineDto saveNewMedicine(MedicineDto medicineDto);

    MedicineDto updateMedicine(UUID medicineId, MedicineDto medicineDto);

    MedicineDto getByUpc(String upc);
}
