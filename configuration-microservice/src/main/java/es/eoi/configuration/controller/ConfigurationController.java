package es.eoi.configuration.controller;

import es.eoi.common.configuration.EOIConfiguration;
import es.eoi.configuration.service.ConfigurationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/configurations")
public class ConfigurationController {

    private final ConfigurationService configurationService;


    @Operation(description = "Get all global EOI configurations defined")
    @GetMapping("/all")
    public ResponseEntity<EOIConfiguration> getEOIConfiguration() {
        return ResponseEntity.ok(configurationService.getEOIConfiguration());
    }

    @Operation(description = "Get list of labels by language iso code.")
    @GetMapping("/labels/{language}")
    public ResponseEntity<Map<String, String>> getLabelsByLanguage(@PathVariable @NotBlank @Size(max = 2) String language) {
        return ResponseEntity.ok(configurationService.getLabels(language));
    }

    @Operation(description = "Get list of colours by language.")
    @GetMapping("/colours/{language}")
    public ResponseEntity<List<String>> getColoursByLanguage(@PathVariable @NotBlank @Size(max = 2) String language) {
        return ResponseEntity.ok(configurationService.getColours(language));
    }
}
