package andreasgroup.medicineservice.bootstrap;

import andreasgroup.medicineservice.domain.Medicine;
import andreasgroup.medicineservice.repositories.MedicineRepository;
import andreasgroup.production.model.MedicineStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created on 28/Nov/2020 to microservices-medicine-production
 */
@Component
@RequiredArgsConstructor
public class MedicineLoader implements CommandLineRunner {

    private final MedicineRepository medicineRepository;
    public static final String MEDICINE_1_UPC = "0631234200036";
    public static final String MEDICINE_2_UPC = "0631234200037";
    public static final String MEDICINE_3_UPC = "0631234200038";

    @Override
    public void run(String... args) throws Exception {
        if(medicineRepository.count() == 0){
            loadMedicineObjects();
        }
    }

    private void loadMedicineObjects(){
        Medicine medicine1 = Medicine.builder()
                .medicineName("Meclizine")
                .medicineStyle(MedicineStyleEnum.Antiemetics.name())
                .minOnHand(12)
                .quantityToProduction(200)
                .price(new BigDecimal("12.95"))
                .upc(MEDICINE_1_UPC)
                .build();

        Medicine medicine2 = Medicine.builder()
                .medicineName("Calcium carbonate")
                .medicineStyle(MedicineStyleEnum.Antacids.name())
                .minOnHand(12)
                .quantityToProduction(200)
                .price(new BigDecimal("12.95"))
                .upc(MEDICINE_2_UPC)
                .build();

        Medicine medicine3 = Medicine.builder()
                .medicineName("Miscellanous analgesics")
                .medicineStyle(MedicineStyleEnum.Analgesics.name())
                .minOnHand(12)
                .quantityToProduction(200)
                .price(new BigDecimal("12.95"))
                .upc(MEDICINE_3_UPC)
                .build();

        medicineRepository.save(medicine1);
        medicineRepository.save(medicine2);
        medicineRepository.save(medicine3);
    }
}
