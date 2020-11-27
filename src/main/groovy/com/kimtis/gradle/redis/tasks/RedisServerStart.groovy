package com.kimtis.gradle.redis.tasks


import com.kimtis.gradle.redis.RedisServerExtension
import groovy.transform.Internal
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecSpec

class RedisServerStart extends DefaultTask {

    @Internal
    RedisServerExtension extension

    RedisServerStart() {
        group = 'redis'
        description = 'Pull and run docker container of redis server'
    }

    @TaskAction
    void start() {

        def redisDir = project.layout.buildDirectory.dir('redis').get()

        if (redisDir.asFile.exists()) {
            redisDir.asFile.deleteDir()
        }
        redisDir.asFile.mkdirs()

        def nodesConf = redisDir.file("nodes.conf").asFile << """\
            b466e270e9ada5aa4826d0198eaccb9260cd6990 :6379 myself,master - 0 0 0 connected 0-16383
            vars currentEpoch 0 lastVoteEpoch 0
        """.stripIndent()

        def redisConf = redisDir.file("redis.conf").asFile << """\
            port 6379
            save ""
            appendonly no
            cluster-enabled yes
            cluster-node-timeout 150
            cluster-config-file /usr/local/etc/redis/nodes.conf
        """.stripIndent()

        println(new ByteArrayOutputStream().withStream { ByteArrayOutputStream os ->
            def er = project.exec { ExecSpec e ->
                e.commandLine = ['docker', 'run', '-d', '--rm',
                                 "--name=${project.name}-redis-cluster-m0-r0",
                                 "--volume=${redisConf.getAbsolutePath()}:/usr/local/etc/redis/redis.conf",
                                 "--volume=${nodesConf.getAbsolutePath()}:/usr/local/etc/redis/nodes.conf",
                                 "--publish=${extension.port.get()}:6379",
                                 "redis:${extension.version.get()}",
                                 "redis-server",
                                 "/usr/local/etc/redis/redis.conf"
                ]
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
