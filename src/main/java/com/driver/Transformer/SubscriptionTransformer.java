package com.driver.Transformer;

import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;

public class SubscriptionTransformer {
    public static Subscription convertDtoToEntity(SubscriptionEntryDto entryDto) {
        Subscription subscription = new Subscription();
        subscription.setSubscriptionType(entryDto.getSubscriptionType());
        subscription.setNoOfScreensSubscribed(entryDto.getNoOfScreensRequired());

        return subscription;
    }
}
