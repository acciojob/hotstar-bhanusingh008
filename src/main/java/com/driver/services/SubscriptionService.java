package com.driver.services;


import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto){

        //Save The subscription Object into the Db and return the total Amount that user has to pay

        Optional<User> user = userRepository.findById(subscriptionEntryDto.getUserId());

        if(!user.isPresent()){
            throw new RuntimeException("User does not exist.");
        }

        int amount = 0;

        String sub = subscriptionEntryDto.getSubscriptionType().toString();

        switch (sub) {
            case "BASIC":
                amount += 500 + 200 * (subscriptionEntryDto.getNoOfScreensRequired());
                break;
            case "PRO":
                amount += 800 + 250 * (subscriptionEntryDto.getNoOfScreensRequired());
                break;
            case "ELITE":
                amount += 1000 + 350 * (subscriptionEntryDto.getNoOfScreensRequired());
                break;
        }

        Subscription subscription = new Subscription();
        subscription.setSubscriptionType(subscriptionEntryDto.getSubscriptionType());
        subscription.setStartSubscriptionDate(new Date());
        subscription.setUser(user.get());
        subscription.setNoOfScreensSubscribed(subscriptionEntryDto.getNoOfScreensRequired());
        subscription.setTotalAmountPaid(amount);

        subscriptionRepository.save(subscription);

        return amount;
    }

    public Integer upgradeSubscription(Integer userId)throws Exception{

        //If you are already at an ElITE subscription : then throw Exception ("Already the best Subscription")
        //In all other cases just try to upgrade the subscription and tell the difference of price that user has to pay
        //update the subscription in the repository

        Optional<User> optionalUser = userRepository.findById(userId);

        if(!optionalUser.isPresent()){
            throw new RuntimeException("User not present");
        }

        User user = optionalUser.get();

        Subscription subscription = user.getSubscription();

        String s = subscription.getSubscriptionType().toString();

        int extra = 0;

        switch (s) {
            case "BASIC":
                user.getSubscription().setSubscriptionType(SubscriptionType.PRO);
                extra = 300 + subscription.getNoOfScreensSubscribed()*(50);
                break;

            case "PRO":
                user.getSubscription().setSubscriptionType(SubscriptionType.ELITE);
                extra = 200 + subscription.getNoOfScreensSubscribed()*(100);
                break;

            case "ELITE":
                throw new RuntimeException("Already the best Subscription");
        }

        return extra;
    }

    public Integer calculateTotalRevenueOfHotstar(){

        //We need to find out total Revenue of hotstar : from all the subscriptions combined
        //Hint is to use findAll function from the SubscriptionDb

        int totalRevenue = 0;

        List<Subscription> allSubscriptions = subscriptionRepository.findAll();

        if(allSubscriptions.size()==0) return 0;

        for (Subscription subscription : allSubscriptions){
            totalRevenue+=subscription.getTotalAmountPaid();
        }

        return totalRevenue;
    }

}
