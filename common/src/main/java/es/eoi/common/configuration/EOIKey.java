package es.eoi.common.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class EOIKey {

    private String key;

    private String label;

    @JsonIgnore
    private Boolean enabled;

}
