package com.driver.services;

import com.driver.EntryDto.WebSeriesEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.model.WebSeries;
import com.driver.repository.ProductionHouseRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WebSeriesService {

    @Autowired
    WebSeriesRepository webSeriesRepository;

    @Autowired
    ProductionHouseRepository productionHouseRepository;

    public Integer addWebSeries(WebSeriesEntryDto webSeriesEntryDto)throws  Exception{

        //Add a webSeries to the database and update the ratings of the productionHouse
        //Incase the seriesName is already present in the Db throw Exception("Series is already present")
        //use function written in Repository Layer for the same
        //Dont forget to save the production and webseries Repo

        Optional<ProductionHouse> productionHouseOptional = productionHouseRepository.findById(webSeriesEntryDto.getProductionHouseId());

        if(!productionHouseOptional.isPresent()){
            throw new RuntimeException("ProductionHouse not available");
        }

        ProductionHouse productionHouse = productionHouseOptional.get();

        WebSeries series = new WebSeries();
        series.setSeriesName(webSeriesEntryDto.getSeriesName());
        series.setAgeLimit(webSeriesEntryDto.getAgeLimit());
        series.setRating(webSeriesEntryDto.getRating());
        series.setProductionHouse(productionHouse);
        series.setSubscriptionType(webSeriesEntryDto.getSubscriptionType());

        if(webSeriesRepository.findBySeriesName(series.getSeriesName()) == null){
            throw new RuntimeException("Series is already present");
        }

        ProductionHouse savedProductionHouse = productionHouseRepository.save(productionHouse);

        List<WebSeries> list = savedProductionHouse.getWebSeriesList();

        WebSeries last = list.get(list.size()-1);

        return last.getId();
    }

}
