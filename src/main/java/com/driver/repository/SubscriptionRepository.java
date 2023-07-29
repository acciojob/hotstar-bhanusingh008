package com.driver.repository;

import com.driver.model.Subscription;
import jdk.internal.loader.AbstractClassLoaderValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription,Integer> {

}
