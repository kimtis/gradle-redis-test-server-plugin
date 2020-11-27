package com.kimtis.gradle.redis

import org.gradle.api.provider.Property
import org.gradle.api.model.ObjectFactory

class RedisServerExtension {

    final Property<String> version

    final Property<Integer> port

    RedisServerExtension(ObjectFactory objectFactory) {
        version = objectFactory.property(String)
        version.set('4.0.14')
        port = objectFactory.property(Integer)
        port.set(6379)
    }
}
