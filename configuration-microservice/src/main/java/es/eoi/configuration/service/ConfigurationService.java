package es.eoi.configuration.service;

import es.eoi.common.configuration.EOIConfiguration;

import java.util.HashMap;
import java.util.List;
public interface ConfigurationService {

    EOIConfiguration getEOIConfiguration();

    HashMap<String, String> getLabels(String language);

    List<String> getColours(String language);
}
