package com.springproject.offre_emploi.services;

import com.springproject.offre_emploi.Beans.ChartData;
import com.springproject.offre_emploi.Repository.OffreRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ChartDataService {

    private final OffreRepository offreRepository;

    public ChartDataService(OffreRepository offreRepository) {
        this.offreRepository = offreRepository;
    }

    public ChartData getChartData() {

        // Retrieve the data from the repository
        List<String> labels =new ArrayList<String>();
                //chartDataRepository.getLabels();
        List<Integer> data = new ArrayList<Integer>() ;

        for (int i = 1; i <= 12; i++) {
           data.add(offreRepository.CountOfferByMonth(i));
        }
        //chartDataRepository.getData();
        // Create a ChartData object and populate it with the fetched data
        ChartData chartData = new ChartData(labels, data);

        return chartData;
    }
    public List<Object[]> getChartData2() {
        List<Object[]> MostCityPosts = offreRepository.findTopCitiesWithOffers();
        return MostCityPosts;
    }

}
