package andreasgroup.production.model.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Created on 28/Nov/2020 to microservices-medicine-production
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineOrderLineDto {

    @JsonProperty("id")
    private UUID id = null;

    @JsonProperty("version")
    private Integer version = null;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
    @JsonProperty("createdDate")
    private OffsetDateTime createdDate = null;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
    @JsonProperty("lastModifiedDate")
    private OffsetDateTime lastModifiedDate = null;

    private String upc;
    private String medicineName;
    private String medicineStyle;
    private UUID medicineId;
    private Integer orderQuantity = 0;
    private BigDecimal price;
}
