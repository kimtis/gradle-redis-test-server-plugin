package com.kimtis.gradle.redis

import com.kimtis.gradle.redis.tasks.RedisServerStart
import com.kimtis.gradle.redis.tasks.RedisServerStop
import org.gradle.api.Plugin
import org.gradle.api.Project

class RedisServerPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        RedisServerExtension extension = project.extensions.create('redisTestServer', RedisServerExtension, project.objects)
        project.tasks.register('redisStart', RedisServerStart, {it.extension = extension})
        project.tasks.register('redisStop', RedisServerStop, {it.extension = extension})
    }
}
