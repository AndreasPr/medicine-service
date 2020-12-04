package andreasgroup.medicineservice.web.controller;

import andreasgroup.medicineservice.bootstrap.MedicineLoader;
import andreasgroup.medicineservice.services.MedicineService;
import andreasgroup.production.model.MedicineDto;
import andreasgroup.production.model.MedicineStyleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created on 02/Dec/2020 to microservices-medicine-production
 */
@WebMvcTest
public class MedicineControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MedicineService medicineService;

    @Test
    void getMedicineById() throws Exception {

        given(medicineService.getById(any(), anyBoolean())).willReturn(getValidMedicineDto());

        mockMvc.perform(get("/api/v1/medicine/" + UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void saveNewMedicine() throws Exception {

        MedicineDto medicineDto = getValidMedicineDto();
        String medicineDtoJson = objectMapper.writeValueAsString(medicineDto);

        given(medicineService.saveNewMedicine(any())).willReturn(getValidMedicineDto());

        mockMvc.perform(post("/api/v1/medicine/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(medicineDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void updateMedicineById() throws Exception {
        given(medicineService.updateMedicine(any(), any())).willReturn(getValidMedicineDto());

        MedicineDto medicineDto = getValidMedicineDto();
        String medicineDtoJson = objectMapper.writeValueAsString(medicineDto);

        mockMvc.perform(put("/api/v1/medicine/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(medicineDtoJson))
                .andExpect(status().isNoContent());
    }

    MedicineDto getValidMedicineDto(){
        return MedicineDto.builder()
                .medicineName("My Medicine")
                .medicineStyle(MedicineStyleEnum.Antacids)
                .price(new BigDecimal("2.99"))
                .upc(MedicineLoader.MEDICINE_1_UPC)
                .build();
    }
}
