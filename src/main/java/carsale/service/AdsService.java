package carsale.service;

import carsale.dao.AdsRepository;
import carsale.models.Ads;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AdsService {

    private final AdsRepository adsRepository;

    @Autowired
    public AdsService(AdsRepository adsRepository) {
        this.adsRepository = adsRepository;
    }

    @Transactional
    public Ads save(Ads ad) {
        adsRepository.save(ad);
        return ad;
    }

    @Transactional
    public void removeById(int id) {
        adsRepository.removeById(id);
    }

    @Transactional
    public List<Ads> getAll() {
        return adsRepository.getAll();
    }

    @Transactional
    public Ads getById(int id) {
        Ads ad = adsRepository.getById(id);
        return ad;
    }

    @Transactional
    public List<Ads> getFilteredList(Integer sessionUserId, String onlyUserAds, String lastDay, String inSale,
                                     String withPhoto, String checkBrand, String brandId) {
        List<Ads> listOfAds = getAll();
        List<Ads> filterList = new ArrayList<>();

        filterList.addAll(listOfAds);
        LocalDate now = LocalDate.now();

        for (Ads ad : listOfAds) {
            if ((onlyUserAds != null) && (sessionUserId != null)) {
                if (ad.getUserId().getId() != sessionUserId) {
                    filterList.remove(ad);
                }
            }
            if (lastDay != null) {
                LocalDate adDate = ad.getCreated().toLocalDateTime().toLocalDate();
                if (now.compareTo(adDate) != 0) {
                    filterList.remove(ad);
                }
            }
            if (inSale != null) {
                if (ad.getSold()) {
                    filterList.remove(ad);
                }
            }
            if (withPhoto != null) {
                if (ad.getPhoto().length == 0) {
                    filterList.remove(ad);
                }
            }
            if (checkBrand != null) {
                if (ad.getCarDetails().getBrand().getId() != Integer.parseInt(brandId)) {
                    filterList.remove(ad);
                }
            }
        }
        return filterList;
    }

}

