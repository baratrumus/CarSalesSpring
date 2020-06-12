package carsale.service;

import carsale.dao.CarRepository;
import carsale.models.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarsService {
    public CarsService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    private final CarRepository carRepository;

    @Transactional
    public Car create(Car car) {
       carRepository.save(car);
       return car;
    }

    @Transactional
    public List<Brands> getBrands() {
        return carRepository.getBrands();
    }

    @Transactional
    public List<BodyType> getBodies() {
        return carRepository.getBodies();
    }

    @Transactional
    public List<Engines> getEngines() {
        return carRepository.getEngines();
    }

    @Transactional
    public List<Models> getModelsByBrandId(int brandId) {
        return carRepository.getModelsByBrandId(brandId);
    }

    @Transactional
    public BodyType getBodyById(int id) {
        return carRepository.getBodyById(id);
    }

    @Transactional
    public Brands getBrandById(int id) {
        return carRepository.getBrandById(id);
    }

    @Transactional
    public Models getModelById(int id) {
        return carRepository.getModelById(id);
    }

    @Transactional
    public Engines getEngineById(int id) {
        return carRepository.getEngineById(id);
    }
}
