package andreasgroup.production.model.events;

import andreasgroup.production.model.MedicineDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created on 28/Nov/2020 to microservices-medicine-production
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicineEvent implements Serializable {

    static final long serialVersionUID = 4926654148444624764L;
    private MedicineDto medicineDto;
}
