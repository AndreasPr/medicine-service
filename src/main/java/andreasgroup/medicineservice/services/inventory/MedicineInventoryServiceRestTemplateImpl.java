package andreasgroup.medicineservice.services.inventory;

import andreasgroup.medicineservice.services.inventory.model.MedicineInventoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created on 28/Nov/2020 to microservices-medicine-production
 */
@Profile("!local-discovery")
@Slf4j
@ConfigurationProperties(prefix = "andreas.production", ignoreUnknownFields = true)
@Component
public class MedicineInventoryServiceRestTemplateImpl implements MedicineInventoryService{

    public static final String INVENTORY_PATH = "/api/v1/medicine/{medicineId}/inventory";
    private final RestTemplate restTemplate;

    private String medicineInventoryServiceHost;

    public void setMedicineInventoryServiceHost(String medicineInventoryServiceHost) {
        this.medicineInventoryServiceHost = medicineInventoryServiceHost;
    }

    public MedicineInventoryServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder,
                                                @Value("${andreas.production.inventory-user}") String inventoryUser,
                                                @Value("${andreas.production.inventory-password}")String inventoryPassword) {
        this.restTemplate = restTemplateBuilder
                .basicAuthentication(inventoryUser, inventoryPassword)
                .build();
    }

    @Override
    public Integer getOnhandInventory(UUID medicineId) {

        log.debug("Calling Inventory Service...");

        ResponseEntity<List<MedicineInventoryDto>> responseEntity = restTemplate
                .exchange(medicineInventoryServiceHost + INVENTORY_PATH, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<MedicineInventoryDto>>(){}, (Object) medicineId);

        //sum from inventory list
        Integer onHand = Objects.requireNonNull(responseEntity.getBody())
                .stream()
                .mapToInt(MedicineInventoryDto::getQuantityOnHand)
                .sum();

        return onHand;
    }
}
