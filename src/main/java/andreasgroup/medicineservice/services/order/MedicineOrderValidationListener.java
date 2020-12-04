package andreasgroup.medicineservice.services.order;

import andreasgroup.medicineservice.configuration.JmsConfiguration;
import andreasgroup.production.model.events.ValidateOrderRequest;
import andreasgroup.production.model.events.ValidateOrderResult;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Created on 28/Nov/2020 to microservices-medicine-production
 */
@RequiredArgsConstructor
@Component
public class MedicineOrderValidationListener {

    private final MedicineOrderValidator validator;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfiguration.VALIDATE_ORDER_QUEUE)
    public void listen(ValidateOrderRequest validateOrderRequest){
        Boolean isValid = validator.validateOrder(validateOrderRequest.getMedicineOrder());

        jmsTemplate.convertAndSend(JmsConfiguration.VALIDATE_ORDER_RESPONSE_QUEUE,
                ValidateOrderResult.builder()
                        .isValid(isValid)
                        .orderId(validateOrderRequest.getMedicineOrder().getId())
                        .build());
    }
}
