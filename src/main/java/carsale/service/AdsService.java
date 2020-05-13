package carsale.service;

import carsale.dao.AdsRepository;
import carsale.models.Ads;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdsService {

    private final AdsRepository adsRepository;

    public AdsService(AdsRepository adsRepository) {
        this.adsRepository = adsRepository;
    }


    public Ads save(Ads ad) {
        adsRepository.save(ad);
        return ad;
    }

    public void removeById(int id) {
        adsRepository.removeById(id);
    }

    public List<Ads> getAll() {
        return adsRepository.getAll();
    }

    public Ads getById(int id) {
        Ads ad = adsRepository.getById(id);
        return ad;
    }

}

