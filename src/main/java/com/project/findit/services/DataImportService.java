package com.project.findit.services;

import com.project.findit.models.CityModel;
import com.project.findit.models.CountryModel;
import com.project.findit.models.StateModel;
import com.project.findit.repositories.CityRepository;
import com.project.findit.repositories.CountryRepository;
import com.project.findit.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class DataImportService {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private CityRepository cityRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public void importDataFromIBGE() {
        importCountry();
        Map<Integer, StateModel> states = importStates();
        importCities(states);
    }

    private void importCountry() {
        if (!countryRepository.existsById("BR")) {
            CountryModel country = new CountryModel();
            country.setSigla("BR");
            country.setNome("Brasil");
            countryRepository.save(country);
        }
    }

    private Map<Integer, StateModel> importStates() {
        String url = "https://servicodados.ibge.gov.br/api/v1/localidades/estados";
        List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);

        Map<Integer, StateModel> stateMap = new HashMap<>();
        for (Map<String, Object> stateData : response) {
            String sigla = (String) stateData.get("sigla");
            String nome = (String) stateData.get("nome");
            Integer id = (Integer) stateData.get("id");

            if (!stateRepository.existsById(sigla)) {
                StateModel state = new StateModel();
                state.setSigla(sigla);
                state.setNome(nome);
                stateRepository.save(state);
                stateMap.put(id, state);
            } else {
                stateMap.put(id, stateRepository.findById(sigla).orElse(null));
            }
        }

        return stateMap;
    }

    private void importCities(Map<Integer, StateModel> stateMap) {
        String url = "https://servicodados.ibge.gov.br/api/v1/localidades/municipios";
        List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);

        for (Map<String, Object> cityData : response) {
            String nomeCidade = (String) cityData.get("nome");
            Map<String, Object> microrregiao = (Map<String, Object>) cityData.get("microrregiao");
            Map<String, Object> mesorregiao = (Map<String, Object>) microrregiao.get("mesorregiao");
            Map<String, Object> uf = (Map<String, Object>) mesorregiao.get("UF");
            Integer idUf = (Integer) uf.get("id");

            CityModel city = new CityModel();
            city.setNome(nomeCidade);
            cityRepository.save(city);
        }
    }
}