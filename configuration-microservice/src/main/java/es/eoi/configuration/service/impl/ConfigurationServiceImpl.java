package es.eoi.configuration.service.impl;

import es.eoi.common.configuration.EOIConfiguration;
import es.eoi.common.configuration.EOIKey;
import es.eoi.common.exceptions.ConfigurationException;
import es.eoi.configuration.properties.GlobalsConfiguration;
import es.eoi.configuration.service.ConfigurationService;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static es.eoi.common.utils.ErrorMessages.ILLEGAL_ARG_EMPTY_PARAMS;
import static es.eoi.common.utils.ErrorMessages.UNDEFINED_CONFIG_COLOURS;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    protected static final String COTYPE_KEY_TRANSLATION_LANGUAGES = "translationLanguages";

    private final GlobalsConfiguration globalsConfiguration;

    /* ========= All configurations ========= */
    @Override
    public EOIConfiguration getEOIConfiguration() {
        log.debug("get EOI Configuration");
        // Return only the configurations that FE needs and filter them if it is necessary
        return EOIConfiguration.builder()
                .colours(globalsConfiguration.getColours())
                .defConfiguration( filterByEnabledKeys(globalsConfiguration.getDefConfiguration()))
                .build();
    }

    /* ========= Translations languages ========= */
    @Override
    public HashMap<String, String> getLabels(String language) {
        log.debug("get Labels");
        if (StringUtils.isBlank(language)) {
            throw new IllegalArgumentException(ILLEGAL_ARG_EMPTY_PARAMS);
        }

        if (globalsConfiguration.getLabels().containsKey(language.toLowerCase(Locale.ROOT))) {
            return globalsConfiguration.getLabels().get(language.toLowerCase(Locale.ROOT));
        }
        return new HashMap<>();
    }

    /* ========= Colours ========= */
    @Override
    public List<String> getColours(String language) {
        log.debug("get Colours");
        if (StringUtils.isBlank(language)) {
            throw new IllegalArgumentException(ILLEGAL_ARG_EMPTY_PARAMS);
        }

        if (globalsConfiguration.getColours() == null) {
            throw new ConfigurationException(UNDEFINED_CONFIG_COLOURS);
        }

        if (globalsConfiguration.getColours().containsKey(language.toLowerCase(Locale.ROOT))) {
            return globalsConfiguration.getColours().get(language.toLowerCase(Locale.ROOT));
        }

        return new ArrayList<>();
    }

    /* ========= Private methods ========= */
    private HashMap<String, List<EOIKey>> filterByEnabledKeys(HashMap<String, List<EOIKey>> mapToBeFiltered) {
        log.debug("Filtering not enabled values");
        return (HashMap<String, List<EOIKey>>) mapToBeFiltered.entrySet().stream().parallel()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        c -> c.getValue().stream()
                                .filter(EOIKey::getEnabled)
                                .collect(Collectors.toList()))
                );

    }
}
