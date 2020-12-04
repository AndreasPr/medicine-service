package andreasgroup.medicineservice.services.production;

import andreasgroup.medicineservice.configuration.JmsConfiguration;
import andreasgroup.medicineservice.domain.Medicine;
import andreasgroup.medicineservice.repositories.MedicineRepository;
import andreasgroup.medicineservice.services.inventory.MedicineInventoryService;
import andreasgroup.medicineservice.web.mappers.MedicineMapper;
import andreasgroup.production.model.events.ProductionMedicineEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 28/Nov/2020 to microservices-medicine-production
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductionService {

    private final MedicineRepository medicineRepository;
    private final MedicineInventoryService medicineInventoryService;
    private final JmsTemplate jmsTemplate;
    private final MedicineMapper medicineMapper;

    @Scheduled(fixedRate = 5000) //every 5 seconds
    public void checkForLowInventory(){
        List<Medicine> medicines = medicineRepository.findAll();

        medicines.forEach(medicine -> {
            Integer invQOH = medicineInventoryService.getOnhandInventory(medicine.getId());
            log.debug("Checking Inventory for: " + medicine.getMedicineName() + " and ID: " + medicine.getId());
            log.debug("Minimum OnHand is: " + medicine.getMinOnHand());
            log.debug("Inventory is: "  + invQOH);

            if(medicine.getMinOnHand() >= invQOH){
                jmsTemplate.convertAndSend(JmsConfiguration.PRODUCTION_REQUEST_QUEUE, new ProductionMedicineEvent(medicineMapper.medicineToMedicineDto(medicine)));
            }
        });

    }
}
