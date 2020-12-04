package andreasgroup.medicineservice.web.mappers;

import andreasgroup.medicineservice.domain.Medicine;
import andreasgroup.production.model.MedicineDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

/**
 * Created on 28/Nov/2020 to microservices-medicine-production
 */
@Mapper(uses = {DateMapper.class})
@DecoratedWith(MedicineMapperDecorator.class)
public interface MedicineMapper {
    MedicineDto medicineToMedicineDto(Medicine medicine);

    MedicineDto medicineToMedicineDtoWithInventory(Medicine medicine);

    Medicine medicineDtoToMedicine(MedicineDto dto);
}
