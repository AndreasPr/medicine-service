package andreasgroup.medicineservice.repositories;

import andreasgroup.medicineservice.domain.Medicine;
import andreasgroup.production.model.MedicineStyleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created on 28/Nov/2020 to microservices-medicine-production
 */
public interface MedicineRepository extends JpaRepository<Medicine, UUID> {

    Page<Medicine> findAllByMedicineName(String medicineName, Pageable pageable);

    Page<Medicine> findAllByMedicineStyle(MedicineStyleEnum medicineStyle, Pageable pageable);

    Page<Medicine> findAllByMedicineNameAndMedicineStyle(String medicineName, MedicineStyleEnum medicineStyle, Pageable pageable);

    Medicine findByUpc(String upc);
}
