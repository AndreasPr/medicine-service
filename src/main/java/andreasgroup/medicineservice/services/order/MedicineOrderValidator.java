package andreasgroup.medicineservice.services.order;

import andreasgroup.medicineservice.repositories.MedicineRepository;
import andreasgroup.production.model.events.MedicineOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created on 28/Nov/2020 to microservices-medicine-production
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class MedicineOrderValidator {

    private final MedicineRepository medicineRepository;

    public Boolean validateOrder(MedicineOrderDto medicineOrder){

        AtomicInteger medicinesNotFound = new AtomicInteger();

        medicineOrder.getMedicineOrderLines().forEach(orderline -> {
            if(medicineRepository.findByUpc(orderline.getUpc()) == null){
                medicinesNotFound.incrementAndGet();
            }
        });

        return medicinesNotFound.get() == 0;
    }
}
