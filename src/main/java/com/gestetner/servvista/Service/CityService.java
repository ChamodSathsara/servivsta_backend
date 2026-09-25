package com.gestetner.servvista.Service;

import com.gestetner.servvista.Dto.organization.CityRequest;
import com.gestetner.servvista.Dto.organization.CityResponse;
import com.gestetner.servvista.Models.entity.organization.City;
import com.gestetner.servvista.Repositories.organization.CityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public CityResponse create(CityRequest request) {
        String cityName = request.cityName().trim();
        validateUnique(request, cityName, null);

        City city = new City();
        apply(city, request, cityName, true);

        return response(cityRepository.saveAndFlush(city));
    }

    @Transactional(readOnly = true)
    public List<CityResponse> getAll() {
        return cityRepository.findAll(Sort.by("area", "cityName"))
                .stream()
                .map(this::response)
                .toList();
    }

    @Transactional(readOnly = true)
    public CityResponse getById(Long cityId) {
        return response(find(cityId));
    }

    public CityResponse update(Long cityId, CityRequest request) {
        City city = find(cityId);
        String cityName = request.cityName().trim();
        validateUnique(request, cityName, cityId);
        apply(city, request, cityName, false);

        return response(cityRepository.saveAndFlush(city));
    }

    public void delete(Long cityId) {
        City city = find(cityId);

        try {
            cityRepository.delete(city);
            cityRepository.flush();
        } catch (DataIntegrityViolationException exception) {
            throw new IllegalStateException(
                    "City " + cityId + " cannot be deleted because it is referenced by other records",
                    exception);
        }
    }

    private City find(Long cityId) {
        return cityRepository.findById(cityId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "City " + cityId + " was not found"));
    }

    private void validateUnique(CityRequest request, String cityName, Long cityId) {
        boolean exists = cityId == null
                ? cityRepository.existsByAreaAndCityNameIgnoreCase(request.area(), cityName)
                : cityRepository.existsByAreaAndCityNameIgnoreCaseAndCityIdNot(
                        request.area(), cityName, cityId);

        if (exists) {
            throw new IllegalStateException(
                    "City " + cityName + " already exists in area " + request.area());
        }
    }

    private void apply(City city, CityRequest request, String cityName, boolean create) {
        city.setArea(request.area());
        city.setCityName(cityName);

        if (create) {
            city.setIsActive(request.isActive() == null || request.isActive());
        } else if (request.isActive() != null) {
            city.setIsActive(request.isActive());
        }
    }

    private CityResponse response(City city) {
        return new CityResponse(
                city.getCityId(),
                city.getArea(),
                city.getCityName(),
                city.getIsActive());
    }
}
