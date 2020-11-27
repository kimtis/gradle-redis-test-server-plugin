package com.kimtis.gradle.redis

import com.kimtis.gradle.redis.tasks.RedisServerStart
import com.kimtis.gradle.redis.tasks.RedisServerStop
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class RedisServerPluginTest extends Specification {
    def "add tasks and extension to the project"() {
        def project = ProjectBuilder.builder().build()
        when:
            project.plugins.apply 'com.kimtis.gradle.redis-test-server'
        then:
            project.tasks.redisStart instanceof RedisServerStart
            project.tasks.redisStop instanceof RedisServerStop
    }

//    def "redis start task manually"() {
//        def project = ProjectBuilder.builder().build()
//        when:
//            project.plugins.apply 'com.kimtis.gradle.redis-test-server'
//        then:
//            project.tasks.redisStart.start()
//    }
//
//    def "redis stop task manually"() {
//        def project = ProjectBuilder.builder().build()
//        when:
//            project.plugins.apply 'com.kimtis.gradle.redis-test-server'
//        then:
//            project.tasks.redisStop.stop()
//    }
}