package carsale.service;

import carsale.dao.CarRepository;
import carsale.models.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    private final CarRepository carRepository;

    public Car create(Car car) {
       carRepository.save(car);
       return car;
    }

    public List<Brands> getBrands() {
        return carRepository.getBrands();
    }

    public List<BodyType> getBodies() {
        return carRepository.getBodies();
    }

    public List<Engines> getEngines() {
        return carRepository.getEngines();
    }

    public List<Models> getModelsByBrandId(int brandId) {
        return carRepository.getModelsByBrandId(brandId);
    }

    public BodyType getBodyById(int id) {
        return carRepository.getBodyById(id);
    }

    public Brands getBrandById(int id) {
        return carRepository.getBrandById(id);
    }

    public Models getModelById(int id) {
        return carRepository.getModelById(id);
    }

    public Engines getEngineById(int id) {
        return carRepository.getEngineById(id);
    }
}
