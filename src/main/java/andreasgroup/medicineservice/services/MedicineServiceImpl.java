package andreasgroup.medicineservice.services;

import andreasgroup.medicineservice.domain.Medicine;
import andreasgroup.medicineservice.repositories.MedicineRepository;
import andreasgroup.medicineservice.web.controller.NotFoundException;
import andreasgroup.medicineservice.web.mappers.MedicineMapper;
import andreasgroup.production.model.MedicineDto;
import andreasgroup.production.model.MedicinePagedList;
import andreasgroup.production.model.MedicineStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created on 28/Nov/2020 to microservices-medicine-production
 */
@RequiredArgsConstructor
@Service
public class MedicineServiceImpl implements MedicineService{

    private final MedicineRepository medicineRepository;
    private final MedicineMapper medicineMapper;

    @Cacheable(cacheNames = "medicineListCache", condition = "#showInventoryOnHand == false")
    @Override
    public MedicinePagedList listMedicines(String medicineName, MedicineStyleEnum medicineStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {

        MedicinePagedList medicinePagedList;
        Page<Medicine> medicinePage;

        if (!StringUtils.isEmpty(medicineName) && !StringUtils.isEmpty(medicineStyle)) {
            //search both
            medicinePage = medicineRepository.findAllByMedicineNameAndMedicineStyle(medicineName, medicineStyle, pageRequest);
        } else if (!StringUtils.isEmpty(medicineName) && StringUtils.isEmpty(medicineStyle)) {
            //search by medicine_service name
            medicinePage = medicineRepository.findAllByMedicineName(medicineName, pageRequest);
        } else if (StringUtils.isEmpty(medicineName) && !StringUtils.isEmpty(medicineStyle)) {
            //search by medicine_service style
            medicinePage = medicineRepository.findAllByMedicineStyle(medicineStyle, pageRequest);
        } else {
            medicinePage = medicineRepository.findAll(pageRequest);
        }

        if (showInventoryOnHand){
            medicinePagedList = new MedicinePagedList(medicinePage
                    .getContent()
                    .stream()
                    .map(medicineMapper::medicineToMedicineDtoWithInventory)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(medicinePage.getPageable().getPageNumber(),
                                    medicinePage.getPageable().getPageSize()),
                    medicinePage.getTotalElements());
        } else {
            medicinePagedList = new MedicinePagedList(medicinePage
                    .getContent()
                    .stream()
                    .map(medicineMapper::medicineToMedicineDto)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(medicinePage.getPageable().getPageNumber(),
                                    medicinePage.getPageable().getPageSize()),
                    medicinePage.getTotalElements());
        }

        return medicinePagedList;
    }

    @Cacheable(cacheNames = "medicineCache", key = "#medicineId", condition = "#showInventoryOnHand == false ")
    @Override
    public MedicineDto getById(UUID medicineId, Boolean showInventoryOnHand) {
        if (showInventoryOnHand) {
            return medicineMapper.medicineToMedicineDtoWithInventory(
                    medicineRepository.findById(medicineId).orElseThrow(NotFoundException::new)
            );
        } else {
            return medicineMapper.medicineToMedicineDto(
                    medicineRepository.findById(medicineId).orElseThrow(NotFoundException::new)
            );
        }
    }

    @Override
    public MedicineDto saveNewMedicine(MedicineDto medicineDto) {
        return medicineMapper.medicineToMedicineDto(medicineRepository.save(medicineMapper.medicineDtoToMedicine(medicineDto)));
    }

    @Override
    public MedicineDto updateMedicine(UUID medicineId, MedicineDto medicineDto) {
        Medicine medicine = medicineRepository.findById(medicineId).orElseThrow(NotFoundException::new);
        medicine.setMedicineName(medicineDto.getMedicineName());
        medicine.setMedicineStyle(medicineDto.getMedicineStyle().name());
        medicine.setPrice(medicineDto.getPrice());
        medicine.setUpc(medicineDto.getUpc());

        return medicineMapper.medicineToMedicineDto(medicineRepository.save(medicine));
    }

    @Cacheable(cacheNames = "medicineUpcCache")
    @Override
    public MedicineDto getByUpc(String upc) {
        return medicineMapper.medicineToMedicineDto(medicineRepository.findByUpc(upc));
    }
}
