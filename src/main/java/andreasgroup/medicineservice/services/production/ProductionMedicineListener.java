package andreasgroup.medicineservice.services.production;

import andreasgroup.medicineservice.configuration.JmsConfiguration;
import andreasgroup.medicineservice.domain.Medicine;
import andreasgroup.medicineservice.repositories.MedicineRepository;
import andreasgroup.production.model.MedicineDto;
import andreasgroup.production.model.events.NewInventoryEvent;
import andreasgroup.production.model.events.ProductionMedicineEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 28/Nov/2020 to microservices-medicine-production
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class ProductionMedicineListener {

    private final MedicineRepository medicineRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfiguration.PRODUCTION_REQUEST_QUEUE)
    public void listen(ProductionMedicineEvent event){
        MedicineDto medicineDto = event.getMedicineDto();

        Medicine medicine = medicineRepository.getOne(medicineDto.getId());

        medicineDto.setQuantityOnHand(medicine.getQuantityToProduction());

        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(medicineDto);

        log.debug("Medicine Production is " + medicine.getMinOnHand() + " : Quantity OnHand: " + medicineDto.getQuantityOnHand());

        jmsTemplate.convertAndSend(JmsConfiguration.NEW_INVENTORY_QUEUE, newInventoryEvent);
    }
}
