package com.kimtis.gradle.redis.tasks


import com.kimtis.gradle.redis.RedisServerExtension
import groovy.transform.Internal
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecSpec

class RedisServerStop extends DefaultTask {

    @Internal
    RedisServerExtension extension

    RedisServerStop() {
        group = 'redis'
        description = 'Stop and remove docker container of redis server'
    }

    @TaskAction
    void stop() {
        println(new ByteArrayOutputStream().withStream { ByteArrayOutputStream os ->
            def er = project.exec { ExecSpec e ->
                e.commandLine = ['docker', 'stop', "${project.name}-redis-cluster-m0-r0"]
                e.standardOutput = os
                e.ignoreExitValue = true
            }
            def stdout = os.toString().trim()
            if (er.exitValue != 0) {
                throw new RuntimeException("Exit-code ${er.exitValue} when calling docker, stdout: $stdout")
            }
            stdout
        })
    }
}
