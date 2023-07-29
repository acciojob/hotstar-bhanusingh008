package com.driver.Transformer;

import com.driver.EntryDto.ProductionHouseEntryDto;
import com.driver.model.ProductionHouse;

public class ProductionTransformer {

    public static ProductionHouse convertDtoToEntity(ProductionHouseEntryDto entryDto){
        ProductionHouse productionHouse=new ProductionHouse();
        productionHouse.setName(entryDto.getName());
        productionHouse.setRatings(0);
        return productionHouse;
    }
}
