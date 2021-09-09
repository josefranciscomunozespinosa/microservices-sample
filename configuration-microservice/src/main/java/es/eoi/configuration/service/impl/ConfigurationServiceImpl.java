package es.eoi.configuration.service.impl;

import es.eoi.common.configuration.EOIConfiguration;
import es.eoi.common.exceptions.ConfigurationException;
import es.eoi.configuration.properties.GlobalsConfiguration;
import es.eoi.configuration.service.ConfigurationService;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
        // Return only the configurations that FE needs and filter them if it is necessary
        return EOIConfiguration.builder()
                .colours(globalsConfiguration.getColours())
                .defConfiguration( globalsConfiguration.getDefConfiguration())
                .build();
    }

    /* ========= Translations languages ========= */
    @Override
    public HashMap<String, String> getLabels(String language) {
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

}
