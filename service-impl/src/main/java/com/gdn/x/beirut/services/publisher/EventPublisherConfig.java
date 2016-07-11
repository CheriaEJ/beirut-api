package com.gdn.x.beirut.services.publisher;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.gdn.common.base.domainevent.publisher.PublishDomainEventConfig;

@Configuration
@EnableAspectJAutoProxy
public class EventPublisherConfig extends PublishDomainEventConfig {

}
