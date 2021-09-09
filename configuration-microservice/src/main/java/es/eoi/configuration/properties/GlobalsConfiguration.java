package es.eoi.configuration.properties;

import es.eoi.common.configuration.EOIKey;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;


@Data
@Configuration
@ConfigurationProperties(prefix = "globals.properties.configurations")
public class GlobalsConfiguration {

    /* ========= Translations languages ========= */
    private HashMap<String, HashMap<String, String>> labels;

    /* ========= General configurations ========= */
    private HashMap<String, List<String>> colours;

    private HashMap<String, List<EOIKey>> defConfiguration;

}