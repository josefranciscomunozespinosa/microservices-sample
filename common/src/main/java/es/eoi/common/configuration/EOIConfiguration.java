package es.eoi.common.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.List;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class EOIConfiguration {

    /* ========= General configurations ========= */
    private HashMap<String, List<String>> colours;

    private HashMap<String, List<EOIKey>> defConfiguration;

}
