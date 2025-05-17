package com.project.findit.services;

import com.project.findit.models.LocationModel;
import com.project.findit.repositories.CityRepository;
import com.project.findit.repositories.CountryRepository;
import com.project.findit.repositories.LocationRepository;
import com.project.findit.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LocationServiceImpl implements LocationService{

    @Autowired
    public LocationRepository locationRepository;
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public LocationModel createLocation(LocationModel locationModel) {
        if (locationModel.getCidades() != null) {
            locationModel.setCidades(cityRepository.findById(locationModel.getCidades().getId())
                    .orElseThrow(() -> new RuntimeException("Cidade não encontrada!")));
        }

        if (locationModel.getEstados() != null) {
            locationModel.setEstados(stateRepository.findById(locationModel.getEstados().getSigla())
                    .orElseThrow(() -> new RuntimeException("Estado não encontrado!")));
        }

        if (locationModel.getPaises() != null) {
            locationModel.setPaises(countryRepository.findById(locationModel.getPaises().getSigla())
                    .orElseThrow(() -> new RuntimeException("País não encontrado!")));
        }

        return locationRepository.save(locationModel);
    }

    @Override
    public List<LocationModel> getAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Optional<LocationModel> getLocationDetails(UUID id) {
        return locationRepository.findById(id);
    }

    @Override
    public LocationModel updateLocationDatails(UUID id, LocationModel locationModel) {
        LocationModel existingLocation = locationRepository.findById(id).orElse(null);

        if (existingLocation != null){
            existingLocation.setCapacidade_de_pessoas(locationModel.getCapacidade_de_pessoas());
            existingLocation.setEndereco(locationModel.getEndereco());
            existingLocation.setNome(locationModel.getNome());
            existingLocation.setTelefone(locationModel.getTelefone());
            existingLocation.setUrl_mapa(locationModel.getUrl_mapa());
            existingLocation.setCidades(locationModel.getCidades());
            existingLocation.setEstados(locationModel.getEstados());
            existingLocation.setPaises(locationModel.getPaises());

            return locationRepository.save(existingLocation);
        } else {
            throw new RuntimeException("Local não encontrado com esse ID: " + id);
        }
    }

    @Override
    public void deleteLocation(UUID id) {
        locationRepository.deleteById(id);
    }
}
