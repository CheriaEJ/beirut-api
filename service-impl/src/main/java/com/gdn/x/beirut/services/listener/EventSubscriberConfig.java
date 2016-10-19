package com.gdn.x.beirut.services.listener;

import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;

import com.gdn.common.base.domainevent.subscriber.SubscribeDomainEventConfig;

@Configuration
@EnableIntegration
public class EventSubscriberConfig extends SubscribeDomainEventConfig {

}
