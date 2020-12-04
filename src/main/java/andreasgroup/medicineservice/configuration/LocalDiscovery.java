package andreasgroup.medicineservice.configuration;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created on 28/Nov/2020 to microservices-medicine-production
 */
@Profile("local-discovery")
@EnableDiscoveryClient
@Configuration
public class LocalDiscovery {
}
