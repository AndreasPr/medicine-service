package andreasgroup.production.model.events;

import andreasgroup.production.model.MedicineDto;
import lombok.NoArgsConstructor;

/**
 * Created on 28/Nov/2020 to microservices-medicine-production
 */
@NoArgsConstructor
public class NewInventoryEvent extends MedicineEvent{

    public NewInventoryEvent(MedicineDto medicineDto) {
        super(medicineDto);
    }

}
