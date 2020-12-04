package andreasgroup.medicineservice.web.mappers;

import andreasgroup.medicineservice.domain.Medicine;
import andreasgroup.medicineservice.services.inventory.MedicineInventoryService;
import andreasgroup.production.model.MedicineDto;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created on 28/Nov/2020 to microservices-medicine-production
 */
public abstract class MedicineMapperDecorator implements MedicineMapper{

    private MedicineInventoryService medicineInventoryService;
    private MedicineMapper mapper;

    @Autowired
    public void setMedicineInventoryService(MedicineInventoryService medicineInventoryService) {
        this.medicineInventoryService = medicineInventoryService;
    }

    @Autowired
    public void setMapper(MedicineMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public MedicineDto medicineToMedicineDto(Medicine medicine) {
        return mapper.medicineToMedicineDto(medicine);
    }

    @Override
    public MedicineDto medicineToMedicineDtoWithInventory(Medicine medicine) {
        MedicineDto dto = mapper.medicineToMedicineDto(medicine);
        dto.setQuantityOnHand(medicineInventoryService.getOnhandInventory(medicine.getId()));
        return dto;
    }

    @Override
    public Medicine medicineDtoToMedicine(MedicineDto medicineDto) {
        return mapper.medicineDtoToMedicine(medicineDto);
    }
}
